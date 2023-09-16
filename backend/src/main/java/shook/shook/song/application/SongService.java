package shook.shook.song.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shook.shook.auth.ui.Authority;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongSwipeResponse;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.SongTitle;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.domain.repository.dto.SongTotalLikeCountDto;
import shook.shook.song.exception.SongException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongService {

    private static final int AFTER_SONGS_COUNT = 10;
    private static final int BEFORE_SONGS_COUNT = 10;

    private final SongRepository songRepository;
    private final KillingPartRepository killingPartRepository;
    private final MemberRepository memberRepository;
    private final KillingPartLikeRepository killingPartLikeRepository;
    private final SongDataExcelReader songDataExcelReader;

    @Transactional
    public Long register(final SongWithKillingPartsRegisterRequest request) {
        final Song savedSong = saveSong(request.convertToSong());

        return savedSong.getId();
    }

    private Song saveSong(final Song song) {
        if (songRepository.existsSongByTitle(new SongTitle(song.getTitle()))) {
            throw new SongException.SongAlreadyExistException(Map.of("Song-Name", song.getTitle()));
        }
        final Song savedSong = songRepository.save(song);
        killingPartRepository.saveAll(song.getKillingParts());
        return savedSong;
    }

    public List<HighLikedSongResponse> showHighLikedSongs() {
        final List<SongTotalLikeCountDto> songsWithLikeCount = songRepository.findAllWithTotalLikeCount();

        return HighLikedSongResponse.ofSongTotalLikeCounts(
            sortByHighestLikeCountAndId(songsWithLikeCount)
        );
    }

    private List<SongTotalLikeCountDto> sortByHighestLikeCountAndId(
        final List<SongTotalLikeCountDto> songWithLikeCounts
    ) {
        return songWithLikeCounts.stream()
            .sorted(
                Comparator.comparing(
                    SongTotalLikeCountDto::getTotalLikeCount,
                    Comparator.reverseOrder()
                ).thenComparing(dto -> dto.getSong().getId(), Comparator.reverseOrder())
            ).toList();
    }

    public SongSwipeResponse findSongByIdForFirstSwipe(
        final Long songId,
        final MemberInfo memberInfo
    ) {
        final Song currentSong = findSongById(songId);

        final List<Song> songs = songRepository.findAllWithKillingParts();
        songs.sort(Comparator.comparing(Song::getTotalLikeCount, Comparator.reverseOrder())
            .thenComparing(Song::getId, Comparator.reverseOrder()));

        final int currentSongIndex = songs.indexOf(currentSong);
        final List<Song> beforeSongs = songs.subList(Math.max(0, currentSongIndex - 10), currentSongIndex);

        if (currentSongIndex == songs.size() - 1) {
            return convertToSongSwipeResponse(memberInfo, currentSong, beforeSongs, Collections.emptyList());
        }

        final List<Song> afterSongs = songs.subList(
            Math.min(currentSongIndex + 1, songs.size() - 1),
            Math.min(songs.size(), currentSongIndex + 11)
        );

        return convertToSongSwipeResponse(memberInfo, currentSong, beforeSongs, afterSongs);
    }

    private Song findSongById(final Long songId) {
        return songRepository.findById(songId)
            .orElseThrow(() -> new SongException.SongNotExistException(
                Map.of("SongId", String.valueOf(songId))
            ));
    }

    private List<Song> findBeforeSongs(final Song song) {
        final List<Song> result = songRepository.findSongsWithMoreLikeCountThanSongWithId(
            song.getId(), PageRequest.of(0, BEFORE_SONGS_COUNT)
        );

        Collections.reverse(result);
        return result;
    }

    private List<Song> findAfterSongs(final Song song) {
        return songRepository.findSongsWithLessLikeCountThanSongWithId(
            song.getId(), PageRequest.of(0, AFTER_SONGS_COUNT)
        );
    }

    private SongSwipeResponse convertToSongSwipeResponse(
        final MemberInfo memberInfo,
        final Song currentSong,
        final List<Song> beforeSongs, final List<Song> afterSongs
    ) {
        final Authority authority = memberInfo.getAuthority();

        if (authority.isAnonymous()) {
            return SongSwipeResponse.ofUnauthorizedUser(currentSong, beforeSongs, afterSongs);
        }

        final Member member = findMemberById(memberInfo.getMemberId());
        final List<Long> killingPartIdsByMember = killingPartLikeRepository.findKillingPartIdsByMember(member);

        return SongSwipeResponse.of(killingPartIdsByMember, currentSong, beforeSongs, afterSongs);
    }

    private Member findMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(
                () -> new MemberException.MemberNotExistException(
                    Map.of("MemberId", String.valueOf(memberId))
                )
            );
    }

    public List<SongResponse> findSongByIdForBeforeSwipe(
        final Long songId,
        final MemberInfo memberInfo
    ) {
        final Song currentSong = findSongById(songId);

        final List<Song> beforeSongs =
            findBeforeSongs(currentSong);

        return convertToSongResponses(memberInfo, beforeSongs);
    }

    private List<SongResponse> convertToSongResponses(
        final MemberInfo memberInfo,
        final List<Song> songs
    ) {
        final Authority authority = memberInfo.getAuthority();

        if (authority.isAnonymous()) {
            return songs.stream()
                .map(SongResponse::fromUnauthorizedUser)
                .toList();
        }

        final Member member = findMemberById(memberInfo.getMemberId());
        final List<Long> killingPartIdsByMember = killingPartLikeRepository.findKillingPartIdsByMember(member);
        return songs.stream()
            .map(song -> SongResponse.of(song, killingPartIdsByMember))
            .toList();
    }

    public List<SongResponse> findSongByIdForAfterSwipe(
        final Long songId,
        final MemberInfo memberInfo
    ) {
        final Song currentSong = findSongById(songId);

        final List<Song> afterSongs =
            findAfterSongs(currentSong);

        return convertToSongResponses(memberInfo, afterSongs);
    }

    @Transactional
    public void saveSongsFromExcelFile(final MultipartFile excel) {
        final List<Song> songs = songDataExcelReader.parseToSongs(excel);
        songs.forEach(this::saveSong);
    }
}

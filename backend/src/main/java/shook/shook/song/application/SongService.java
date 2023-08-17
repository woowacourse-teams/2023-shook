package shook.shook.song.application;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.auth.ui.Authority;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException.MemberNotExistException;
import shook.shook.song.application.dto.SongResponse;
import shook.shook.song.application.dto.SongSwipeResponse;
import shook.shook.song.application.dto.SongWithKillingPartsRegisterRequest;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.domain.repository.dto.SongTotalLikeCountDto;
import shook.shook.song.exception.SongException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongService {

    private static final int AFTER_SONGS_COUNT = 10;
    private static final int BEFORE_SONGS_COUNT = 10;

    private final SongRepository songRepository;
    private final KillingPartRepository killingPartRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long register(final SongWithKillingPartsRegisterRequest request) {
        final Song song = request.convertToSong();
        final Song savedSong = songRepository.save(song);

        killingPartRepository.saveAll(song.getKillingParts());

        return savedSong.getId();
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
                Comparator.comparing(SongTotalLikeCountDto::getTotalLikeCount,
                        Comparator.reverseOrder())
                    .thenComparing(dto -> dto.getSong().getId(), Comparator.reverseOrder())
            ).toList();
    }

    public SongSwipeResponse findSongByIdForFirstSwipe(
        final Long songId,
        final MemberInfo memberInfo
    ) {
        final Song currentSong = findSongById(songId);

        final List<Song> beforeSongs = findBeforeSongs(currentSong);
        final List<Song> afterSongs = findAfterSongs(currentSong);

        return convertToSongSwipeResponse(memberInfo, currentSong, beforeSongs, afterSongs);
    }

    private Song findSongById(final Long songId) {
        return songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);
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

        return SongSwipeResponse.of(member, currentSong, beforeSongs, afterSongs);
    }

    private Member findMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotExistException::new);
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

        return songs.stream()
            .map((song) -> SongResponse.of(song, member))
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
}

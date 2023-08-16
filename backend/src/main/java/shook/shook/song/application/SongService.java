package shook.shook.song.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
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
        final Long memberId
    ) {
        final Song currentSong = findSongById(songId);

        final List<Song> beforeSongs = findBeforeSongs(currentSong);
        final List<Song> afterSongs = findAfterSongs(currentSong);

        return convertToSongSwipeResponse(memberId, currentSong, beforeSongs, afterSongs);
    }

    private Song findSongById(final Long songId) {
        return songRepository.findById(songId)
            .orElseThrow(SongException.SongNotExistException::new);
    }

    private List<Song> findBeforeSongs(final Song song) {
        return songRepository.findSongsWithMoreLikeCountThanSongWithId(
            song.getId(), PageRequest.of(0, BEFORE_SONGS_COUNT)
        );
    }

    private List<Song> findAfterSongs(final Song song) {
        return songRepository.findSongsWithLessLikeCountThanSongWithId(
            song.getId(), PageRequest.of(0, AFTER_SONGS_COUNT)
        );
    }

    private SongSwipeResponse convertToSongSwipeResponse(
        final Long memberId,
        final Song currentSong,
        final List<Song> beforeSongs, final List<Song> afterSongs
    ) {
        final Optional<Member> member = memberRepository.findById(memberId);

        return member.map(
                value -> SongSwipeResponse.of(value, currentSong, beforeSongs, afterSongs)
            )
            .orElseGet(
                () -> SongSwipeResponse.ofUnauthorizedUser(currentSong, beforeSongs, afterSongs)
            );
    }

    public List<SongResponse> findSongByIdForBeforeSwipe(
        final Long songId,
        final Long memberId
    ) {
        final Song currentSong = findSongById(songId);

        final List<Song> beforeSongs =
            findBeforeSongs(currentSong);

        return convertToSongResponses(memberId, beforeSongs);
    }

    private List<SongResponse> convertToSongResponses(
        final Long memberId,
        final List<Song> songs
    ) {
        final Optional<Member> member = memberRepository.findById(memberId);

        return member.map(
                value -> songs.stream()
                    .map((song) -> SongResponse.of(song, value))
                    .toList()
            )
            .orElseGet(
                () -> songs.stream()
                    .map(SongResponse::fromUnauthorizedUser)
                    .toList()
            );
    }

    public List<SongResponse> findSongByIdForAfterSwipe(
        final Long songId,
        final Long memberId
    ) {
        final Song currentSong = findSongById(songId);

        final List<Song> afterSongs =
            findAfterSongs(currentSong);

        return convertToSongResponses(memberId, afterSongs);
    }
}

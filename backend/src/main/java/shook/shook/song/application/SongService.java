package shook.shook.song.application;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
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
import shook.shook.song.domain.Genre;
import shook.shook.song.domain.InMemorySongs;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.song.domain.repository.ArtistRepository;
import shook.shook.song.domain.repository.SongRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SongService {

    private static final int AFTER_SONGS_COUNT = 10;
    private static final int BEFORE_SONGS_COUNT = 10;
    private static final int TOP_COUNT = 10;

    private final SongRepository songRepository;
    private final KillingPartRepository killingPartRepository;
    private final KillingPartLikeRepository killingPartLikeRepository;
    private final MemberRepository memberRepository;
    private final InMemorySongs inMemorySongs;
    private final ArtistRepository artistRepository;
    private final SongDataExcelReader songDataExcelReader;

    @Transactional
    public Long register(final SongWithKillingPartsRegisterRequest request) {
        final Song savedSong = saveSong(request.convertToSong());

        return savedSong.getId();
    }

    private Song saveSong(final Song song) {
        artistRepository.save(song.getArtist());
        final Song savedSong = songRepository.save(song);
        killingPartRepository.saveAll(song.getKillingParts());
        return savedSong;
    }

    public List<HighLikedSongResponse> showHighLikedSongs() {
        final List<Song> songs = inMemorySongs.getSongs(TOP_COUNT);

        return HighLikedSongResponse.ofSongs(songs);
    }

    public SongSwipeResponse findSongByIdForFirstSwipe(
        final Long songId,
        final MemberInfo memberInfo
    ) {
        final Song currentSong = inMemorySongs.getSongById(songId);

        final List<Song> beforeSongs = inMemorySongs.getPrevLikedSongs(currentSong,
            BEFORE_SONGS_COUNT);
        final List<Song> afterSongs = inMemorySongs.getNextLikedSongs(currentSong,
            AFTER_SONGS_COUNT);

        return convertToSongSwipeResponse(memberInfo, currentSong, beforeSongs, afterSongs);
    }

    private SongSwipeResponse convertToSongSwipeResponse(
        final MemberInfo memberInfo,
        final Song currentSong,
        final List<Song> beforeSongs,
        final List<Song> afterSongs
    ) {
        final Authority authority = memberInfo.getAuthority();

        if (authority.isAnonymous()) {
            return SongSwipeResponse.ofUnauthorizedUser(currentSong, beforeSongs, afterSongs);
        }

        final Member member = findMemberById(memberInfo.getMemberId());
        final List<Long> killingPartIds = killingPartLikeRepository.findLikedKillingPartIdsByMember(
            member);
        return SongSwipeResponse.of(currentSong, beforeSongs, afterSongs, killingPartIds);
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
        final Song currentSong = inMemorySongs.getSongById(songId);
        final List<Song> beforeSongs = inMemorySongs.getPrevLikedSongs(currentSong,
            BEFORE_SONGS_COUNT);

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
        final List<Long> likedKillingPartIds =
            killingPartLikeRepository.findLikedKillingPartIdsByMember(member);

        return songs.stream()
            .map(song -> SongResponse.of(song, likedKillingPartIds))
            .toList();
    }

    public List<SongResponse> findSongByIdForAfterSwipe(
        final Long songId,
        final MemberInfo memberInfo
    ) {
        final Song currentSong = inMemorySongs.getSongById(songId);
        final List<Song> afterSongs = inMemorySongs.getNextLikedSongs(currentSong,
            AFTER_SONGS_COUNT);

        return convertToSongResponses(memberInfo, afterSongs);
    }

    @Transactional
    public void saveSongsFromExcelFile(final MultipartFile excel) {
        final List<Song> songs = songDataExcelReader.parseToSongs(excel);
        songs.forEach(this::saveSong);
    }

    public List<HighLikedSongResponse> findSongsByGenre(final String genreName) {
        final Genre genre = Genre.findByName(genreName);
        final List<Song> songs = inMemorySongs.getSortedSongsByGenre(genre, TOP_COUNT);

        return HighLikedSongResponse.ofSongs(songs);
    }

    public SongSwipeResponse findSongsByGenreForSwipe(
        final Long songId,
        final String genreName,
        final MemberInfo memberInfo
    ) {
        final Genre genre = Genre.findByName(genreName);
        final Song currentSong = inMemorySongs.getSongById(songId);
        final List<Song> prevSongs = inMemorySongs.getPrevLikedSongByGenre(currentSong, genre,
            BEFORE_SONGS_COUNT);
        final List<Song> nextSongs = inMemorySongs.getNextLikedSongByGenre(currentSong, genre,
            AFTER_SONGS_COUNT);

        final Authority authority = memberInfo.getAuthority();

        if (authority.isAnonymous()) {
            return SongSwipeResponse.ofUnauthorizedUser(currentSong, prevSongs, nextSongs);
        }

        final Member member = findMemberById(memberInfo.getMemberId());
        final List<Long> likedKillingPartIds =
            killingPartLikeRepository.findLikedKillingPartIdsByMember(member);
        return SongSwipeResponse.of(currentSong, prevSongs, nextSongs, likedKillingPartIds);
    }

    public List<SongResponse> findPrevSongsByGenre(
        final Long songId,
        final String genreName,
        final MemberInfo memberInfo
    ) {
        final Genre genre = Genre.findByName(genreName);
        final Song currentSong = inMemorySongs.getSongById(songId);
        final List<Song> prevSongs = inMemorySongs.getPrevLikedSongByGenre(currentSong, genre,
            BEFORE_SONGS_COUNT);

        return convertToSongResponses(memberInfo, prevSongs);
    }

    public List<SongResponse> findNextSongsByGenre(
        final Long songId,
        final String genreName,
        final MemberInfo memberInfo
    ) {
        final Genre genre = Genre.findByName(genreName);
        final Song currentSong = inMemorySongs.getSongById(songId);
        final List<Song> nextSongs = inMemorySongs.getNextLikedSongByGenre(currentSong, genre,
            AFTER_SONGS_COUNT);

        return convertToSongResponses(memberInfo, nextSongs);
    }
}

package shook.shook.song.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
import shook.shook.song.application.dto.RecentSongCarouselResponse;
import shook.shook.member_part.domain.MemberPart;
import shook.shook.member_part.domain.repository.MemberPartRepository;
import shook.shook.song.application.dto.RecentSongCarouselResponse;
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
    private final MemberPartRepository memberPartRepository;
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

        final List<Song> beforeSongs = inMemorySongs.getPrevLikedSongs(currentSong, BEFORE_SONGS_COUNT);
        final List<Song> afterSongs = inMemorySongs.getNextLikedSongs(currentSong, AFTER_SONGS_COUNT);

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
        final List<Long> killingPartIds = killingPartLikeRepository.findLikedKillingPartIdsByMember(member);

        final List<Song> allSongs = new ArrayList<>(beforeSongs);
        allSongs.add(currentSong);
        allSongs.addAll(afterSongs);

        final Map<Long, MemberPart> memberPartsGroupBySong = makeMemberPartsGroupBySongId(member, allSongs);

        return SongSwipeResponse.of(currentSong, beforeSongs, afterSongs, killingPartIds, memberPartsGroupBySong);
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
        final List<Song> beforeSongs = inMemorySongs.getPrevLikedSongs(currentSong, BEFORE_SONGS_COUNT);

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
        final Map<Long, MemberPart> memberPartsGroupBySongId = makeMemberPartsGroupBySongId(member, songs);

        return songs.stream()
            .map(song -> SongResponse.of(song, likedKillingPartIds,
                                         memberPartsGroupBySongId.getOrDefault(song.getId(), null)))
            .toList();
    }

    private Map<Long, MemberPart> makeMemberPartsGroupBySongId(final Member member, final List<Song> songs) {
        final List<Long> songIds = songs.stream()
            .map(Song::getId)
            .collect(Collectors.toList());
        final List<MemberPart> memberParts = memberPartRepository.findByMemberAndSongIdIn(member, songIds);

        return memberParts.stream()
            .collect(Collectors.toMap(memberPart -> memberPart.getSong().getId(),
                                      memberPart -> memberPart));
    }

    public List<SongResponse> findSongByIdForAfterSwipe(
        final Long songId,
        final MemberInfo memberInfo
    ) {
        final Song currentSong = inMemorySongs.getSongById(songId);
        final List<Song> afterSongs = inMemorySongs.getNextLikedSongs(currentSong, AFTER_SONGS_COUNT);

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
        final List<Song> nextSongs = inMemorySongs.getNextLikedSongByGenre(currentSong, genre, AFTER_SONGS_COUNT);

        return convertToSongSwipeResponse(memberInfo, currentSong, prevSongs, nextSongs);
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
        final List<Song> nextSongs = inMemorySongs.getNextLikedSongByGenre(currentSong, genre, AFTER_SONGS_COUNT);

        return convertToSongResponses(memberInfo, nextSongs);
    }

    public SongResponse findSongById(final Long songId, final MemberInfo memberInfo) {
        final Song song = inMemorySongs.getSongById(songId);
        final Authority authority = memberInfo.getAuthority();

        if (authority.isAnonymous()) {
            return SongResponse.fromUnauthorizedUser(song);
        }

        final Member member = findMemberById(memberInfo.getMemberId());
        final List<Long> likedKillingPartIds =
            killingPartLikeRepository.findLikedKillingPartIdsByMember(member);
        final MemberPart memberPart = memberPartRepository.findByMemberAndSong(member, song)
            .orElse(null);

        return SongResponse.of(song, likedKillingPartIds, memberPart);
    }

    public List<RecentSongCarouselResponse> findRecentRegisteredSongsForCarousel(final Integer size) {
        final List<Song> topSongs = songRepository.findSongsOrderById(PageRequest.of(0, size));

        return topSongs.stream()
            .map(RecentSongCarouselResponse::from)
            .toList();
    }

    public List<RecentSongCarouselResponse> findRecentRegisteredSongsForCarousel(final Integer size) {
        final List<Song> topSongs = songRepository.findSongsOrderById(PageRequest.of(0, size));

        return topSongs.stream()
            .map(RecentSongCarouselResponse::from)
            .toList();
    }
}

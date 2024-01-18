package shook.shook.legacy.song.application;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.legacy.song.application.dto.ArtistResponse;
import shook.shook.legacy.song.application.dto.ArtistWithSongSearchResponse;
import shook.shook.legacy.song.domain.Artist;
import shook.shook.legacy.song.domain.InMemoryArtistSynonyms;
import shook.shook.legacy.song.domain.InMemoryArtistSynonymsGenerator;
import shook.shook.legacy.song.domain.Song;
import shook.shook.legacy.song.domain.repository.ArtistRepository;
import shook.shook.legacy.song.domain.repository.SongRepository;
import shook.shook.legacy.song.domain.repository.dto.SongTotalLikeCountDto;
import shook.shook.legacy.song.exception.ArtistException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArtistSearchService {

    private static final int TOP_SONG_COUNT_OF_ARTIST = 3;

    private final InMemoryArtistSynonyms inMemoryArtistSynonyms;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final InMemoryArtistSynonymsGenerator generator;

    public List<ArtistResponse> searchArtistsByKeyword(final String keyword) {
        final List<Artist> artists = findArtistsStartsWithKeyword(keyword);

        return artists.stream()
            .map(ArtistResponse::from)
            .toList();
    }

    private List<Artist> findArtistsStartsWithKeyword(final String keyword) {
        final List<Artist> artistsFoundByName = inMemoryArtistSynonyms.findAllArtistsNameStartsWith(
            keyword);
        final List<Artist> artistsFoundBySynonym = inMemoryArtistSynonyms.findAllArtistsHavingSynonymStartsWith(
            keyword);

        return removeDuplicateArtistResultAndSortByName(artistsFoundByName, artistsFoundBySynonym);
    }

    private List<Artist> removeDuplicateArtistResultAndSortByName(final List<Artist> firstResult,
                                                                  final List<Artist> secondResult) {
        return Stream.concat(firstResult.stream(), secondResult.stream())
            .distinct()
            .sorted(Comparator.comparing(Artist::getArtistName))
            .toList();
    }

    public List<ArtistWithSongSearchResponse> searchArtistsAndTopSongsByKeyword(
        final String keyword) {
        final List<Artist> artists = findArtistsStartsOrEndsWithKeyword(keyword);

        return artists.stream()
            .map(artist -> ArtistWithSongSearchResponse.of(
                artist,
                getSongsOfArtistSortedByLikeCount(artist).size(),
                getTopSongsOfArtist(artist))
            )
            .toList();
    }

    private List<Artist> findArtistsStartsOrEndsWithKeyword(final String keyword) {
        final List<Artist> artistsFoundByName = inMemoryArtistSynonyms.findAllArtistsNameStartsOrEndsWith(
            keyword);
        final List<Artist> artistsFoundBySynonym = inMemoryArtistSynonyms.findAllArtistsHavingSynonymStartsOrEndsWith(
            keyword);

        return removeDuplicateArtistResultAndSortByName(artistsFoundByName, artistsFoundBySynonym);
    }

    private List<Song> getTopSongsOfArtist(final Artist artist) {
        final List<Song> songs = getSongsOfArtistSortedByLikeCount(artist);
        if (songs.size() < TOP_SONG_COUNT_OF_ARTIST) {
            return songs;
        }

        return songs.subList(0, TOP_SONG_COUNT_OF_ARTIST);
    }

    private List<Song> getSongsOfArtistSortedByLikeCount(final Artist artist) {
        final List<SongTotalLikeCountDto> songsWithTotalLikeCount = songRepository.findAllSongsWithTotalLikeCountByArtist(
            artist);

        return songsWithTotalLikeCount.stream()
            .sorted(Comparator.comparing(SongTotalLikeCountDto::getTotalLikeCount,
                                         Comparator.reverseOrder())
                        .thenComparing(songWithTotalLikeCount -> songWithTotalLikeCount.getSong().getId(),
                                       Comparator.reverseOrder())
            )
            .map(SongTotalLikeCountDto::getSong)
            .toList();
    }

    public ArtistWithSongSearchResponse searchAllSongsByArtist(final long artistId) {
        final Artist artist = findArtistById(artistId);
        final List<Song> songs = getSongsOfArtistSortedByLikeCount(artist);

        return ArtistWithSongSearchResponse.of(artist, songs.size(), songs);
    }

    private Artist findArtistById(final long artistId) {
        return artistRepository.findById(artistId)
            .orElseThrow(() -> new ArtistException.NotExistException(
                Map.of("ArtistId", String.valueOf(artistId))
            ));
    }

    public void updateArtistSynonymFromDatabase() {
        generator.initialize();
    }
}

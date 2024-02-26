package shook.shook.improved.song.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.improved.artist.domain.Artist;
import shook.shook.improved.song.domain.Song;
import shook.shook.improved.song.domain.repository.dto.SongArtists;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SongHighScoredResponse {

    private Long id;

    private String title;

    private String singer;

    private String albumCoverUrl;

    private long totalLikeCount;

    private String genre;

    public static SongHighScoredResponse from(final SongArtists songArtists) {
        final Song song = songArtists.getSong();
        final List<Artist> artists = songArtists.getArtists();

        return new SongHighScoredResponse(song.getId(),
                                          song.getTitle(),
                                          getArtistName(artists),
                                          song.getAlbumCoverUrl(),
                                          song.getScore(),
                                          song.getGenre().name());
    }

    private static String getArtistName(final List<Artist> artists) {
        return artists.stream()
            .map(Artist::getArtistName)
            .collect(Collectors.joining(","));
    }
}

package shook.shook.song.application.dto;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.application.dto.maniadb.SongArtistResponse;

@AllArgsConstructor
@Getter
public class SearchedSongFromManiaDBApiResponse {

    private static final String EMPTY_SINGER = "";
    private static final String SINGER_DELIMITER = ", ";

    private String title;
    private String singer;
    private String albumImageUrl;

    public static SearchedSongFromManiaDBApiResponse from(
        final shook.shook.song.application.dto.maniadb.UnregisteredSongResponse unregisteredSongResponse) {
        if (unregisteredSongResponse.getTrackArtists() == null
            || unregisteredSongResponse.getTrackArtists().getArtists() == null) {
            return new SearchedSongFromManiaDBApiResponse(
                unregisteredSongResponse.getTitle().trim(),
                EMPTY_SINGER,
                unregisteredSongResponse.getAlbum().getImage().trim()
            );
        }

        final String singers = collectToString(unregisteredSongResponse);

        return new SearchedSongFromManiaDBApiResponse(
            unregisteredSongResponse.getTitle().trim(),
            singers,
            unregisteredSongResponse.getAlbum().getImage().trim()
        );
    }

    private static String collectToString(
        final shook.shook.song.application.dto.maniadb.UnregisteredSongResponse unregisteredSongResponse) {
        return unregisteredSongResponse.getTrackArtists().getArtists().stream()
            .map(SongArtistResponse::getName)
            .map(String::trim)
            .collect(Collectors.joining(SINGER_DELIMITER));
    }
}

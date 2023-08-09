package shook.shook.song.application.dto;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.application.dto.maniadb.SearchedSongFromManiaDBApiResponse;
import shook.shook.song.application.dto.maniadb.SongArtistResponse;

@AllArgsConstructor
@Getter
public class UnregisteredSongResponse {

    private static final String EMPTY_SINGER = "";
    private static final String SINGER_DELIMITER = ", ";

    private String title;
    private String singer;
    private String albumImageUrl;

    public static UnregisteredSongResponse from(
        final SearchedSongFromManiaDBApiResponse searchedSongFromManiaDBApiResponse) {
        if (isEmptyArtists(searchedSongFromManiaDBApiResponse)) {
            return new UnregisteredSongResponse(
                searchedSongFromManiaDBApiResponse.getTitle().trim(),
                EMPTY_SINGER,
                searchedSongFromManiaDBApiResponse.getAlbum().getImage().trim()
            );
        }

        final String singers = collectToString(searchedSongFromManiaDBApiResponse);

        return new UnregisteredSongResponse(
            searchedSongFromManiaDBApiResponse.getTitle().trim(),
            singers,
            searchedSongFromManiaDBApiResponse.getAlbum().getImage().trim()
        );
    }

    private static boolean isEmptyArtists(
        final SearchedSongFromManiaDBApiResponse searchedSongFromManiaDBApiResponse) {
        return searchedSongFromManiaDBApiResponse.getTrackArtists() == null
            || searchedSongFromManiaDBApiResponse.getTrackArtists().getArtists() == null;
    }

    private static String collectToString(
        final SearchedSongFromManiaDBApiResponse searchedSongFromManiaDBApiResponse) {
        return searchedSongFromManiaDBApiResponse.getTrackArtists().getArtists().stream()
            .map(SongArtistResponse::getName)
            .map(String::trim)
            .collect(Collectors.joining(SINGER_DELIMITER));
    }
}

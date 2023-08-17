package shook.shook.song.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;

@AllArgsConstructor
@Getter
public class SearchedSongResponse {

    private String title;
    private String singer;
    private String albumImage;
    private int length;

    public static SearchedSongResponse from(final Song song) {
        return new SearchedSongResponse(
            song.getTitle(),
            song.getSinger(),
            null,
            song.getLength()
        );
    }
}

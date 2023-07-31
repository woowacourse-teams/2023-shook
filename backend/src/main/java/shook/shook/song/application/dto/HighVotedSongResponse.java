package shook.shook.song.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;

@AllArgsConstructor
@Getter
public class HighVotedSongResponse {

    private final Long id;
    private final String title;
    private final String singer;
    private final String imageUrl;

    public static HighVotedSongResponse from(final Song song) {
        return new HighVotedSongResponse(
            song.getId(),
            song.getTitle(),
            song.getSinger(),
            song.getImageUrl()
        );
    }

    public static List<HighVotedSongResponse> getList(final List<Song> songs) {
        return songs.stream()
            .map(HighVotedSongResponse::from)
            .toList();
    }
}

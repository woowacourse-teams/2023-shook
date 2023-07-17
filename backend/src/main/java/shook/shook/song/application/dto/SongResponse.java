package shook.shook.song.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;

@AllArgsConstructor
@Getter
public class SongResponse {

    private Long id;
    private String title;
    private String videoUrl;
    private String singer;
    private int length;
    private LocalDateTime createdAt;

    public static SongResponse from(final Song song) {
        return new SongResponse(
            song.getId(),
            song.getTitle(),
            song.getVideoUrl(),
            song.getSinger(),
            song.getLength(),
            song.getCreatedAt()
        );
    }
}

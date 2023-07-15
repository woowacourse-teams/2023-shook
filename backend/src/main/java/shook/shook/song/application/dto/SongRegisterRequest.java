package shook.shook.song.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.song.domain.Song;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class SongRegisterRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String videoUrl;

    @NotBlank
    private String singer;

    @NotBlank
    private Integer length;

    public Song getSong() {
        return new Song(title, videoUrl, singer, length);
    }
}

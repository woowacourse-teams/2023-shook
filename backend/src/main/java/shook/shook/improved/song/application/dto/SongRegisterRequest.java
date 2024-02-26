package shook.shook.improved.song.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SongRegisterRequest {

    private String title;
    private String videoId;
    private String imageUrl;
    private List<Long> artistIds;
    private int length;
    private String genre;

}


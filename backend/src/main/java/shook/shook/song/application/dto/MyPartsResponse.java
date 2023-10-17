package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.my_part.domain.MemberPart;
import shook.shook.song.domain.Song;

@Schema(description = "마이파트 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPartsResponse {

    private Long songId;

    private String albumCoverUrl;

    private String title;

    private int start;

    private int length;


    public static MyPartsResponse of(final Song song, final MemberPart memberPart) {
        return new MyPartsResponse(
                song.getId(),
                song.getAlbumCoverUrl(),
                song.getTitle(),
                memberPart.getStartSecond(),
                memberPart.getLength());
    }
}

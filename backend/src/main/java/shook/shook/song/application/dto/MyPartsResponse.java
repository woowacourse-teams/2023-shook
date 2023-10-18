package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.member_part.domain.MemberPart;
import shook.shook.song.domain.Song;

@Schema(description = "마이 파트 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPartsResponse {

    @Schema(description = "노래 id", example = "1")
    private Long songId;

    @Schema(description = "노래 앨범 커버 이미지 url", example = "https://image.com/album-cover.jpg")
    private String albumCoverUrl;

    @Schema(description = "노래 제목", example = "제목")
    private String title;

    @Schema(description = "마이 파트 시작 시간", example = "0")
    private int start;

    @Schema(description = "마이 파트 길이", example = "10")
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

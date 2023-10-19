package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.member_part.domain.MemberPart;
import shook.shook.song.domain.Song;

@Schema(description = "내 파트 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPartsResponse {

    @Schema(description = "노래 id", example = "1")
    private final Long songId;

    @Schema(description = "노래 제목", example = "제목")
    private final String title;

    @Schema(description = "노래 비디오 id", example = "4")
    private final String songVideoId;

    @Schema(description = "가수 이름", example = "가수")
    private final String singer;

    @Schema(description = "앨범 자켓 이미지 url", example = "https://image.com/album_cover.jpg")
    private final String albumCoverUrl;

    @Schema(description = "내 파트 id", example = "1")
    private final Long partId;

    @Schema(description = "내 파트 시작 초", example = "30")
    private final int start;

    @Schema(description = "내 파트 끝 초", example = "40")
    private final int end;

    public static MyPartsResponse of(final Song song, final MemberPart memberPart) {
        return new MyPartsResponse(
            song.getId(),
            song.getTitle(),
            song.getVideoId(),
            song.getArtistName(),
            song.getAlbumCoverUrl(),
            memberPart.getId(),
            memberPart.getStartSecond(),
            memberPart.getEndSecond()
        );
    }
}

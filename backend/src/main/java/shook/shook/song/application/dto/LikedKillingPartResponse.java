package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

@Schema(description = "좋아요한 킬링파트 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LikedKillingPartResponse {

    @Schema(description = "노래 id", example = "1")
    private final Long songId;

    @Schema(description = "노래 제목", example = "제목")
    private final String title;

    @Schema(description = "가수 이름", example = "가수")
    private final String singer;

    @Schema(description = "앨범 자켓 이미지 url", example = "https://image.com/album_cover.jpg")
    private final String albumCoverUrl;

    @Schema(description = "킬링파트 id", example = "1")
    private final Long partId;

    @Schema(description = "킬링파트 시작 초", example = "30")
    private final int start;

    @Schema(description = "킬링파트 끝 초", example = "40")
    private final int end;

    public static LikedKillingPartResponse of(final Song song, final KillingPart killingPart) {
        return new LikedKillingPartResponse(
            song.getId(),
            song.getTitle(),
            song.getArtistName(),
            song.getAlbumCoverUrl(),
            killingPart.getId(),
            killingPart.getStartSecond(),
            killingPart.getEndSecond()
        );
    }
}

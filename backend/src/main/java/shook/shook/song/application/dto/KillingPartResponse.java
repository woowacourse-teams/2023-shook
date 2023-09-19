package shook.shook.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.member.domain.Member;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.killingpart.KillingPart;

@Schema(description = "킬링파트 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class KillingPartResponse {

    @Schema(description = "킬링파트 id", example = "1")
    private final Long id;

    @Schema(description = "킬링파트 순위", example = "1")
    private final int rank;

    @Schema(description = "좋아요 개수", example = "10")
    private final long likeCount;

    @Schema(description = "킬링파트 시작 초", example = "30")
    private final int start;

    @Schema(description = "킬링파트 끝 초", example = "40")
    private final int end;

    @Schema(description = "킬링파트 영상 Url", example = "https://www.youtube.com/watch?v=asdfasdfasdf&start=30&end=40")
    private final String partVideoUrl;

    @Schema(description = "킬링파트 길이", example = "10")
    private final int partLength;

    @Schema(description = "좋아요 여부", example = "true")
    private final boolean likeStatus;

    public static KillingPartResponse of(
        final Song song,
        final KillingPart killingPart,
        final int rank,
        final Member member
    ) {
        return new KillingPartResponse(
            killingPart.getId(),
            rank,
            killingPart.getLikeCount(),
            killingPart.getStartSecond(),
            killingPart.getEndSecond(),
            song.getPartVideoUrl(killingPart),
            killingPart.getLength(),
            killingPart.isLikedByMember(member)
        );
    }

    public boolean getLikeStatus() {
        return likeStatus;
    }
}

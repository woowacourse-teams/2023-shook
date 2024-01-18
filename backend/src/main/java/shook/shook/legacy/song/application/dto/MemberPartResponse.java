package shook.shook.legacy.song.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shook.shook.legacy.member_part.domain.MemberPart;

@Schema(description = "멤버 파트 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberPartResponse {

    @Schema(description = "멤버 파트 id", example = "1")
    private final Long id;

    @Schema(description = "멤버 파트 시작 초", example = "30")
    private final int start;

    @Schema(description = "멤버 파트 끝 초", example = "40")
    private final int end;

    @Schema(description = "멤버 파트 길이", example = "10")
    private final int partLength;

    public static MemberPartResponse from(final MemberPart memberPart) {
        if (memberPart == null) {
            return null;
        }

        return new MemberPartResponse(
            memberPart.getId(),
            memberPart.getStartSecond(),
            memberPart.getEndSecond(),
            memberPart.getLength()
        );
    }
}

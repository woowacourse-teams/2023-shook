package shook.shook.member.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shook.shook.member.domain.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberRegisterRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    public Member toMember() {
        return new Member(email, nickname);
    }

}

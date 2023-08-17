package shook.shook.auth.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReissueAccessTokenResponse {

    private String accessToken;
}

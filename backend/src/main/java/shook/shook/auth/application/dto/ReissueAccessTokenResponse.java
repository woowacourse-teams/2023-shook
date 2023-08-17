package shook.shook.auth.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
//클래스명도 조금 더 확실하게 바꿔도 좋을 것 같아요
public class ReissueAccessTokenResponse {

    private String accessToken;
}

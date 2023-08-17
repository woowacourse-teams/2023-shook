package shook.shook.auth.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
//TokenInfo 는 토큰의 정보라는 뜻같아서 여러 의미로 해석될 수 있을 것 같아요 access, refresh 페어로 쓰이는 토큰 두개를 뜻하는 네이밍은 어떨까요??
public class TokenPair {

    private String accessToken;
    private String refreshToken;
}

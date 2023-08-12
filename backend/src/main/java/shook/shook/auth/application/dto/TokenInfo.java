package shook.shook.auth.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenInfo {

    private String accessToken;
    private String refreshToken;
}

package shook.shook.auth.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenPair {

    private String accessToken;
    private String refreshToken;
}

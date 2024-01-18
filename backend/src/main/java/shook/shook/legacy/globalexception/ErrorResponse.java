package shook.shook.legacy.globalexception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ErrorResponse {

    private int code;
    private String message;

    public static ErrorResponse from(final CustomException customException) {
        return new ErrorResponse(customException.getCode(), customException.getMessage());
    }
}

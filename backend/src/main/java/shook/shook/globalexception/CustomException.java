package shook.shook.globalexception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CustomException extends RuntimeException {

    private final int code;
    private final String message;

    public CustomException(final ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}

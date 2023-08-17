package shook.shook.globalexception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ErrorResponse {

    private int code;
    private String message;

    public static ErrorResponse from(final CustomException customException) {
        return new ErrorResponse(customException.getCode(), customException.getMessage());
    }

    public static ErrorResponse fromMethodArgumentException(
        final CustomException exception, final FieldError fieldError
    ) {
        final String field = fieldError.getField();
        final String message = fieldError.getDefaultMessage();
        return new ErrorResponse(exception.getCode(),
            String.format(exception.getMessage(), field, message));
    }
}

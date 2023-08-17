package shook.shook.globalexception;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.FieldError;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class CustomException extends RuntimeException {

    private static final String EXCEPTION_INFO_BRACKET = "{ %s }";
    private static final String PROPERTY_VALUE = " Property: %s, Value: %s ";
    private static final String VALUE_DELIMITER = "/";

    private final int code;
    private final String message;
    private final Map<String, String> inputValuesByProperty;

    protected CustomException(final ErrorCode errorCode) {
        this(errorCode, Collections.emptyMap());
    }

    protected CustomException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.inputValuesByProperty = inputValuesByProperty;
    }

    public static CustomException of(
        final ErrorCode errorCode,
        final Map<String, String> propertyValues
    ) {
        return new CustomException(errorCode, propertyValues);
    }

    public static CustomException from(final ErrorCode errorCode) {
        return new CustomException(errorCode);
    }

    public static CustomException fromFieldError(final FieldError fieldError) {
        final String inputValue = (String) fieldError.getRejectedValue();
        final String errorMessage = fieldError.getDefaultMessage();
        final String field = fieldError.getField();

        return new CustomException(
            ErrorCode.REFRESH_TOKEN_NOT_FOUND_EXCEPTION.getCode(),
            errorMessage,
            Map.of(field, inputValue)
        );
    }

    public String getErrorPropertyValue() {
        final String propertyWithValue = inputValuesByProperty.entrySet()
            .stream()
            .map(entry -> String.format(PROPERTY_VALUE, entry.getKey(), entry.getValue()))
            .collect(Collectors.joining(VALUE_DELIMITER));

        return String.format(EXCEPTION_INFO_BRACKET, propertyWithValue);
    }
}

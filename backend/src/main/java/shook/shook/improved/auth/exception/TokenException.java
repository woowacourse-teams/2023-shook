package shook.shook.improved.auth.exception;

import java.util.Map;
import shook.shook.improved.globalexception.CustomException;
import shook.shook.improved.globalexception.ErrorCode;

public class TokenException extends CustomException {

    public TokenException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class NotIssuedTokenException extends TokenException {

        public NotIssuedTokenException() {
            super(ErrorCode.NOT_ISSUED_TOKEN);
        }

        public NotIssuedTokenException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.NOT_ISSUED_TOKEN, inputValuesByProperty);
        }
    }

    public static class ExpiredTokenException extends TokenException {

        public ExpiredTokenException() {
            super(ErrorCode.EXPIRED_TOKEN);
        }

        public ExpiredTokenException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EXPIRED_TOKEN, inputValuesByProperty);
        }
    }

    public static class RefreshTokenNotFoundException extends TokenException {

        public RefreshTokenNotFoundException() {
            super(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        public RefreshTokenNotFoundException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.INVALID_REFRESH_TOKEN, inputValuesByProperty);
        }
    }

    public static class TokenPairNotMatchingException extends TokenException {

        public TokenPairNotMatchingException() {
            super(ErrorCode.TOKEN_PAIR_NOT_MATCHING_EXCEPTION);
        }

        public TokenPairNotMatchingException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOKEN_PAIR_NOT_MATCHING_EXCEPTION, inputValuesByProperty);
        }
    }
}

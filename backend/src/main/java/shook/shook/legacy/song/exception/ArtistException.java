package shook.shook.legacy.song.exception;

import java.util.Map;
import shook.shook.legacy.globalexception.CustomException;
import shook.shook.legacy.globalexception.ErrorCode;

public class ArtistException extends CustomException {

    public ArtistException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public ArtistException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class NullOrEmptyProfileUrlException extends ArtistException {

        public NullOrEmptyProfileUrlException() {
            super(ErrorCode.EMPTY_ARTIST_PROFILE_URL);
        }

        public NullOrEmptyProfileUrlException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_ARTIST_PROFILE_URL, inputValuesByProperty);
        }
    }

    public static class TooLongProfileUrlException extends ArtistException {

        public TooLongProfileUrlException() {
            super(ErrorCode.TOO_LONG_ARTIST_PROFILE_URL);
        }

        public TooLongProfileUrlException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_ARTIST_PROFILE_URL, inputValuesByProperty);
        }
    }

    public static class NullOrEmptyNameException extends ArtistException {

        public NullOrEmptyNameException() {
            super(ErrorCode.EMPTY_SINGER_NAME);
        }

        public NullOrEmptyNameException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_SINGER_NAME, inputValuesByProperty);
        }
    }

    public static class TooLongNameException extends ArtistException {

        public TooLongNameException() {
            super(ErrorCode.TOO_LONG_SINGER_NAME);
        }

        public TooLongNameException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_SINGER_NAME, inputValuesByProperty);
        }
    }

    public static class NullOrEmptySynonymException extends ArtistException {

        public NullOrEmptySynonymException() {
            super(ErrorCode.EMPTY_ARTIST_SYNONYM);
        }

        public NullOrEmptySynonymException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_ARTIST_SYNONYM, inputValuesByProperty);
        }
    }

    public static class TooLongSynonymException extends ArtistException {

        public TooLongSynonymException() {
            super(ErrorCode.TOO_LONG_ARTIST_SYNONYM);
        }

        public TooLongSynonymException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_ARTIST_SYNONYM, inputValuesByProperty);
        }
    }

    public static class NotExistException extends ArtistException {

        public NotExistException() {
            super(ErrorCode.ARTIST_NOT_EXIST);
        }

        public NotExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.ARTIST_NOT_EXIST, inputValuesByProperty);
        }
    }
}

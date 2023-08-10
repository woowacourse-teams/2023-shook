package shook.shook.song.exception;

public class ExternalApiException extends RuntimeException {

    public static class EmptyResultException extends ExternalApiException {

        public EmptyResultException() {
            super();
        }
    }

    public static class ManiaDBServerException extends ExternalApiException {

        public ManiaDBServerException() {
            super();
        }
    }

    public static class ManiaDBClientException extends ExternalApiException {

        public ManiaDBClientException() {
            super();
        }
    }
}

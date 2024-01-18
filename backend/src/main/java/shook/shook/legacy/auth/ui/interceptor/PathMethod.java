package shook.shook.legacy.auth.ui.interceptor;

public enum PathMethod {

    POST,
    GET,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE,
    HEAD;

    public boolean match(final String method) {
        return name().equalsIgnoreCase(method);
    }
}

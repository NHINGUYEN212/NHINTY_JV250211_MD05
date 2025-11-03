package md05_ontap.exception;

public class HttpNotFound extends RuntimeException {
    public HttpNotFound(String message) {
        super(message);
    }
}

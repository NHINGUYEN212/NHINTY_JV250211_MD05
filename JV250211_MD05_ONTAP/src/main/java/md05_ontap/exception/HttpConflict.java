package md05_ontap.exception;

public class HttpConflict extends RuntimeException{
    public HttpConflict(String message){
        super(message);
    }
}

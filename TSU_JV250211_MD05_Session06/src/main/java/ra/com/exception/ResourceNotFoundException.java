package ra.com.exception;

import org.aspectj.bridge.IMessage;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

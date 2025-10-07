package uz.pdp.startup.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class RestException extends RuntimeException {
    private final int statusCode;

    public RestException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public static RestException error(String message) {
        return new RestException(message, 400);
    }

    public static RestException notFound(String message, Object id) {
        return new RestException(message + (id != null ? " with id " + id : ""), 404);
    }

    public static RestException badRequest(String message) {
        return new RestException(message, 400);
    }

    public static RestException restThrow(String message, HttpStatus status) {
        return new RestException(message, status.value());
    }
}

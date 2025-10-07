package uz.pdp.startup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import uz.pdp.startup.payload.ErrorDTO;

import java.sql.Timestamp;

@RestControllerAdvice
public class GlobalExceptionHandler2 {

    // ✅ Custom RestException — loyihadagi xatolar uchun
    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorDTO> handleRestException(RestException e) {
        return buildResponse(
                e.getMessage(),
                e.getStatusCode(),
                HttpStatus.valueOf(e.getStatusCode())
        );
    }

    // ✅ ResponseStatusException (masalan: unauthorized, conflict va h.k.)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDTO> handleResponseStatusException(ResponseStatusException e) {
        return buildResponse(
                e.getReason() != null ? e.getReason() : e.getMessage(),
                e.getStatusCode().value(),
                HttpStatus.valueOf(e.getStatusCode().value())
        );
    }

    // ✅ UsernameNotFoundException, IllegalArgumentException, va boshqa umumiy xatolar
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception e) {
        return buildResponse(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // ✅ Response builder
    private ResponseEntity<ErrorDTO> buildResponse(String message, int status, HttpStatus httpStatus) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Timestamp(System.currentTimeMillis()),
                message,
                status
        );

        return ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorDTO);
    }
}

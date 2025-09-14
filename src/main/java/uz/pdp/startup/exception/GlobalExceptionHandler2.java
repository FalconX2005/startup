package uz.pdp.startup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.startup.payload.ErrorDTO;

import java.sql.Timestamp;

@RestControllerAdvice
public class GlobalExceptionHandler2 {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ErrorDTO> handle(RestException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Timestamp(System.currentTimeMillis()),
                e.getMessage(),
                e.getStatusCode()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.valueOf(e.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handle(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Timestamp(System.currentTimeMillis()),
                e.getMessage(),
                500
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

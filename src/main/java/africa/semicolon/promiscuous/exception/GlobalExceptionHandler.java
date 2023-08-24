package africa.semicolon.promiscuous.exception;
import africa.semicolon.promiscuous.dto.reponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(value = {PromiscuousException.class})
    @ResponseStatus(BAD_REQUEST)

    public ResponseEntity<?> handler(PromiscuousException exception){
      var response=  ApiResponse.builder().data(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {IOException.class})
    @ResponseStatus(BAD_REQUEST)

    public ResponseEntity<?> handler(IOException exception){
        var response=  ApiResponse.builder().data(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }



}

package br.com.compassuol.pb.challenge.apigateway.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public AccessDeniedException(HttpStatus status,String message) {
        this.message =message;
        this.status=status;
    }

    public AccessDeniedException(HttpStatus status, String message,String message1) {
        super(message);
        this.status = status;
        this.message = message1;

    }
}

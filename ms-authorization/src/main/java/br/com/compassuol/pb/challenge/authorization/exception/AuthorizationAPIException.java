package br.com.compassuol.pb.challenge.authorization.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public AuthorizationAPIException(HttpStatus status, String message) {
        this.message =message;
        this.status=status;
    }

    public AuthorizationAPIException(HttpStatus status, String message, String message1) {
        super(message);
        this.status = status;
        this.message = message1;

    }
}

package br.com.compassuol.pb.challenge.apigateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public ProductAPIException(HttpStatus status,String message) {
        this.message =message;
        this.status=status;
    }

    public ProductAPIException(HttpStatus status, String message,String message1) {
        super(message);
        this.status = status;
        this.message = message1;

    }
}

package br.com.compassuol.pb.challenge.notification.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDto {

    @Email(message = "The From email must be valid")
    private String fromEmail;

    @NotBlank(message = "Must have the from name field")
    private String fromName;

    //@Email(message = "The reply email must be valid")
    private String replyTo;

    @NotBlank(message = "Must have the To field")
    private String to;

    @NotBlank(message = "Must have the subject field")
    private String subject;

    @NotBlank(message = "Must have the body field")
    private String body;
    @NotBlank(message = "Must have the content field")
    private String contentType;
}

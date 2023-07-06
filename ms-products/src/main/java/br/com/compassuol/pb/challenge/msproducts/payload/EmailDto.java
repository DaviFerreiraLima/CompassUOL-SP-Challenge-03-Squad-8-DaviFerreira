package br.com.compassuol.pb.challenge.msproducts.payload;

import lombok.*;

@Data
public class EmailDto {
    private String fromEmail;
    private String fromName;
    private String replyTo;
    private String to;
    private String subject;
    private String body;
    private String contentType;
}

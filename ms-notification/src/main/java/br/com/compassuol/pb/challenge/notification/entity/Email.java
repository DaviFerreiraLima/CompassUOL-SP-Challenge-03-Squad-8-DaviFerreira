package br.com.compassuol.pb.challenge.notification.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "from_email")
    private String fromEmail;
    @Column(name = "from_name")
    private String fromName;
    @Column(name = "reply_to")
    private String replyTo;
    @Column(name = "to_email")
    private String to;
    private String subject;
    private String body;
    @Column(name = "content_type")
    private String contentType;

}

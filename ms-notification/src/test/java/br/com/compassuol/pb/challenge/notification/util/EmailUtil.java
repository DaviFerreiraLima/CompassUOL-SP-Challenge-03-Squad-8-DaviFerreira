package br.com.compassuol.pb.challenge.notification.util;

import br.com.compassuol.pb.challenge.notification.entity.Email;
import br.com.compassuol.pb.challenge.notification.payload.EmailDto;

public class EmailUtil {

    public static Email createEmail(){
        var email = new Email();
        email.setFromEmail("compass@gmail.com");
        email.setTo("compass1@gmail.com");
        email.setId(1L);
        email.setSubject("CREATED");
        email.setBody("Thats a great message");
        email.setReplyTo("none@gmail.com");
        email.setFromName("compass");
        email.setContentType("text/plain");
        return email;
    }

    public static EmailDto createEmailDto(){
        var email = new EmailDto();
        email.setFromEmail("compass@gmail.com");
        email.setTo("compass1@gmail.com");
        email.setSubject("CREATED");
        email.setBody("Thats a great message");
        email.setReplyTo("compass1@gmail.com");
        email.setFromName("compass");
        email.setContentType("text/plain");
        return email;
    }
}

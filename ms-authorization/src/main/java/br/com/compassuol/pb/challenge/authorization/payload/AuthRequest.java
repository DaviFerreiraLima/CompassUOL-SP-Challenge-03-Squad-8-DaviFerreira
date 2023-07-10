package br.com.compassuol.pb.challenge.authorization.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {

    @Email(message = "The email must be valid")
    private String email;

    @NotBlank(message = "User password should not be empty")
    @Size(min = 6, message = "User password should have at least 6 characters")
    private String password;

}

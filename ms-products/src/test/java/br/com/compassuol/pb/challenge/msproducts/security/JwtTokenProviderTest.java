package br.com.compassuol.pb.challenge.msproducts.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;


    @Test
    public void validateTokenSuccess() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "bob@gmail.com",
                null,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        String token = jwtTokenProvider.generateJwtToken(authentication);

        String username = jwtTokenProvider.getUsername(token);

       assertEquals("bob@gmail.com", username);
    }


    @Test
    public void validateTokenInvalidToken() {
        String token = "invalid";

       assertThrows(Exception.class, () -> jwtTokenProvider.validateToken(token));
    }



    @Test
    public void validateTokenInvalidTokenTampered() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "emma@gmail.com",
                null,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
        String token = jwtTokenProvider.generateJwtToken(authentication);


        String tamperedToken = token + "X";

        assertThrows(Exception.class, () -> jwtTokenProvider.validateToken(tamperedToken));
    }
}

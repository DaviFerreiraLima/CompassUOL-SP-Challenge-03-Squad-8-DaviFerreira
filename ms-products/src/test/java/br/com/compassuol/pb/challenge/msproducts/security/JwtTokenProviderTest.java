package br.com.compassuol.pb.challenge.msproducts.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;
    private static final String VALID_TOKEN = "valid-token";
    private static final String INVALID_TOKEN = "invalid-token";
    private static final String EXPIRED_TOKEN = "expired-token";

    @BeforeEach
    public void setup() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    public void testGetUsername_ValidToken_ReturnsUsername() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "bob@gmail.com",    // Nome de usuário
                null,           // Senha (não utilizada neste exemplo)
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))  // Autoridade/Permissão
        );
        String token = jwtTokenProvider.generateJwtToken(authentication);

        String username = jwtTokenProvider.getUsername(token);

        Assertions.assertEquals("bob@gmail.com", username);
    }




    @Test
    public void testValidateToken_ValidToken_ReturnsTrue() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "alice@gmail.com",  // Nome de usuário
                null,               // Senha (não utilizada neste exemplo)
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))  // Autoridade/Permissão
        );
        String token = jwtTokenProvider.generateJwtToken(authentication);

        boolean isValid = jwtTokenProvider.validateToken(token);

        Assertions.assertTrue(isValid);
    }

    @Test
    public void testValidateToken_InvalidToken_ThrowsException() {
        String token = "invalid-token";

        Assertions.assertThrows(Exception.class, () -> jwtTokenProvider.validateToken(token));
    }



    @Test
    public void testValidateToken_TamperedToken_ThrowsException() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "emma@gmail.com",   // Nome de usuário
                null,               // Senha (não utilizada neste exemplo)
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))  // Autoridade/Permissão
        );
        String token = jwtTokenProvider.generateJwtToken(authentication);

        // Alterar o token (adicionando um caractere ao final)
        String tamperedToken = token + "X";

        Assertions.assertThrows(Exception.class, () -> jwtTokenProvider.validateToken(tamperedToken));
    }
}

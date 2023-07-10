package br.com.compassuol.pb.challenge.authorization.service;

import br.com.compassuol.pb.challenge.authorization.config.JwtTokenProvider;
import br.com.compassuol.pb.challenge.authorization.payload.AuthRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void login() {
        var authRequest = new AuthRequest("daviferreilima@gmail.com", "senha123456");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "daviferreilima@gmail.com",
                null,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_OPERATOR")));
        var token = "token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateJwtToken(any(Authentication.class))).thenReturn(token);

        authService.login(authRequest);

        verify(jwtTokenProvider).generateJwtToken(authentication);

    }
}
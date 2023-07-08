package br.com.compassuol.pb.challenge.authorization.service;

import br.com.compassuol.pb.challenge.authorization.config.JwtTokenProvider;
import br.com.compassuol.pb.challenge.authorization.payload.AuthRequest;
import br.com.compassuol.pb.challenge.authorization.repository.UserCredentialRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager, UserCredentialRepository repository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(AuthRequest authRequest){
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateJwtToken(authentication);
    }

    public void validateToken(String token){
        jwtTokenProvider.validateToken(token);
    }
}

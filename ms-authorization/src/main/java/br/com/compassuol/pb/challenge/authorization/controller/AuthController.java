package br.com.compassuol.pb.challenge.authorization.controller;

import br.com.compassuol.pb.challenge.authorization.exception.ProductAPIException;
import br.com.compassuol.pb.challenge.authorization.payload.AuthRequest;
import br.com.compassuol.pb.challenge.authorization.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController

public class AuthController {

    private AuthService authService;

    private AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        var authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        if (authenticate.isAuthenticated()){
            return authService.login(authRequest);
        }else {
            return " invalid authentication";
        }
    }

}

package br.com.compassuol.pb.challenge.authorization.controller;

import br.com.compassuol.pb.challenge.authorization.payload.AuthRequest;
import br.com.compassuol.pb.challenge.authorization.service.AuthService;
import br.com.compassuol.pb.challenge.authorization.util.ControllerUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {AuthController.class})
class AuthControllerTest {

    @MockBean
    private AuthService authService;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getToken() throws Exception {
        var authRequest = new AuthRequest("daviferreilima@gmail.com","compassinho123");
        var authenticate = mock(Authentication.class);
        var token ="token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticate);
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authService.login(any(AuthRequest.class))).thenReturn(token);


        String requestBody = ControllerUtils.mapToString(authRequest);

        var result =
                mockMvc.perform(post("/token")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }



    @Test
    void getTokenInvalid() throws Exception {
        var authRequest = new AuthRequest("daviferreilima@gmail.com", "compassinho123");
        var authenticate = mock(Authentication.class);
        var token = "token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticate);
        when(authenticate.isAuthenticated()).thenReturn(false);

        String requestBody = ControllerUtils.mapToString(authRequest);

        var result =
                mockMvc.perform(post("/token")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Invalid Authentication", response.getContentAsString());
    }
}
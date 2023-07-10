package br.com.compassuol.pb.challenge.authorization.config;

import br.com.compassuol.pb.challenge.authorization.entity.UserCredential;
import br.com.compassuol.pb.challenge.authorization.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.authorization.repository.UserCredentialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @Test
    void loadUserByUsernameSuccess() {
        String email = "bob@gmail.com";
        String password = "password";
        long id = 1L;


        var user = new UserCredential(id,email, password);
        when(userCredentialRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());


        verify(userCredentialRepository).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsernameResourceNotFoundException() {
        String email = "nonexisting@gmail.com";
        when(userCredentialRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(email));

        verify(userCredentialRepository).findByEmail(email);
    }
}
package br.com.compassuol.pb.challenge.msproducts.security;

import br.com.compassuol.pb.challenge.msproducts.entity.Role;
import br.com.compassuol.pb.challenge.msproducts.entity.User;
import br.com.compassuol.pb.challenge.msproducts.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLoadUserByUsernameSuccess() {
        String email = "bob@gmail.com";
        String password = "password";
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L,"ROLE_USER"));

        User user = new User(1L,"BOB","BOB",email, password, roles);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        Assertions.assertEquals(email, userDetails.getUsername());
        Assertions.assertEquals(password, userDetails.getPassword());

        Set<GrantedAuthority> expectedAuthorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        Assertions.assertEquals(expectedAuthorities, userDetails.getAuthorities());

        verify(userRepository).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsernameUsernameNotFoundException() {
        String email = "nonexisting@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(email));

        verify(userRepository).findByEmail(email);
    }
}

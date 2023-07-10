package br.com.compassuol.pb.challenge.authorization.config;

import br.com.compassuol.pb.challenge.authorization.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.authorization.repository.UserCredentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserCredentialRepository userCredentialRepository;

    public CustomUserDetailsService(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws ResourceNotFoundException {

        var user = userCredentialRepository.findByEmail(email);

        return user.map(CustomUserDetails::new).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

    }
}
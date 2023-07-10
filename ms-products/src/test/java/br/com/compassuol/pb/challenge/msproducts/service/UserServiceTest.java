package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.entity.Role;
import br.com.compassuol.pb.challenge.msproducts.entity.User;
import br.com.compassuol.pb.challenge.msproducts.exception.ProductAPIException;
import br.com.compassuol.pb.challenge.msproducts.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.msproducts.publisher.RabbitMQProducer;
import br.com.compassuol.pb.challenge.msproducts.repository.RoleRepository;
import br.com.compassuol.pb.challenge.msproducts.repository.UserRepository;
import br.com.compassuol.pb.challenge.msproducts.utils.UserUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RabbitMQProducer producer;
    @Spy
    private ModelMapper mapper;
    @InjectMocks
    private UserService userService;


    @Test
    void createUser() {
        var userDto = UserUtils.createUserDto();
        var user = UserUtils.createUser();
        var role = new Role();
        String contentType = "text/plain";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.createUser(userDto,contentType);

        assertEquals(userDto.getEmail(),response.getEmail());
    }

    @Test
    void CreateUserProductAPIException(){
        var userDto = UserUtils.createUserDto();
        var user = UserUtils.createUser();
        String contentType = "text/plain";
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(ProductAPIException.class,()-> userService.createUser(userDto,contentType));
    }
    @Test
    void getUserByIdSuccess() {
        var userDto = UserUtils.createUserDto();
        var user = UserUtils.createUser();
        long userId = 1L;

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        var response = userService.getUserById(userId);

        assertEquals(userId,response.getId());
    }

    @Test
    void getUserByIdResourceNotFoundException() {
        var userDto = UserUtils.createUserDto();
        long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()-> userService.getUserById(userId));
    }
    @Test
    void updateUserSuccess() {
        long userId = 1L;
        var user = UserUtils.createUser();
        var userDto = UserUtils.createUserDto();
        String contentType = "text/plain";
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.updateUser(userId,userDto,contentType);

        assertEquals(userDto.getEmail(),response.getEmail());
    }

    @Test
    void updateUserResourceNotFoundException() {

        long userId = 1L;
        var userDto = UserUtils.createUserDto();
        String contentType = "text/plain";
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() -> userService.updateUser(userId,userDto,contentType));
    }

    @Test
    void updateUserProductApiException() {

        long userId = 1L;
        var user = UserUtils.createUser();
        var userDto = UserUtils.createUserDto();
        String contentType = "text/plain";
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(ProductAPIException.class,()-> userService.updateUser(userId,userDto,contentType));
    }
}
package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.entity.Role;
import br.com.compassuol.pb.challenge.msproducts.entity.User;
import br.com.compassuol.pb.challenge.msproducts.exception.ProductAPIException;
import br.com.compassuol.pb.challenge.msproducts.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.msproducts.publisher.RabbitMQProducer;
import br.com.compassuol.pb.challenge.msproducts.repository.RoleRepository;
import br.com.compassuol.pb.challenge.msproducts.repository.UserRepository;
import br.com.compassuol.pb.challenge.msproducts.utils.userUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


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
        var userDto = userUtils.createUserDto();
        var user = userUtils.createUser();


        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.createUser(userDto);

        assertEquals(userDto.getEmail(),response.getEmail());
    }
    @Test
    void CreateUserProductAPIException(){
        var userDto = userUtils.createUserDto();
        var user = userUtils.createUser();
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(ProductAPIException.class,()-> userService.createUser(userDto));
    }
    @Test
    void getUserByIdSuccess() {
        var userDto = userUtils.createUserDto();
        var user = userUtils.createUser();
        long userId = 1L;

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        var response = userService.getUserById(userId);

        assertEquals(userId,response.getId());
    }

    @Test
    void getUserByIdResourceNotFoundException() {
        var userDto = userUtils.createUserDto();
        long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()-> userService.getUserById(userId));
    }
    @Test
    void updateUserSuccess() {
        long userId = 1L;
        var user = userUtils.createUser();
        var userDto = userUtils.createUserDto();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.updateUser(userId,userDto);

        assertEquals(userDto.getEmail(),response.getEmail());
    }

    @Test
    void updateUserResourceNotFoundException() {

        long userId = 1L;
        var userDto = userUtils.createUserDto();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() -> userService.updateUser(userId,userDto));
    }
}
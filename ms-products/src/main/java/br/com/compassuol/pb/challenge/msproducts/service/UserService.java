package br.com.compassuol.pb.challenge.msproducts.service;


import br.com.compassuol.pb.challenge.msproducts.entity.Role;
import br.com.compassuol.pb.challenge.msproducts.entity.User;
import br.com.compassuol.pb.challenge.msproducts.exception.ProductAPIException;
import br.com.compassuol.pb.challenge.msproducts.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.msproducts.payload.EmailDto;
import br.com.compassuol.pb.challenge.msproducts.payload.UserDto;
import br.com.compassuol.pb.challenge.msproducts.publisher.RabbitMQProducer;
import br.com.compassuol.pb.challenge.msproducts.repository.RoleRepository;
import br.com.compassuol.pb.challenge.msproducts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper mapper;
    private PasswordEncoder passwordEncoder;
    private RabbitMQProducer producer;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper mapper, RabbitMQProducer producer,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.producer = producer;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserDto userDto){
        var existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()){
            throw new ProductAPIException(HttpStatus.BAD_REQUEST,"This User email is already registered");
        }
        var roles = getRolesByIds(userDto.getRoles());

        var newUser = mapper.map(userDto, User.class);
        newUser.setRoles(roles);
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        var email = buildEmail(newUser.getEmail());
        email.setSubject("CREATED ACCOUNT");
        email.setBody("Your account has been created");

        newUser = userRepository.save(newUser);
        producer.sendMessage(email);

        return mapper.map(newUser,UserDto.class);
    }

    public UserDto getUserById(long userId){
        var user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        return mapper.map(user,UserDto.class);
    }
    public UserDto updateUser(long userId, UserDto userDto){

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
        var roles = getRolesByIds(userDto.getRoles());

        user = mapper.map(userDto, User.class);
        user.setId(userId);
        user.setRoles(roles);
        var email = buildEmail(user.getEmail());

        email.setSubject("UPDATED ACCOUNT");
        email.setBody("Your account information has been updated");
        var newUser = userRepository.save(user);
        producer.sendMessage(email);
        return mapper.map(newUser,UserDto.class);
    }
    private Set<Role> getRolesByIds(Set<Role> roles) throws ResourceNotFoundException{
        Set<Role> existingRolesList = new HashSet<>();

        for (Role role: roles) {
            Role foundRole = roleRepository.findById(role.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "id", role.getId()));
            existingRolesList.add(foundRole);
        }
        return existingRolesList;
    }
    private EmailDto buildEmail(String email){
        var emailDto = new EmailDto();
        emailDto.setFromEmail("challengecompass1@gmail.com");
        emailDto.setFromName("Compass");
        emailDto.setTo(email);
        emailDto.setReplyTo("");
        emailDto.setBody("");
        emailDto.setSubject("");
        emailDto.setContentType("text/plain");
        return  emailDto;
    }
}

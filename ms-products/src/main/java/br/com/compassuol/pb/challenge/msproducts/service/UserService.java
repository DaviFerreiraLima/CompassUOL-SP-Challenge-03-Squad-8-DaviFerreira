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
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper mapper;

    private RabbitMQProducer producer;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper mapper, RabbitMQProducer producer) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.producer = producer;
    }

    public UserDto createUser(UserDto userDto){
        var existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser !=null){
            throw new ProductAPIException(HttpStatus.BAD_REQUEST,"This User email is already registered");
        }
        var roles = getRolesByIds(userDto.getRoles());

        var newUser = mapper.map(userDto, User.class);
        newUser.setRoles(roles);

        var email = buildEmail(newUser);
        email.setContentType("CREATED ACCOUNT");

        newUser = userRepository.save(newUser);
        producer.sendMessage(email);

        return mapper.map(newUser,UserDto.class);
    }

    public UserDto getUserById(long userId){
        var user = userRepository.findById(userId);
        if (user==null){
            throw  new ResourceNotFoundException("User","id",userId);
        }
        return mapper.map(user,UserDto.class);
    }

    public UserDto updateUser(long userId, UserDto userDto){

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
        var roles = getRolesByIds(userDto.getRoles());

        user = mapper.map(userDto, User.class);
        user.setId(userId);
        user.setRoles(roles);

        var email = buildEmail(user);
        email.setContentType("UPDATED ACCOUNT");

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

    private EmailDto buildEmail(User user){
        var emailDto = new EmailDto();
        emailDto.setFromEmail("daviferreilima@gmail.com");
        emailDto.setFromName("Products Service");
        emailDto.setTo(user.getEmail());
        emailDto.setReplyTo("none reply");
        emailDto.setBody("Thats a great email");
        emailDto.setSubject("compass");
        emailDto.setContentType("String");

        return  emailDto;
    }
}

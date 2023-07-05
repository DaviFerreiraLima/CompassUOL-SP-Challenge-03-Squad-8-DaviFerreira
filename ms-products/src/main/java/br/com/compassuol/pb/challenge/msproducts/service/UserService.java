package br.com.compassuol.pb.challenge.msproducts.service;


import br.com.compassuol.pb.challenge.msproducts.entity.Role;
import br.com.compassuol.pb.challenge.msproducts.entity.User;
import br.com.compassuol.pb.challenge.msproducts.exception.ProductAPIException;
import br.com.compassuol.pb.challenge.msproducts.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.msproducts.payload.UserDto;
import br.com.compassuol.pb.challenge.msproducts.repository.RoleRepository;
import br.com.compassuol.pb.challenge.msproducts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper mapper;

    public UserService(UserRepository userRepository, ModelMapper mapper,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
    }

    public UserDto createUser(UserDto userDto){
        var existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser !=null){
            throw new ProductAPIException(HttpStatus.BAD_REQUEST,"This User email is already registered");
        }
        var roles = getRolesByIds(userDto.getRoles());

        var newUser = mapper.map(userDto, User.class);
        newUser.setRoles(roles);

        newUser = userRepository.save(newUser);
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

        var newUser = userRepository.save(user);
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
}

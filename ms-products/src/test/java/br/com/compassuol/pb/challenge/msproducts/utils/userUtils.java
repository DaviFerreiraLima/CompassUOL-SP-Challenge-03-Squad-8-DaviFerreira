package br.com.compassuol.pb.challenge.msproducts.utils;

import br.com.compassuol.pb.challenge.msproducts.entity.Role;
import br.com.compassuol.pb.challenge.msproducts.entity.User;
import br.com.compassuol.pb.challenge.msproducts.payload.UserDto;

import java.util.HashSet;

public class userUtils {

    public static UserDto createUserDto(){
        var user = new UserDto();
        var rolesSet = new HashSet<Role>();
        var role = new Role();

        role.setId(1);
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        user.setRoles(rolesSet);

        return user;
    }

    public static User createUser(){
        var user = new User();
        var rolesSet = new HashSet<Role>();
        var role = new Role();
        role.setId(1);

        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        user.setRoles(rolesSet);

        return user;
    }
}

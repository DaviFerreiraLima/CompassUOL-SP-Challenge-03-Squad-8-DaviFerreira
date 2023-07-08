package br.com.compassuol.pb.challenge.msproducts.controller;

import br.com.compassuol.pb.challenge.msproducts.payload.UserDto;
import br.com.compassuol.pb.challenge.msproducts.publisher.RabbitMQProducer;
import br.com.compassuol.pb.challenge.msproducts.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;
    private RabbitMQProducer producer;

    public UserController(UserService userService, RabbitMQProducer producer) {
        this.userService = userService;
        this.producer = producer;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") long userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateProduct(@PathVariable(value = "id") long userId, @RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(userService.updateUser(userId,userDto),HttpStatus.OK);
    }
}

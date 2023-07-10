package br.com.compassuol.pb.challenge.msproducts.controller;

import br.com.compassuol.pb.challenge.msproducts.entity.User;
import br.com.compassuol.pb.challenge.msproducts.exception.ProductAPIException;
import br.com.compassuol.pb.challenge.msproducts.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.msproducts.payload.RoleDto;
import br.com.compassuol.pb.challenge.msproducts.payload.UserDto;
import br.com.compassuol.pb.challenge.msproducts.service.RoleService;
import br.com.compassuol.pb.challenge.msproducts.service.UserService;
import br.com.compassuol.pb.challenge.msproducts.utils.ControllerUtils;
import br.com.compassuol.pb.challenge.msproducts.utils.UserUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    public static final String BASE_URL = "/users";
    public static final String ID_URL = "/users/1";
    @Test
    void createUserSuccess() throws Exception {

        var userDto = UserUtils.createUserDto();
        var contentType = "text/plain";
        when(userService.createUser(any(UserDto.class),anyString())).thenReturn(userDto);

        String requestBody = ControllerUtils.mapToString(userDto);
        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-Content-Type", contentType)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
    @Test
    void createUserProductApiException() throws Exception {

        when(userService.createUser(any(UserDto.class),anyString())).thenThrow(new ProductAPIException(HttpStatus.BAD_REQUEST,"This User email is already registered"));

        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
    @Test
    void getUserByIdSuccess() throws Exception {

        var userDto = UserUtils.createUserDto();

        when(userService.getUserById(anyLong())).thenReturn(userDto);

        String requestBody = ControllerUtils.mapToString(userDto);
        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getUserByIdResourceNotFoundException() throws Exception {

        when(userService.getUserById(anyLong())).thenThrow( new ResourceNotFoundException("User","id",anyLong()));
        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void updateUserSuccess() throws Exception {

        var userDto = UserUtils.createUserDto();
        var contentType = "text/plain";
        when(userService.updateUser(anyLong(),any(UserDto.class),anyString())).thenReturn(userDto);

        String requestBody = ControllerUtils.mapToString(userDto);
        var result =
                mockMvc.perform(put(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .header("X-Content-Type", contentType))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void updateUserUserAPIException() throws Exception {

        when(userService.updateUser(anyLong(),any(UserDto.class),anyString())).thenThrow(new ResourceNotFoundException("Product", "id", 1L));
        var result = mockMvc.perform(put(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}
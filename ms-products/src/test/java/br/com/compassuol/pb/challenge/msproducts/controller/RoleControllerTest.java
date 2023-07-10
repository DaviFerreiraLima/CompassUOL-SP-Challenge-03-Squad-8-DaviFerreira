package br.com.compassuol.pb.challenge.msproducts.controller;

import br.com.compassuol.pb.challenge.msproducts.payload.ProductDto;
import br.com.compassuol.pb.challenge.msproducts.payload.RoleDto;
import br.com.compassuol.pb.challenge.msproducts.service.ProductService;
import br.com.compassuol.pb.challenge.msproducts.service.RoleService;
import br.com.compassuol.pb.challenge.msproducts.utils.ControllerUtils;
import br.com.compassuol.pb.challenge.msproducts.utils.ProductUtil;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RoleController.class})
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Test
    void createRoleSuccess() throws Exception {
        var role= new RoleDto(1L,"ROLE_USER");

        when(roleService.createRole(any(RoleDto.class))).thenReturn(role);

        String requestBody = ControllerUtils.mapToString(role);
        var result =
                mockMvc.perform(post("/roles")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}
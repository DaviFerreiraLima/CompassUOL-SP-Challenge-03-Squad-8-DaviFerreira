package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.entity.Role;
import br.com.compassuol.pb.challenge.msproducts.payload.RoleDto;
import br.com.compassuol.pb.challenge.msproducts.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @Spy
    private ModelMapper mapper;
    @InjectMocks
    private RoleService roleService;
    @Test
    void createRole() {
        var roleDto =new RoleDto(1L,"operator");
        var newRole = new Role(1L,"operator");

        when(roleRepository.save(any(Role.class))).thenReturn(newRole);

        var response = roleService.createRole(roleDto);

        assertEquals(roleDto.getId(),response.getId());
    }
}
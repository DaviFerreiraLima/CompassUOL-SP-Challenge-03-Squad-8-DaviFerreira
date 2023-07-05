package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.entity.Role;
import br.com.compassuol.pb.challenge.msproducts.payload.RoleDto;
import br.com.compassuol.pb.challenge.msproducts.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    private ModelMapper mapper;

    public RoleService(RoleRepository roleRepository, ModelMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    public RoleDto createRole(RoleDto roleDto){
        var role = mapper.map(roleDto, Role.class);
        var newRole = roleRepository.save(role);
        return mapper.map(newRole,RoleDto.class);
    }


}

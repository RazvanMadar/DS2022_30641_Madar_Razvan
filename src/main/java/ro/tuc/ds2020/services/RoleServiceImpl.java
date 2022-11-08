package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.BusinessException;
import ro.tuc.ds2020.dtos.RoleDTO;
import ro.tuc.ds2020.dtos.mappers.RoleMapper;
import ro.tuc.ds2020.entities.Role;
import ro.tuc.ds2020.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Long saveRole(RoleDTO role) {
        roleRepository.save(RoleMapper.INSTANCE.dtoToModel(role));
        return role.getId();
    }

    @Override
    public List<RoleDTO> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(RoleMapper.INSTANCE::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        final Role role = roleRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("Role not found!");
        });

        return RoleMapper.INSTANCE.modelToDto(role);
    }
}

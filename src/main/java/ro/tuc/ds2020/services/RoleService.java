package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dtos.RoleDTO;

import java.util.List;

public interface RoleService {
    Long saveRole(RoleDTO role);
    List<RoleDTO> getRoles();
    RoleDTO getRoleById(Long id);
}

package ro.tuc.ds2020.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.tuc.ds2020.dtos.RoleDTO;
import ro.tuc.ds2020.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO modelToDto(Role role);
    Role dtoToModel(RoleDTO role);
}

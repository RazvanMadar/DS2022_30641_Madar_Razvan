package ro.tuc.ds2020.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO modelToDto(User user);
    User dtoToModel(UserDTO user);
}

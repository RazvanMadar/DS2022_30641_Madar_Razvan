package ro.tuc.ds2020.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.tuc.ds2020.dtos.DeviceUserDTO;
import ro.tuc.ds2020.entities.Device;

@Mapper(componentModel = "spring")
public interface DeviceUserMapper {

    DeviceUserMapper INSTANCE = Mappers.getMapper(DeviceUserMapper.class);

    DeviceUserDTO modelToDto(Device device);
    Device dtoToModel(DeviceUserDTO device);
}

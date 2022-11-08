package ro.tuc.ds2020.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    DeviceDTO modelToDto(Device device);
    Device dtoToModel(DeviceDTO device);
}
package ro.tuc.ds2020.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.entities.Consumption;

@Mapper(componentModel = "spring")
public interface ConsumptionMapper {

    ConsumptionMapper INSTANCE = Mappers.getMapper(ConsumptionMapper.class);

    ConsumptionDTO modelToDto(Consumption consumption);
    Consumption dtoToModel(ConsumptionDTO consumption);
}
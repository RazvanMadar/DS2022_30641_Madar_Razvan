package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.BusinessException;
import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.dtos.mappers.ConsumptionMapper;
import ro.tuc.ds2020.entities.Consumption;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.ConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.Optional;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final DeviceRepository deviceRepository;

    public ConsumptionServiceImpl(ConsumptionRepository consumptionRepository, DeviceRepository deviceRepository) {
        this.consumptionRepository = consumptionRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Long saveConsumption(ConsumptionDTO consumptionDTO) {
        final Consumption consumption = ConsumptionMapper.INSTANCE.dtoToModel(consumptionDTO);
        final Device device = deviceRepository.findById(consumptionDTO.getDeviceId()).orElseThrow(() -> {
            throw new BusinessException("Device not found!");
        });
        consumption.setDevice(device);
        consumptionRepository.save(consumption);
        return consumption.getId();
    }
}

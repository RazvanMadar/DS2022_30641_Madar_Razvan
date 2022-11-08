package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.BusinessException;
import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceUserDTO;
import ro.tuc.ds2020.dtos.mappers.ConsumptionMapper;
import ro.tuc.ds2020.dtos.mappers.DeviceMapper;
import ro.tuc.ds2020.dtos.mappers.DeviceUserMapper;
import ro.tuc.ds2020.entities.Consumption;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.ConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final ConsumptionRepository consumptionRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, ConsumptionRepository consumptionRepository) {
        this.deviceRepository = deviceRepository;
        this.consumptionRepository = consumptionRepository;
    }

    @Override
    public Long saveDevice(DeviceDTO device) {
        final Device savedDevice = DeviceMapper.INSTANCE.dtoToModel(device);
        deviceRepository.save(savedDevice);
        return savedDevice.getId();
    }

    @Override
    public List<DeviceUserDTO> getDevices() {
        return deviceRepository.findAll().stream()
                .map(device -> {
                    DeviceUserDTO deviceUserDTO = DeviceUserMapper.INSTANCE.modelToDto(device);
                    User deviceUser = device.getUser();
                    if (null != deviceUser)
                        deviceUserDTO.setUserId(deviceUser.getId());
                    return deviceUserDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceDTO> getFreeDevices() {
        return deviceRepository.findAll().stream()
                .filter(device -> device.getUser() == null)
                .map(DeviceMapper.INSTANCE::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDTO getDeviceById(Long id) {
        final Device device = deviceRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("Device not found!");
        });
        return DeviceMapper.INSTANCE.modelToDto(device);
    }

    @Override
    public DeviceDTO updateDevice(Long id, DeviceDTO deviceDTO) {
        final Device device = deviceRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("Device not found!");
        });

        final String description = deviceDTO.getDescription();
        final String address = deviceDTO.getAddress();
        final Double maxConsumptionPerHour = deviceDTO.getMaxConsumptionPerHour();
        if (null != description) {
            device.setDescription(description);
        }
        if (null != address) {
            device.setAddress(address);
        }
        if (null != maxConsumptionPerHour) {
            device.setMaxConsumptionPerHour(maxConsumptionPerHour);
        }
        Device savedDevice = deviceRepository.save(device);

        return DeviceMapper.INSTANCE.modelToDto(savedDevice);
    }

    @Override
    public Long deleteDevice(Long id) {
        final Device device = deviceRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("Device not found!");
        });
        deviceRepository.delete(device);

        return id;
    }

    @Override
    public Long assignConsumptionToDevice(Long deviceId, ConsumptionDTO consumptionDTO) {
        final Device device = deviceRepository.findById(deviceId).orElseThrow(() -> {
            throw new BusinessException("Device not found!");
        });
        Consumption consumption = ConsumptionMapper.INSTANCE.dtoToModel(consumptionDTO);
        consumption.setDevice(device);
        consumptionRepository.save(consumption);
        return deviceId;
    }

    @Override
    public List<ConsumptionDTO> getConsumptionsPerDevice(Long id, String date) {
        final Device deviceById = deviceRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("Device not found!");
        });
        System.out.println(date);
        return deviceById.getConsumptions().stream()
                .filter(device -> device.getTimestamp().toLocalDate().equals(LocalDate.parse(date)))
                .map(ConsumptionMapper.INSTANCE::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsumptionDTO> getConsumptions(List<DeviceDTO> devices, String date) {
        return devices.stream()
                .map(device -> getConsumptionsPerDevice(device.getId(), date))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Long freeDevice(Long deviceId) {
        final Device device = deviceRepository.findById(deviceId).orElseThrow(() -> {
            throw new BusinessException("Device not found!");
        });
        device.setUser(null);
        deviceRepository.save(device);
        return deviceId;
    }
}

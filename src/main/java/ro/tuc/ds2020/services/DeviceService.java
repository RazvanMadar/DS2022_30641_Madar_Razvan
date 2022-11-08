package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceUserDTO;

import java.util.List;

public interface DeviceService {
    Long saveDevice(DeviceDTO device);
    List<DeviceUserDTO> getDevices();
    List<DeviceDTO> getFreeDevices();
    DeviceDTO getDeviceById(Long id);
    DeviceDTO updateDevice(Long id, DeviceDTO user);
    Long deleteDevice(Long id);
    Long assignConsumptionToDevice(Long deviceId, ConsumptionDTO consumption);
    List<ConsumptionDTO> getConsumptionsPerDevice(Long id, String date);
    List<ConsumptionDTO> getConsumptions(List<DeviceDTO> devices, String date);
    Long freeDevice(Long deviceId);
}

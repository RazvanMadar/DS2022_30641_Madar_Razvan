package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceUserDTO;
import ro.tuc.ds2020.services.DeviceService;

import java.util.List;

@RestController
@RequestMapping("/devices")
@CrossOrigin(origins = "http://localhost:3000")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<?> createDevice(@RequestBody DeviceDTO deviceDTO) {
        Long id = deviceService.saveDevice(deviceDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DeviceUserDTO>> getDevices() {
        List<DeviceUserDTO> deviceDTOS = deviceService.getDevices();
        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @PutMapping("/{deviceId}/assign-consumption")
    public ResponseEntity<?> assignConsumptionToDevice(@PathVariable Long deviceId, @RequestBody ConsumptionDTO consumptionDTO) {
        final Long id = deviceService.assignConsumptionToDevice(deviceId, consumptionDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable Long id, @RequestBody DeviceDTO device) {
        final DeviceDTO deviceDTO = deviceService.updateDevice(id, device);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable Long id) {
        final Long deviceId = deviceService.deleteDevice(id);
        return new ResponseEntity<>(deviceId, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeviceById(@PathVariable Long id) {
        final DeviceDTO deviceById = deviceService.getDeviceById(id);
        return new ResponseEntity<>(deviceById, HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<?> getFreeDevices() {
        final List<DeviceDTO> freeDevices = deviceService.getFreeDevices();
        return new ResponseEntity<>(freeDevices, HttpStatus.OK);
    }

    @GetMapping("/consumptions")
    public ResponseEntity<?> getConsumptionsPerDevice(@RequestParam Long deviceId, @RequestParam String date) {
        final List<ConsumptionDTO> consumptions = deviceService.getConsumptionsPerDevice(deviceId, date);
        return new ResponseEntity<>(consumptions, HttpStatus.OK);
    }

    @GetMapping("/all-consumptions")
    public ResponseEntity<?> getConsumptions(@RequestParam List<DeviceDTO> devices, @RequestParam String date) {
        final List<ConsumptionDTO> consumptions = deviceService.getConsumptions(devices, date);
        return new ResponseEntity<>(consumptions, HttpStatus.OK);
    }

    @PutMapping("/unassigned")
    public ResponseEntity<?> freeDevice(@RequestParam Long deviceId) {
        final Long device = deviceService.freeDevice(deviceId);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

}

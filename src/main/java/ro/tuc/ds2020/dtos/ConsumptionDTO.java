package ro.tuc.ds2020.dtos;

import java.time.LocalDateTime;

public class ConsumptionDTO {
    private Long id;
    private LocalDateTime timestamp;
    private Double energyConsumption;
    private Long deviceId;

    public ConsumptionDTO() {}

    public ConsumptionDTO(Long id, LocalDateTime timestamp, Double energyConsumption, Long deviceId) {
        this.id = id;
        this.timestamp = timestamp;
        this.energyConsumption = energyConsumption;
        this.deviceId = deviceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}

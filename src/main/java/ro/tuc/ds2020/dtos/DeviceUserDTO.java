package ro.tuc.ds2020.dtos;

public class DeviceUserDTO {
    private Long id;
    private String description;
    private String address;
    private Double maxConsumptionPerHour;
    private Long userId;

    public DeviceUserDTO() {}

    public DeviceUserDTO(Long id, String description, String address, Double maxConsumptionPerHour, Long userId) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maxConsumptionPerHour = maxConsumptionPerHour;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getMaxConsumptionPerHour() {
        return maxConsumptionPerHour;
    }

    public void setMaxConsumptionPerHour(Double maxConsumptionPerHour) {
        this.maxConsumptionPerHour = maxConsumptionPerHour;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

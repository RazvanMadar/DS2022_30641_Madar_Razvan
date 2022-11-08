package ro.tuc.ds2020.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String address;
    private Double maxConsumptionPerHour;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="device", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Consumption> consumptions;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Consumption> getConsumptions() {
        return consumptions;
    }

    public void setConsumptions(Set<Consumption> consumptions) {
        this.consumptions = consumptions;
    }
}

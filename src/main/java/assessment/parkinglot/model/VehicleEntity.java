package assessment.parkinglot.model;

import jakarta.persistence.*;

@Entity
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;

    public VehicleEntity() {

    }

    public VehicleEntity(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package assessment.parkinglot.vehicles;

import assessment.parkinglot.utils.Spots;

import java.util.List;

public abstract class Vehicle {
    private String type;

    private int spotsNeeded;

    private List<Spots> spotsAllowed;

    public Vehicle (String type, int spotsNeeded, List<Spots> spotsAllowed) {
        this.type = type;
        this.spotsNeeded = spotsNeeded;
        this.spotsAllowed = spotsAllowed;
    }

    public String getType() {
        return type;
    }

    public int getSpotsNeeded() {
        return spotsNeeded;
    }

    public List<Spots> getSpotsAllowed() {
        return spotsAllowed;
    }
}

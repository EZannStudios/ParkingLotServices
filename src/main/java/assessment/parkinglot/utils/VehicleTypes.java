package assessment.parkinglot.utils;

import assessment.parkinglot.vehicles.Car;
import assessment.parkinglot.vehicles.Motorcycle;
import assessment.parkinglot.vehicles.Van;

public enum VehicleTypes {
    MOTORCYCLE(Motorcycle.class, "motorcycle"),
    CAR(Car.class, "car"),
    VAN(Van.class, "van");

    private final Class clazz;
    private final String type;

    VehicleTypes(Class clazz, String type) {
        this.clazz = clazz;
        this.type = type;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getType() {
        return type;
    }
}

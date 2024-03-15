package assessment.parkinglot.utils;

import assessment.parkinglot.vehicles.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleFactory<T extends Vehicle> {
    public T createVehicle(Class clazz) {
        try {
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}

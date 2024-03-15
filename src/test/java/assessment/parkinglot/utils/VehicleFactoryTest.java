package assessment.parkinglot.utils;

import assessment.parkinglot.vehicles.Car;
import assessment.parkinglot.vehicles.Motorcycle;
import assessment.parkinglot.vehicles.Van;
import assessment.parkinglot.vehicles.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehicleFactoryTest {

    @Test
    void testCreateVehicle_Car() {
        VehicleFactory<Vehicle> vehicleFactory = new VehicleFactory<>();

        Vehicle vehicle = vehicleFactory.createVehicle(Car.class);

        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Car);
    }

    @Test
    void testCreateVehicle_Motorcycle() {
        VehicleFactory<Vehicle> vehicleFactory = new VehicleFactory<>();

        Vehicle vehicle = vehicleFactory.createVehicle(Motorcycle.class);

        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Motorcycle);
    }

    @Test
    void testCreateVehicle_Van() {
        VehicleFactory<Vehicle> vehicleFactory = new VehicleFactory<>();

        Vehicle vehicle = vehicleFactory.createVehicle(Van.class);

        assertNotNull(vehicle);
        assertTrue(vehicle instanceof Van);
    }

    @Test
    void testCreateVehicle_NullClass() {
        VehicleFactory<Vehicle> vehicleFactory = new VehicleFactory<>();

        Vehicle vehicle = vehicleFactory.createVehicle(null);

        assertNull(vehicle);
    }

    @Test
    void testCreateVehicle_InvalidClass() {
        VehicleFactory<Vehicle> vehicleFactory = new VehicleFactory<>();

        Vehicle vehicle = vehicleFactory.createVehicle(String.class);

        assertNull(vehicle);
    }
}


package assessment.parkinglot.services;

import assessment.parkinglot.model.VehicleEntity;
import assessment.parkinglot.vehicles.Vehicle;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IParkingService {
    boolean parkVehicle(Vehicle vehicle);

    void vehicleLeavesParkingLot(Long vehicleId);

    int findRemainingSpots();

    int findRemainingSpots(String spotType);

    boolean areAllSpotsTakenForType(String spotType);

    Optional<VehicleEntity> findVehiclesById(Long id);

    Iterable<VehicleEntity> findAllParkedVehicles();
}

package assessment.parkinglot.services.impl;

import assessment.parkinglot.model.ParkingSpot;
import assessment.parkinglot.model.VehicleEntity;
import assessment.parkinglot.repositories.ParkingSpotRepository;
import assessment.parkinglot.repositories.VehicleRepository;
import assessment.parkinglot.services.IParkingService;
import assessment.parkinglot.utils.Spots;
import assessment.parkinglot.vehicles.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingServiceImpl implements IParkingService {
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public boolean parkVehicle(Vehicle vehicle) {
        List<ParkingSpot> availableSpots = getSpotTypeForVehicle(vehicle);
        if (!availableSpots.isEmpty()) {
            List<ParkingSpot> spotsTaken = new ArrayList<>();
            VehicleEntity vehicleEntity = vehicleRepository.save(
                    new VehicleEntity(vehicle.getType()));
            for (int i = 0; i < vehicle.getSpotsNeeded(); i++) {
                availableSpots.get(i).setOccupied(true);
                availableSpots.get(i).setVehicleId(vehicleEntity.getId());
                spotsTaken.add(availableSpots.get(i));
            }
            parkingSpotRepository.saveAll(spotsTaken);
            return true;
        }
        return false;
    }

    @Override
    public void vehicleLeavesParkingLot(Long vehicleId) {
        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByVehicleId(vehicleId);
        for (ParkingSpot spot : parkingSpots) {
            spot.setOccupied(false);
            parkingSpotRepository.save(spot);
        }
        vehicleRepository.deleteById(vehicleId);
    }

    @Override
    public int findRemainingSpots() {
        return parkingSpotRepository.findByOccupiedFalse().size();
    }

    @Override
    public int findRemainingSpots(String spotType) {
        return parkingSpotRepository.findByTypeAndOccupiedFalse(spotType).size();
    }

    @Override
    public boolean areAllSpotsTakenForType(String spotType) {
        return parkingSpotRepository.findByTypeAndOccupiedFalse(spotType).isEmpty();
    }

    @Override
    public Optional<VehicleEntity> findVehiclesById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Iterable<VehicleEntity> findAllParkedVehicles() {
        return vehicleRepository.findAll();
    }

    private List<ParkingSpot> getSpotTypeForVehicle(Vehicle vehicle) {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        for (Spots spots : vehicle.getSpotsAllowed()) {
            parkingSpots.addAll(parkingSpotRepository.findByTypeAndOccupiedFalse(spots.getSpotType()));
        }
        return parkingSpots.size() >= vehicle.getSpotsNeeded() ? parkingSpots : List.of();
    }
}

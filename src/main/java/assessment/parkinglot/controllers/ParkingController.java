package assessment.parkinglot.controllers;

import assessment.parkinglot.model.VehicleEntity;
import assessment.parkinglot.services.IParkingService;
import assessment.parkinglot.utils.Spots;
import assessment.parkinglot.utils.VehicleFactory;
import assessment.parkinglot.utils.VehicleTypes;
import assessment.parkinglot.vehicles.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parkingLot")
public class ParkingController {
    @Autowired
    private IParkingService parkingService;

    @Autowired
    private VehicleFactory vehicleFactory;

    @PostMapping("/park/{vehicleType}")
    public ResponseEntity<String> parkVehicle(@PathVariable String vehicleType) {
        if (checkVehicleType(vehicleType)) {
            Vehicle vehicle = vehicleFactory.createVehicle(VehicleTypes.valueOf(vehicleType.toUpperCase()).getClazz());
            if (parkingService.parkVehicle(vehicle)) {
                return ResponseEntity.ok(vehicleType + " parked successfully");
            } else {
                return ResponseEntity.badRequest().body("No available spots for " + vehicleType);
            }
        } else {
            return ResponseEntity.badRequest().body(vehicleType + " is not a valid Vehicle Type");
        }
    }

    @PostMapping("/leave/{id}")
    public ResponseEntity<String> vehicleLeavesParkingLot(@PathVariable Long id) {
        Optional<VehicleEntity> vehicleModel = parkingService.findVehiclesById(id);
        if (vehicleModel.isEmpty()) {
            return ResponseEntity.badRequest().body("Vehicle with id: " + id + " not found");
        } else {
            parkingService.vehicleLeavesParkingLot(id);
        }

        return ResponseEntity.ok("Vehicle type " + vehicleModel.get().getType() + " with id: " + id + " left the parking lot");
    }

    @GetMapping("/remaining")
    public ResponseEntity<String> findRemainingSpots() {
        int remainingSpots = parkingService.findRemainingSpots();
        return ResponseEntity.ok(remainingSpots + " remaining spots");
    }

    @GetMapping("/remainingForType/{spotType}")
    public ResponseEntity<String> findRemainingSpotsForType(@PathVariable String spotType) {
        if (checkSpotType(spotType)) {
            int remainingSpots = parkingService.findRemainingSpots(spotType);
            return ResponseEntity.ok(remainingSpots + " remaining spots for type " + spotType);
        } else {
            return ResponseEntity.badRequest().body(spotType + " is not a valid Spot Type");
        }
    }

    @GetMapping("/allTaken/{spotType}")
    public ResponseEntity<Boolean> areAllSpotsTakenForType(@PathVariable String spotType) {
        boolean allTaken = parkingService.areAllSpotsTakenForType(spotType);
        return ResponseEntity.ok(allTaken);
    }

    @GetMapping("/parkedVehicles")
    public ResponseEntity<String> findAllParkedVehicles() {
        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        parkingService.findAllParkedVehicles().forEach(vehicleEntities::add);
        if (!vehicleEntities.isEmpty()) {
            List<String> vehicleTypes = vehicleEntities.stream()
                    .map(VehicleEntity::getType)
                    .toList();

            return ResponseEntity.ok(vehicleTypes.toString());
        } else {
            return ResponseEntity.ok("The parking lot is empty");
        }
    }

    private boolean checkSpotType(String spotType) {
        boolean isContained = false;
        for (Spots value : Spots.values()) {
            if (value.getSpotType().equals(spotType)) {
                isContained = true;
                break;
            }
        }
        return isContained;
    }
    private boolean checkVehicleType(String vehicleType) {
        boolean isContained = false;
        for (VehicleTypes value : VehicleTypes.values()) {
            if (value.getType().equals(vehicleType)) {
                isContained = true;
                break;
            }
        }
        return isContained;
    }
}

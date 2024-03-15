package assessment.parkinglot.services;

import assessment.parkinglot.model.ParkingSpot;
import assessment.parkinglot.model.VehicleEntity;
import assessment.parkinglot.repositories.ParkingSpotRepository;
import assessment.parkinglot.repositories.VehicleRepository;
import assessment.parkinglot.utils.Spots;
import assessment.parkinglot.utils.VehicleFactory;
import assessment.parkinglot.utils.VehicleTypes;
import assessment.parkinglot.vehicles.Vehicle;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ParkingServiceTest {

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleEntity vehicleEntity;

    @InjectMocks
    private ParkingService parkingService;

    @Test
    void testParkVehicle() {
        VehicleFactory vehicleFactory = new VehicleFactory();
        Vehicle vehicle = vehicleFactory.createVehicle(VehicleTypes.CAR.getClazz());
        List<ParkingSpot> availableSpots = new ArrayList<>();
        availableSpots.add(new ParkingSpot(Spots.REGULAR_SPOT.getSpotType()));
        when(vehicleEntity.getId()).thenReturn(1L);
        when(vehicleRepository.save(any())).thenReturn(vehicleEntity);
        when(parkingSpotRepository.findByTypeAndOccupiedFalse(Spots.REGULAR_SPOT.getSpotType())).thenReturn(availableSpots);

        boolean result = parkingService.parkVehicle(vehicle);

        assertTrue(result);
        verify(parkingSpotRepository, times(1)).saveAll(availableSpots);
        verify(vehicleRepository, times(1)).save(any());
    }

    @Test
    void testVehicleLeavesParkingLot() {
        long vehicleId = 1L;
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        parkingSpots.add(new ParkingSpot(Spots.REGULAR_SPOT.getSpotType()));
        when(parkingSpotRepository.findByVehicleId(vehicleId)).thenReturn(parkingSpots);

        parkingService.vehicleLeavesParkingLot(vehicleId);

        verify(parkingSpotRepository, times(1)).findByVehicleId(vehicleId);
        verify(parkingSpotRepository, times(1)).save(any(ParkingSpot.class));
        verify(vehicleRepository, times(1)).deleteById(vehicleId);
    }

    @Test
    void testFindRemainingSpots() {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        parkingSpots.add(new ParkingSpot(Spots.REGULAR_SPOT.getSpotType()));
        when(parkingSpotRepository.findByOccupiedFalse()).thenReturn(parkingSpots);

        int remainingSpots = parkingService.findRemainingSpots();

        assertEquals(1, remainingSpots);
        verify(parkingSpotRepository, times(1)).findByOccupiedFalse();
    }

    @Test
    void testFindRemainingSpotsForType() {
        String spotType = Spots.REGULAR_SPOT.getSpotType();
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        parkingSpots.add(new ParkingSpot(spotType));
        when(parkingSpotRepository.findByTypeAndOccupiedFalse(spotType)).thenReturn(parkingSpots);

        int remainingSpots = parkingService.findRemainingSpots(spotType);

        assertEquals(1, remainingSpots);
        verify(parkingSpotRepository, times(1)).findByTypeAndOccupiedFalse(spotType);
    }

    @Test
    void testAreAllSpotsTakenForType() {
        String spotType = Spots.REGULAR_SPOT.getSpotType();
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        when(parkingSpotRepository.findByTypeAndOccupiedFalse(spotType)).thenReturn(parkingSpots);

        boolean result = parkingService.areAllSpotsTakenForType(spotType);

        assertTrue(result);
        verify(parkingSpotRepository, times(1)).findByTypeAndOccupiedFalse(spotType);
    }

    @Test
    void testFindVehiclesById() {
        long vehicleId = 1L;
        Optional<VehicleEntity> optionalVehicle = Optional.of(new VehicleEntity());
        when(vehicleRepository.findById(vehicleId)).thenReturn(optionalVehicle);

        Optional<VehicleEntity> result = parkingService.findVehiclesById(vehicleId);

        assertTrue(result.isPresent());
        assertEquals(optionalVehicle.get(), result.get());
        verify(vehicleRepository, times(1)).findById(vehicleId);
    }

    @Test
    void testFindAllParkedVehicles() {
        List<VehicleEntity> vehicleEntities = List.of(new VehicleEntity());
        when(vehicleRepository.findAll()).thenReturn(vehicleEntities);

        Iterable<VehicleEntity> result = parkingService.findAllParkedVehicles();

        assertEquals(vehicleEntities, result);
        verify(vehicleRepository, times(1)).findAll();
    }
}

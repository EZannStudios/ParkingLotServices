package assessment.parkinglot.controllers;

import assessment.parkinglot.model.VehicleEntity;
import assessment.parkinglot.services.IParkingService;
import assessment.parkinglot.utils.VehicleFactory;
import assessment.parkinglot.utils.VehicleTypes;
import assessment.parkinglot.vehicles.Car;
import assessment.parkinglot.vehicles.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(ParkingController.class)
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IParkingService parkingService;

    @MockBean
    private VehicleFactory vehicleFactory;

    @Test
    void testParkVehicle() throws Exception {
        String vehicleType = "car";

        Vehicle mockedVehicle = new Car();
        when(vehicleFactory.createVehicle(VehicleTypes.valueOf(vehicleType.toUpperCase()).getClazz()))
                .thenReturn(mockedVehicle);

        when(parkingService.parkVehicle(mockedVehicle)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/parkingLot/park/{vehicleType}", vehicleType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(vehicleType + " parked successfully"));
    }

    @Test
    void testParkVehicle_no_available_spots() throws Exception {
        String vehicleType = "car";

        Vehicle mockedVehicle = new Car();
        when(vehicleFactory.createVehicle(VehicleTypes.valueOf(vehicleType.toUpperCase()).getClazz()))
                .thenReturn(mockedVehicle);

        when(parkingService.parkVehicle(mockedVehicle)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/parkingLot/park/{vehicleType}", vehicleType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("No available spots for " + vehicleType));
    }

    @Test
    void testParkVehicle_vehicleType_is_invalid() throws Exception {
        String vehicleType = "car";

        Vehicle mockedVehicle = new Car();
        when(vehicleFactory.createVehicle(VehicleTypes.valueOf(vehicleType.toUpperCase()).getClazz()))
                .thenReturn(mockedVehicle);

        String invalidVehicleType = "invalidType";
        mockMvc.perform(MockMvcRequestBuilders.post("/parkingLot/park/{vehicleType}", invalidVehicleType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(invalidVehicleType + " is not a valid Vehicle Type"));
    }

    @Test
    void testVehicleLeavesParkingLot() throws Exception {
        long vehicleId = 1L;

        when(parkingService.findVehiclesById(vehicleId))
                .thenReturn(Optional.of(new VehicleEntity()));
        doNothing().when(parkingService).vehicleLeavesParkingLot(vehicleId);

        mockMvc.perform(MockMvcRequestBuilders.post("/parkingLot/leave/{id}", vehicleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Vehicle type null with id: " + vehicleId + " left the parking lot"));
    }

    @Test
    void testVehicleLeavesParkingLot_vehicle_not_found() throws Exception {
        long vehicleId = 1L;

        when(parkingService.findVehiclesById(vehicleId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/parkingLot/leave/{id}", vehicleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Vehicle with id: " + vehicleId + " not found"));
    }

    @Test
    void testFindRemainingSpots() throws Exception {
        int remainingSpots = 10;

        when(parkingService.findRemainingSpots()).thenReturn(remainingSpots);

        mockMvc.perform(MockMvcRequestBuilders.get("/parkingLot/remaining")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(remainingSpots + " remaining spots"));
    }

    @Test
    void testFindRemainingSpotsForType() throws Exception {
        String spotType = "regular_spot";
        int remainingSpots = 5;

        when(parkingService.findRemainingSpots(spotType)).thenReturn(remainingSpots);

        mockMvc.perform(MockMvcRequestBuilders.get("/parkingLot/remainingForType/{spotType}", spotType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(remainingSpots + " remaining spots for type " + spotType));
    }

    @Test
    void testFindRemainingSpotsForType_not_a_valid_type() throws Exception {
        String invalidSpotType = "invalidType";

        mockMvc.perform(MockMvcRequestBuilders.get("/parkingLot/remainingForType/{spotType}", invalidSpotType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(invalidSpotType + " is not a valid Spot Type"));
    }

    @Test
    void testAreAllSpotsTakenForType() throws Exception {
        String spotType = "regular_spot";

        when(parkingService.areAllSpotsTakenForType(spotType)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/parkingLot/allTaken/{spotType}", spotType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    void testFindAllParkedVehicles() throws Exception {
        List<VehicleEntity> vehicleEntities = List.of(
                new VehicleEntity("car"),
                new VehicleEntity("van"),
                new VehicleEntity("motorcycle"));

        when(parkingService.findAllParkedVehicles()).thenReturn(vehicleEntities);

        mockMvc.perform(MockMvcRequestBuilders.get("/parkingLot/parkedVehicles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[car, van, motorcycle]"));
    }

    @Test
    void testFindAllParkedVehicles_no_parked_vehicles_found() throws Exception {
        List<VehicleEntity> vehicleEntities = List.of();

        when(parkingService.findAllParkedVehicles()).thenReturn(vehicleEntities);

        mockMvc.perform(MockMvcRequestBuilders.get("/parkingLot/parkedVehicles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The parking lot is empty"));
    }
}

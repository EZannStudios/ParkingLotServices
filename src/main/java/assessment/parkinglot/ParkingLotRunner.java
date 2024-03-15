package assessment.parkinglot;

import assessment.parkinglot.model.ParkingSpot;
import assessment.parkinglot.repositories.ParkingSpotRepository;
import assessment.parkinglot.utils.Spots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkingLotRunner implements CommandLineRunner {

    @Autowired
    ParkingSpotRepository parkingSpotRepository;

    @Override
    public void run(String... args) throws Exception {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            parkingSpots.add(new ParkingSpot(Spots.COMPACT_CAR_SPOT.getSpotType()));
        }
        for (int i = 0; i < 10; i++) {
            parkingSpots.add(new ParkingSpot(Spots.REGULAR_SPOT.getSpotType()));
        }
        for (int i = 0; i < 5; i++) {
            parkingSpots.add(new ParkingSpot(Spots.MOTORCYCLE_SPOT.getSpotType()));
        }
        parkingSpotRepository.saveAll(parkingSpots);
    }
}

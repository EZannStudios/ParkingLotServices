package assessment.parkinglot.repositories;

import assessment.parkinglot.model.ParkingSpot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends CrudRepository<ParkingSpot, Long> {

    List<ParkingSpot> findByOccupiedFalse();
    List<ParkingSpot> findByTypeAndOccupiedFalse(String type);
    List<ParkingSpot> findByVehicleId(Long id);
}

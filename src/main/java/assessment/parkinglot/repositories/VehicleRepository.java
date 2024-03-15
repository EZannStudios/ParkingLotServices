package assessment.parkinglot.repositories;

import assessment.parkinglot.model.VehicleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> {
}

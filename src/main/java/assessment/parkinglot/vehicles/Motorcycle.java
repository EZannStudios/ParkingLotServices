package assessment.parkinglot.vehicles;

import assessment.parkinglot.utils.Spots;
import assessment.parkinglot.utils.VehicleTypes;

import java.util.List;

public class Motorcycle extends Vehicle{
    public Motorcycle() {
        super(VehicleTypes.MOTORCYCLE.getType(), 1,
                List.of(Spots.REGULAR_SPOT, Spots.COMPACT_CAR_SPOT, Spots.MOTORCYCLE_SPOT));
    }
}

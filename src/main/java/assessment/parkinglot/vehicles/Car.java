package assessment.parkinglot.vehicles;

import assessment.parkinglot.utils.Spots;
import assessment.parkinglot.utils.VehicleTypes;

import java.util.List;

public class Car extends Vehicle{
    public Car() {
        super(VehicleTypes.CAR.getType(), 1,
                List.of(Spots.REGULAR_SPOT, Spots.COMPACT_CAR_SPOT));
    }
}

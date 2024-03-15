package assessment.parkinglot.vehicles;

import assessment.parkinglot.utils.Spots;
import assessment.parkinglot.utils.VehicleTypes;

import java.util.List;

public class Van extends Vehicle {
    public Van() {
        super(VehicleTypes.VAN.getType(), 3, List.of(Spots.REGULAR_SPOT));
    }
}

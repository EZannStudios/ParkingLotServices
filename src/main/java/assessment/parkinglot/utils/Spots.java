package assessment.parkinglot.utils;

public enum Spots {
    REGULAR_SPOT("regular_spot"),
    COMPACT_CAR_SPOT("compact_car_spot"),
    MOTORCYCLE_SPOT("motorcycle_spot");

    private final String spotType;

    Spots(String spotType) {
    this.spotType = spotType;
    }

    public String getSpotType() {
        return spotType;
    }
}

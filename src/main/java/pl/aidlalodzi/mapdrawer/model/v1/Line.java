package pl.aidlalodzi.mapdrawer.model.v1;

public record Line(
        int uniqueId,
        String lineIdentifier,
        VehicleType vehicleType
) {
}
package pl.aidlalodzi.mapdrawer.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertedData {
    private List<Stop> stops;
    private List<Segment> segments;
    private List<RouteVariant> variants;
}

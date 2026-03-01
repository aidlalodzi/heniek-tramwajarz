package pl.aidlalodzi.mapdrawer.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Segment {
    private Integer trackId;
    private Integer fromIndex;
    private Integer toIndex;
    private List<GeoPoint> geometry;
    private Integer flagA;
    private Integer flagB;
}

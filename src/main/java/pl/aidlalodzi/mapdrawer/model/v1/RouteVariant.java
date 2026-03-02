package pl.aidlalodzi.mapdrawer.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteVariant {
    private Long id;
    private Integer direction;
    private Integer variant;
    private String routeDisplay;
    private String from;
    private String to;
    private List<Integer> stopIndexes;
}

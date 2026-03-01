package pl.aidlalodzi.mapdrawer.model.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stop {
    private Long id;
    private String name;
    private String code;
    private Double lon;
    private Double lat;
    private Integer zone;
}

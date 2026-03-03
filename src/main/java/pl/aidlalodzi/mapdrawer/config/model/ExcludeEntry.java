package pl.aidlalodzi.mapdrawer.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcludeEntry {
    private String name;
    private String pattern;
    private boolean active;
}


package pl.aidlalodzi.mapdrawer.config.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AppProperties {
    private String rootUrl;
    private List<ExcludeEntry> excludeLines = new ArrayList<>();
    private List<ExcludeEntry> excludeRoutesDisplay = new ArrayList<>();
    private List<ExcludeEntry> excludeDepotDeparture = new ArrayList<>();
}


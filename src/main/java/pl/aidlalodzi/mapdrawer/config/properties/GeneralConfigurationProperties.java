package pl.aidlalodzi.mapdrawer.config.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "general-configuration")
public class GeneralConfigurationProperties {
    private String rootUrl;
    private List<ExcludeEntry> excludeLines = new ArrayList<>();
    private List<ExcludeEntry> excludeRoutesDisplay = new ArrayList<>();
    private List<ExcludeEntry> excludeDepotDeparture = new ArrayList<>();
}


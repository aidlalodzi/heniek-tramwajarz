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
    private List<ExcludeEntry> exclude = new ArrayList<>();
}


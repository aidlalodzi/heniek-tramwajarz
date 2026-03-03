package pl.aidlalodzi.mapdrawer.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import pl.aidlalodzi.mapdrawer.config.model.AppProperties;

import java.io.InputStream;

@AllArgsConstructor
@Data
public class RootConfigReader {

    private String applicationFileName;

    public AppProperties getRootConfig() {
        Yaml yaml = new Yaml(new Constructor(AppProperties.class, new LoaderOptions()));
        InputStream inputStream = RootConfigReader.class
                .getClassLoader()
                .getResourceAsStream(applicationFileName);

        if (inputStream == null) {
            throw new RuntimeException("Root config file not found: " + applicationFileName);
        }

        return yaml.load(inputStream);
    }
}

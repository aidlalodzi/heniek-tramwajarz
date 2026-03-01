package pl.aidlalodzi.mapdrawer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.aidlalodzi.mapdrawer.config.properties.GeneralConfigurationProperties;

@Disabled
@SpringBootTest
class HeniekTramwajarzAppTests {

    @Autowired
    GeneralConfigurationProperties cfg;

    @Test
    void contextLoads() {
    }
}

package pl.aidlalodzi.mapdrawer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import pl.aidlalodzi.mapdrawer.config.properties.GeneralConfigurationProperties;

@RequiredArgsConstructor
@Configuration
public class MpkConfig {

    private final GeneralConfigurationProperties gcp;

    @Bean
    public RestClient restClient() {
        return RestClient.builder().baseUrl(gcp.getRootUrl()).build();
    }
}


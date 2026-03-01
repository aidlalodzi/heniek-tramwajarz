package pl.aidlalodzi.mapdrawer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.aidlalodzi.mapdrawer.service.LineService;

@SpringBootApplication
public class HeniekTramwajarzApp {

    public static void main(String[] args) {
        SpringApplication.run(HeniekTramwajarzApp.class, args);
    }

    @Bean
    public CommandLineRunner dataPrinter(LineService routeService) {
        return args -> {
            System.out.println("Pobieram listę linii...");
            var lines = routeService.fetchRoutes();
            lines.forEach(System.out::println);
        };
    }
}

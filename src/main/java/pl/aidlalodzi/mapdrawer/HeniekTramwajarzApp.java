package pl.aidlalodzi.mapdrawer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.aidlalodzi.mapdrawer.model.v1.Line;
import pl.aidlalodzi.mapdrawer.model.v1.VehicleType;
import pl.aidlalodzi.mapdrawer.service.LineService;
import pl.aidlalodzi.mapdrawer.service.RouteService;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class HeniekTramwajarzApp {

    public static void main(String[] args) {
        SpringApplication.run(HeniekTramwajarzApp.class, args);
    }

    @Bean
    public CommandLineRunner dataPrinter(LineService lineService, RouteService routeService) {
        return args -> {
            System.out.println("Pobieram listę linii...");
            var lines = lineService.getAllLines();
            lines.forEach(System.out::println);
            System.out.println("Pobrano " + lines.size() + " linii.\n\n");

            lines.stream().filter(line -> line.vehicleType() == VehicleType.TRAM).forEach(line -> {
                var routeData = routeService.getAllRoutesByLine(line);
                System.out.println(routeData);
                System.out.println("\n");
            });

        };
    }
}

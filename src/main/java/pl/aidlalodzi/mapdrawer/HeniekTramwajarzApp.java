package pl.aidlalodzi.mapdrawer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.aidlalodzi.mapdrawer.model.v1.FullRoutesInfoPerLine;
import pl.aidlalodzi.mapdrawer.service.LineService;
import pl.aidlalodzi.mapdrawer.service.RouteService;

import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Pobrano " + lines.size() + " linii.");

            List<FullRoutesInfoPerLine> fullRoutesInfoPerLine = new ArrayList<>();
            lines
//                    .stream()
//                    .filter(line -> line.vehicleType() == VehicleType.TRAM)
                    .forEach(line -> {
                        FullRoutesInfoPerLine allRoutesByLine = routeService.getAllRoutesByLine(line);
                        fullRoutesInfoPerLine.add(allRoutesByLine);
                    });


            fullRoutesInfoPerLine
//                    .stream()
//                    .map(e -> e.getVariants().stream().map(RouteVariant::getFrom).toList())
//                    .flatMap(List::stream)
//                    .distinct()
                    .forEach(System.out::println);

        };
    }
}

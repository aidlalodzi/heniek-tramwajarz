package pl.aidlalodzi.mapdrawer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import pl.aidlalodzi.mapdrawer.config.RootConfigReader;
import pl.aidlalodzi.mapdrawer.config.model.AppProperties;
import pl.aidlalodzi.mapdrawer.model.v1.FullRoutesInfoPerLine;
import pl.aidlalodzi.mapdrawer.model.v1.Line;
import pl.aidlalodzi.mapdrawer.service.LineService;
import pl.aidlalodzi.mapdrawer.service.RouteService;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HeniekTramwajarzApp {


    public static void main(String[] args) throws IOException, InterruptedException {

        RootConfigReader configReader = new RootConfigReader("application.yaml");
        AppProperties appProperties = configReader.getRootConfig();

        HttpClient mpkClient = HttpClient.newHttpClient();

        LineService lineService = new LineService(mpkClient, appProperties, new ObjectMapper());
        List<Line> allLines = lineService.getAllLines();

        List<FullRoutesInfoPerLine> fullRoutesInfoPerLine = new ArrayList<>();
        RouteService routeService = new RouteService(mpkClient, appProperties, new ObjectMapper());
        allLines
//                    .stream()
//                    .filter(line -> line.vehicleType() == VehicleType.TRAM)
                .forEach(line -> {
                    FullRoutesInfoPerLine allRoutesByLine = routeService.getAllRoutesByLine(line);
                    fullRoutesInfoPerLine.add(allRoutesByLine);
                });

        System.out.println("fullRoutesInfoPerLine = " + fullRoutesInfoPerLine);

    }
}

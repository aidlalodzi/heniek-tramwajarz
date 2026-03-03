package pl.aidlalodzi.mapdrawer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import pl.aidlalodzi.mapdrawer.config.model.AppProperties;
import pl.aidlalodzi.mapdrawer.config.model.ExcludeEntry;
import pl.aidlalodzi.mapdrawer.model.v1.Line;
import pl.aidlalodzi.mapdrawer.model.v1.VehicleType;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class LineService {

    private final HttpClient mpkClient;
    private final AppProperties appProperties;
    private final ObjectMapper mapper;

    public List<Line> getAllLines() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(appProperties.getRootUrl() + "/Home/GetRouteList"))
                .GET()
                .build();

        HttpResponse<String> response = mpkClient.send(request, HttpResponse.BodyHandlers.ofString());

        return exclude(parseResponse(response.body()));
    }

    private List<Line> parseResponse(String json) {
        try {
            List<Object> raw = mapper.readValue(json, List.class);
            List<Line> lines = new ArrayList<>();

            for (Object item : raw) {
                List<?> pair = (List<?>) item;

                int type = (Integer) pair.get(0);
                VehicleType vehicleType = getVehicleType(type);
                List<?> flatList = (List<?>) pair.get(1);

                for (int i = 0; i < flatList.size(); i += 2) {
                    int id = (Integer) flatList.get(i);
                    String name = (String) flatList.get(i + 1);

                    lines.add(new Line(id, name, vehicleType));
                }
            }

            return lines;

        } catch (Exception e) {
            throw new RuntimeException("Nie udało się sparsować odpowiedzi z GetRouteList", e);
        }
    }

    private static VehicleType getVehicleType(int type) {
        return switch (type) {
            case 0 -> VehicleType.BUS;
            //case 1 -> VehicleType.TRAIN;
            case 2 -> VehicleType.TRAM;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    private List<Line> exclude(List<Line> lines) {
        List<ExcludeEntry> activeExcludeEntries = appProperties.getExcludeLines().stream().filter(ExcludeEntry::isActive).toList();
        return lines.stream().filter(e -> {
            for (ExcludeEntry entry : activeExcludeEntries) {
                if (e.lineIdentifier().matches(entry.getPattern())) {
                    return false;
                }
            }
            return true;
        }).toList();
    }
}


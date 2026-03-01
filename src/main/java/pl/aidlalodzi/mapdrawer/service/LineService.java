package pl.aidlalodzi.mapdrawer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aidlalodzi.mapdrawer.config.MpkConfig;
import pl.aidlalodzi.mapdrawer.config.properties.ExcludeEntry;
import pl.aidlalodzi.mapdrawer.config.properties.GeneralConfigurationProperties;
import pl.aidlalodzi.mapdrawer.model.v1.Line;
import pl.aidlalodzi.mapdrawer.model.v1.VehicleType;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class LineService {

    private final MpkConfig mpkConfig;
    private final GeneralConfigurationProperties gcp;
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Line> getAllLines() {
        String json = mpkConfig.restClient().get()
                .uri("/Home/GetRouteList")
                .retrieve()
                .body(String.class);

        return exclude(parseResponse(json));
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
        List<ExcludeEntry> activeExcludeEntries = gcp.getExcludeLines().stream().filter(ExcludeEntry::isActive).toList();
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


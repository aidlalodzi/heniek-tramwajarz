package pl.aidlalodzi.mapdrawer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aidlalodzi.mapdrawer.config.MpkConfig;
import pl.aidlalodzi.mapdrawer.config.properties.GeneralConfigurationProperties;
import pl.aidlalodzi.mapdrawer.model.v1.*;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RouteService {

    private final MpkConfig mpkConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConvertedData getAllRoutesByLine(Line line) {

        Integer id = line.uniqueId();
        System.out.printf("getAllRoutesByLine: id=%d, linia=%s%n", id, line.lineIdentifier());

        try {
            String json = mpkConfig.restClient().get()
                    .uri("Home/GetTracks?routeId=" + id)
                    .retrieve()
                    .body(String.class);

            return convert(json);
        } catch (Exception e) {
            throw new RuntimeException("Nie udało się pobrać danych trasy o id " + id, e);
        }
    }

    private ConvertedData convert(String rawJson) {

        List<Object> root = objectMapper.readValue(rawJson, List.class);

        List<List<Object>> stopsRaw = (List<List<Object>>) root.get(0);
        List<List<Object>> segmentsRaw = (List<List<Object>>) root.get(1);
        List<List<Object>> variantsRaw = (List<List<Object>>) root.get(3);

        List<Stop> stops = parseStops(stopsRaw);
        List<Segment> segments = parseSegments(segmentsRaw);
        List<RouteVariant> variants = parseVariants(variantsRaw);

        return new ConvertedData(stops, segments, variants);
        //return new ConvertedData();
    }

    private List<Stop> parseStops(List<List<Object>> raw) {
        List<Stop> result = new ArrayList<>();

        for (List<Object> s : raw) {
            result.add(new Stop(
                    ((Number) s.get(0)).longValue(),
                    (String) s.get(1),
                    (String) s.get(2),
                    ((Number) s.get(3)).doubleValue(),
                    ((Number) s.get(4)).doubleValue(),
                    ((Number) s.get(5)).intValue()
            ));
        }
        return result;
    }

    private List<Segment> parseSegments(List<List<Object>> raw) {
        List<Segment> result = new ArrayList<>();

        for (List<Object> seg : raw) {

            List<Double> coordsRaw = (List<Double>) seg.get(3);
            List<GeoPoint> points = new ArrayList<>();

            for (int i = 0; i < coordsRaw.size(); i += 2) {
                points.add(new GeoPoint(
                        coordsRaw.get(i),
                        coordsRaw.get(i + 1)
                ));
            }

            result.add(new Segment(
                    ((Number) seg.get(0)).intValue(),
                    ((Number) seg.get(1)).intValue(),
                    ((Number) seg.get(2)).intValue(),
                    points,
                    ((Number) seg.get(4)).intValue(),
                    ((Number) seg.get(5)).intValue()
            ));
        }

        return result;
    }

    private List<RouteVariant> parseVariants(List<List<Object>> raw) {
        List<RouteVariant> result = new ArrayList<>();

        for (List<Object> v : raw) {
            result.add(new RouteVariant(
                    ((Number) v.get(0)).longValue(),
                    ((Number) v.get(1)).intValue(),
                    ((Number) v.get(2)).intValue(),
                    (String) v.get(3),
                    (String) v.get(4),
                    (String) v.get(5),
                    (List<Integer>) ((List<?>) v.get(6)).get(0)
            ));
        }

        return result;
    }
}

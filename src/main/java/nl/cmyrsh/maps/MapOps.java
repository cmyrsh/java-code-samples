package nl.cmyrsh.maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MapOps {

    private final Integer range;

    public MapOps(Integer range) {
        this.range = range;
    }

    /**
     * adds string values to given map set by range
     * @param stringStringMap
     */
    public Map<String, String> appendMap(Map<String, String> stringStringMap) {
        IntStream.range(0, range)
                .mapToObj(i -> Map.entry("Key" + i, "Value" + i))
                .forEach(pair -> stringStringMap.put(pair.getKey(), pair.getValue()));
        return stringStringMap;
    }

    public Map<String, String> processMap() {
        Map<String, String> map = new HashMap<>();
        map.put("StartKey", "StartValue");
        return appendMap(map);
    }

    public Map<String, String> processMap_Return_Map_copyOf() {
        Map<String, String> map = new HashMap<>();
        map.put("StartKey", "StartValue");
        return Map.copyOf(appendMap(map));
    }


    public Map<String, String> processMap_Return_Collections_unmodifiablemap() {
        Map<String, String> map = new HashMap<>();
        map.put("StartKey", "StartValue");
        return Collections.unmodifiableMap(appendMap(map));
    }

    public Map<String, String> processImmutableMap() {
        return appendImmutableMap(Map.of("StartKey", "StartValue"));
    }

    public Map<String, String> appendImmutableMap(Map<String, String> stringStringMap) {
        return Stream.concat(IntStream.range(0, range)
                .mapToObj(i -> Map.entry("Key" + i, "Value" + i)),
                stringStringMap.entrySet().stream()
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

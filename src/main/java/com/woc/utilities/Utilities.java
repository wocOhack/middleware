package com.woc.utilities;

import java.util.Map;
import java.util.stream.Collectors;

public class Utilities {

    public static String convertWithStream(Map<String, String> map) {
        String mapAsString = map.keySet().stream().map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }
}

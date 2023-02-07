package ru.nsu.fit.enterprise.xml;

import java.util.*;

public class MapCounter {
    Map<String, Long> frequencies;

    public MapCounter() {
        this.frequencies = new HashMap<>();
    }

    public void increase(String key) {
        long count = frequencies.containsKey(key) ? frequencies.get(key) : 0;
        frequencies.put(key, count + 1);
    }

    public Long getFrequencyByKey(String key) {
        return frequencies.get(key);
    }

    public List<Map.Entry<String, Long>> getSortedDirectOrderFrequencies(){
        return frequencies.entrySet().stream().sorted(Map.Entry.comparingByValue()).toList();
    }

    public List<Map.Entry<String, Long>> getSortedReverseOrderFrequencies(){
        return frequencies.entrySet().stream().sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue())).toList();
    }
}

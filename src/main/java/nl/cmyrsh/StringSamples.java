package nl.cmyrsh;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * String samples
 */
public final class StringSamples {



    public List<String> findOccurances(String input) {
        final List<String> output = new ArrayList<>();
        final AtomicInteger index = new AtomicInteger();
        final AtomicInteger bucketIndex = new AtomicInteger();
        final AtomicReference<String> previous = new AtomicReference<>("");


        Map<Integer, Map<String, List<Pair>>> stringListMap = Arrays.stream(input.split("")).map(c -> {

            if (!c.equals(previous.get())) {
                index.set(0);
                bucketIndex.incrementAndGet();
            }
            previous.set(c);

            return new Pair(c, index.incrementAndGet(), bucketIndex.get());
        }).collect(Collectors.groupingBy(Pair::getBucketIndex, Collectors.groupingBy(Pair::getValue)));

        stringListMap.values().forEach(stringListMap1 -> {
            stringListMap1.entrySet().forEach(stringListEntry -> {
                output.add(stringListEntry.getValue().get(stringListEntry.getValue().size() - 1).toString());
            });
        });
        return output;


    }

    private final static class Pair implements Comparable<Pair>{
        String value;
        Integer index;
        Integer bucketIndex;
        Pair(String value, Integer index, Integer bucketIndex) {
            this.value = value;
            this.index = index;
            this.bucketIndex = bucketIndex;
        }

        public Integer getBucketIndex() {
            return bucketIndex;
        }

        public String getValue() {
            return value;
        }

        public Integer getIndex() {
            return index;
        }

        @Override
        public String toString() {
            return  "{ " + value + ":(" + index + ")}";
        }

        @Override
        public int compareTo(Pair integer) {
            return this.index - integer.index;
        }
    }
}

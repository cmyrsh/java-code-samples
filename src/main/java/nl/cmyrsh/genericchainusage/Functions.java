package nl.cmyrsh.genericchainusage;

import nl.cmyrsh.asyncfunctions.GenericChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class Functions {


    public CompletableFuture<Map<String, String>> startProcessing(Map<String, String> source) {
        return CompletableFuture.supplyAsync(() -> {
                source.computeIfAbsent("startKey", s -> String.join(":", UUID.nameUUIDFromBytes(s.getBytes()).toString() ,
                        UUID.randomUUID().toString()));
                return source;
        });
    }

    public CompletableFuture<Map<String, String>> process(Map<String, String> source) {
        return CompletableFuture.supplyAsync(() -> {
            source.computeIfPresent("payload", (k, v) -> String.join(":", v,
                    UUID.nameUUIDFromBytes(String.join("", source.get("startKey"), v).getBytes()).toString()));
            return source;
        });
    }
    public CompletableFuture<Map<String, String>> endProcessing(Map<String, String> source) {
        return CompletableFuture.supplyAsync(() -> {
            source.computeIfAbsent("endKey", s -> String.join(":", UUID.nameUUIDFromBytes(s.getBytes()).toString() ,
                    UUID.randomUUID().toString()));
            return source;
        });
    }


    public Function<Map<String, String>, CompletableFuture<Map<String, String>>> setup(List<String> calls) {
        Map<String, Function<Map<String, String>, CompletableFuture<Map<String, String>>>> fmap = new HashMap<>();
        fmap.put("one", this::startProcessing);
        fmap.put("two", this::process);
        fmap.put("three", this::endProcessing);


        return new GenericChain<Map<String, String>>().createChain(calls, fmap);
    }

    public static void main(String[] args) {
        Functions f = new Functions();
        final Function<Map<String, String>, CompletableFuture<Map<String, String>>> setup = f.setup(List.of("one", "two", "three"));

        Map<String, String> modifiable = new HashMap<>();

        modifiable.put("hello", "world");
        modifiable.put("payload", "a very big text");
        modifiable.put("footer", "a very long tail");

        System.out.println("Final map is " + setup.apply(modifiable).join());
    }
}

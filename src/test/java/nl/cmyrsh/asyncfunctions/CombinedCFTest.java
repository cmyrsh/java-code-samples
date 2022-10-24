package nl.cmyrsh.asyncfunctions;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.IntStream;

class CombinedCFTest {


    final Function<String, CompletableFuture<String>> func1 = s -> CompletableFuture.supplyAsync(
            () -> new StringBuilder(s).reverse().append(" added Func 1").toString()
    );

    final Function<String, CompletableFuture<String>> func2 = s -> CompletableFuture.supplyAsync(
            () -> new StringBuilder(s).insert(1, " ha ").toString()
    );
    final Function<String, CompletableFuture<String>> func3 = s -> CompletableFuture.supplyAsync(
            () -> s.toUpperCase(Locale.ROOT)
    );


    Map<String, Function<String, CompletableFuture<String>>> functionMap = Map.of(
            "one", func1,
            "two", func2,
            "three", func3
    );

    @Test
    void testChain() {




        CombinedCF ccf = new CombinedCF();
        final Function<String, CompletableFuture<String>> chain1 = ccf.createChain(List.of("one"), functionMap);


        IntStream.range(0, 1000)
                .mapToObj(i -> UUID.randomUUID())
                .map(UUID::toString)
                .map(chain1::apply)
                .forEach(f -> System.out.printf("out->%s\n", f.join()));
    }
}
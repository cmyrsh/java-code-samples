package nl.cmyrsh.asyncfunctions;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CombinedCFTest {

    @Test
    void testChain() {
        CombinedCF ccf = new CombinedCF();
        final Function<String, CompletableFuture<String>> chain1 = ccf.createChain(List.of("one"));


        IntStream.range(0, 1000)
                .mapToObj(i -> UUID.randomUUID())
                .map(UUID::toString)
                .map(chain1::apply)
                .forEach(f -> System.out.printf("out->%s\n", f.join()));
    }
}
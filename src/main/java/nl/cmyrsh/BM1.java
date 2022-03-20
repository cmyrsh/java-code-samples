package nl.cmyrsh;

import nl.cmyrsh.functions.Func1;
import nl.cmyrsh.functions.Func2;
import nl.cmyrsh.functions.Func3;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.Throughput)
public class BM1 {

    private static final Logger log = Logger.getLogger(BM1.class.getName());
    private final static List<String> biglist = IntStream.range(0, 20000).mapToObj(i -> UUID.randomUUID().toString()).collect(Collectors.toList());
    private final static Executor pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 2);

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 4, timeUnit = TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 1)
    public void runX() {
        log.log(Level.FINEST, biglist.stream()
                .map(Lambda.getInstance()::process1)
                .map(Lambda.getInstance()::process2)
                .collect(Collectors.joining()));

    }
    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 4, timeUnit = TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 1)
    public void runX1() {
        log.log(Level.FINEST, biglist.parallelStream()
                .map(Lambda.getInstance()::process1)
                .map(Lambda.getInstance()::process2)
                .collect(Collectors.joining()));

    }
    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 4, timeUnit = TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 1)
    public void runY() {
        log.log(Level.FINEST, biglist.stream()
                .map(s -> Lambda.getInstance().call("process1", s))
                .map(s -> Lambda.getInstance().call("process2", s))
                .collect(Collectors.joining()));

    }
    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 4, timeUnit = TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 1)
    public void runY1() {
        log.log(Level.FINEST, biglist.parallelStream()
                .map(s -> Lambda.getInstance().call("process1", s))
                .map(s -> Lambda.getInstance().call("process2", s))
                .collect(Collectors.joining()));

    }
    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 4, timeUnit = TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 1)
    public void runZ1() {
        log.log(Level.FINEST, biglist.stream()
                .map(s -> Lambda.getInstance().applyAll(s))
                .collect(Collectors.joining()));

    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 4, timeUnit = TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 1)
    public void funcTest(){
        List.of(UUID.randomUUID().toString()).stream()
                .map(Func1.getInstance()::correctText)
                .map(Func2.getInstance()::classifyText)
                .map(Func3.getInstance()::save)
                .forEach(s -> log.log(Level.FINEST, s));
    }
    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 4, timeUnit = TimeUnit.NANOSECONDS)
    @Warmup(iterations = 5, time = 1)
    public void funcAsyncTest(){
        CompletableFuture.supplyAsync(() -> UUID.randomUUID().toString(), pool)
                .thenApply(Func1.getInstance()::correctText)
                .thenApply(Func2.getInstance()::classifyText)
                .thenApply(Func3.getInstance()::save)
                .thenAccept(s -> log.log(Level.FINEST, s));
    }
}

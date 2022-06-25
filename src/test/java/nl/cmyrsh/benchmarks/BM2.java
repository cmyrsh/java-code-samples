package nl.cmyrsh.benchmarks;

import nl.cmyrsh.functions.Func1;
import nl.cmyrsh.functions.Func2;
import nl.cmyrsh.functions.Func3;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.Throughput)
public class BM2 {

    final static Integer SIZE = 50;
    private static final Logger log = Logger.getLogger(BM2.class.getName());
    private final static List<String> biglist = IntStream.range(0, SIZE).mapToObj(i -> UUID.randomUUID().toString()).collect(Collectors.toList());
    private final static Executor pool = ForkJoinPool.commonPool();
    private final static Random random = new Random(SIZE);
    private final static Function<Integer, String> ID = i -> biglist.get(Math.abs(i) % SIZE);

    private final static Function<String, String> correctText = Func1.getInstance()::correctText;
    private final static Function<String, String> classifyText = Func2.getInstance()::classifyText;
    private final static Function<String, String> save = Func3.getInstance()::save;
    private final static Consumer<String> eat = s -> log.finest(s);
    private final static ExecutorService three_threads = Executors.newFixedThreadPool(3);

    @Benchmark
    @Fork(value = 1, jvmArgsAppend = "-Xlog:gc=debug::pid,time,uptime", jvmArgs = {"-server",  "-XX:+UnlockExperimentalVMOptions", "-XX:+UseZGC", "-Xms2G", "-Xmx8G", "-Xss32m"})
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 10, batchSize = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void funcTest(Blackhole blackhole){
        List.of(ID.apply(random.nextInt())).stream()
                .map(correctText)
                .map(classifyText)
                .map(save)
                .forEach(eat);
    }
    @Benchmark
    @Fork(value = 1, jvmArgsAppend = "-Xlog:gc=debug::pid,time,uptime", jvmArgs = {"-server",  "-XX:+UnlockExperimentalVMOptions", "-XX:+UseZGC", "-Xms2G", "-Xmx8G", "-Xss32m"})
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 10, batchSize = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void funcAsyncTest(Blackhole blackhole){
        CompletableFuture.supplyAsync(() -> ID.apply(random.nextInt()), pool)
                .thenApply(correctText)
                .thenApply(classifyText)
                .thenApply(save)
                .thenAccept(eat);
    }
    @Benchmark
    @Fork(value = 1, jvmArgsAppend = "-Xlog:gc=debug::pid,time,uptime", jvmArgs = {"-server",  "-XX:+UnlockExperimentalVMOptions", "-XX:+UseZGC", "-Xms2G", "-Xmx8G", "-Xss32m"})
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 10, batchSize = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void funcAsyncTest_threeThreads(Blackhole blackhole){
        CompletableFuture.supplyAsync(() -> ID.apply(random.nextInt()), three_threads)
                .thenApply(correctText)
                .thenApply(classifyText)
                .thenApply(save)
                .thenAccept(eat);
    }
    // @Benchmark
    // @Fork(value = 1, jvmArgsAppend = "-Xlog:gc=debug::pid,time,uptime", jvmArgs = {"-server",  "-XX:+UnlockExperimentalVMOptions", "-XX:+UseZGC", "-Xms2G", "-Xmx8G", "-Xss32m"})
    // @Warmup(iterations = 1, time = 1)
    // @Measurement(iterations = 10, batchSize = 2)
    // @OutputTimeUnit(TimeUnit.MILLISECONDS)
    // public void funcAsyncTest_withVirtualThreads(Blackhole blackhole){
    //     ExecutorService virtualTPool = Executors.newVirtualThreadPool(pool);
    //     CompletableFuture.supplyAsync(() -> ID.apply(random.nextInt()), pool)
    //             .thenApply(correctText)
    //             .thenApply(classifyText)
    //             .thenApply(save)
    //             .thenAccept(eat);
    // }

}

package nl.cmyrsh.benchmarks;

import nl.cmyrsh.maps.MapOps;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * <p>When using {@link java.util.Map} we come across situations where we only want to pass on read-only copy Map to external parties.<br>
 * Although at the same time we append values to it internally at different places in our logic</p>
 * This benchmark tests performance of appending values to a map<br>
 * <p>
 * We test 4 different scenarios<br><br>
 * 1. Create a {@link java.util.HashMap}, put entries into it. And return the map as is.<br>
 *    This return is not secure as it exposes the map for further modifications<br><br>
 * 2. Create a {@link java.util.HashMap}, put entries into it. And return unmodifiable copy of this map using {@link java.util.Collections}.unmodifiableMap Method<br>
 *    This is secure return as receiver gets read-only copy of the map<br><br>
 * 3. Create a {@link java.util.HashMap}, put entries into it. And return unmodifiable copy of this map using {@link java.util.Map}.copyOf Method<br>
 *    This is secure return as receiver gets read-only copy of the map<br><br>
 * 4. Create an unmodifiable {@link java.util.Map} using Map.of method with initial entries. Later append it using {@link java.util.stream.Stream}.concat Method<br>
 *    This is again a secure return as receiver gets read-only copy<br><br>
 * </p>
 *
 */
@BenchmarkMode(Mode.Throughput)
@Fork(value = 3)
//    @Fork(value = 1, jvmArgsAppend = "-Xlog:gc=debug::pid,time,uptime", jvmArgs = {"-server",  "-XX:+UnlockExperimentalVMOptions", "-XX:+UseZGC", "-Xms2G", "-Xmx8G", "-Xss32m"})
@Warmup(iterations = 4, time = 1)
@Measurement(iterations = 3, batchSize = 2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MapBenchmark {

    private MapOps mapOps;
    private final static int RANGE = 100;

    @Setup
    public void init() {
        mapOps = new MapOps(RANGE);
    }

    @Benchmark
    public void benchmark_processMap(Blackhole blackhole){
        blackhole.consume(mapOps.processMap());
    }

    @Benchmark
    public void benchmark_processImmutableMap(Blackhole blackhole){
        blackhole.consume(mapOps.processImmutableMap());
    }

    @Benchmark
    public void benchmark_processMap_Return_Map_copyOf(Blackhole blackhole){
        blackhole.consume(mapOps.processMap_Return_Map_copyOf());
    }

    @Benchmark
    public void benchmark_processMap_Return_Collections_unmodifiablemap(Blackhole blackhole){
        blackhole.consume(mapOps.processMap_Return_Collections_unmodifiablemap());
    }
}

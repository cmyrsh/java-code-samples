package nl.cmyrsh.benchmarks;

import nl.cmyrsh.maps.MapOps;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.Throughput)
@Fork(value = 3)
//    @Fork(value = 1, jvmArgsAppend = "-Xlog:gc=debug::pid,time,uptime", jvmArgs = {"-server",  "-XX:+UnlockExperimentalVMOptions", "-XX:+UseZGC", "-Xms2G", "-Xmx8G", "-Xss32m"})
@Warmup(iterations = 4, time = 1)
@Measurement(iterations = 3, batchSize = 2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MapBenchmark {

    private MapOps mapOps;
    private final static int RANGE = 4096;

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

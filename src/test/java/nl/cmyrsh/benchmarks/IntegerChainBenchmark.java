package nl.cmyrsh.benchmarks;

import nl.cmyrsh.linearchains.IntegerRunner;
import nl.cmyrsh.linearchains.StaticIntegerRunner;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.All)
public class IntegerChainBenchmark {


    private final static IntegerRunner integerRunner = new IntegerRunner();
    private final static StaticIntegerRunner staticIntegerRunner = new StaticIntegerRunner();

    private final static Integer from = 1000;
    private final static Integer to = 100000;

    @Benchmark
    @Fork(value = 1, jvmArgs = {
            "-server",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+PrintAssembly",
            "-XX:+TraceClassLoading",
            "-XX:+LogCompilation",
//            "-XX:+PrintCompilation",
//            "-XX:+UseZGC",
            "-Xms2G",
            "-Xmx8G",
            "-Xss32m"
    })
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, batchSize = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void ssmaTest(Blackhole blackhole){
        IntStream.range(from, to)
//                .map(integerRunner::ssma)
                .map(staticIntegerRunner::ssma).forEach(i -> blackhole.consume(i));
    }

    @Benchmark
//    @Fork(value = 1, jvmArgsAppend = "-Xlog:gc=debug::pid,time,uptime", jvmArgs = {"-server",  "-XX:+UnlockExperimentalVMOptions", "-XX:+PrintCompilation", "-XX:+UseZGC", "-Xms2G", "-Xmx8G", "-Xss32m"})
    @Fork(value = 1, jvmArgs = {
            "-server",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+PrintAssembly",
            "-XX:+TraceClassLoading",
            "-XX:+LogCompilation",
//            "-XX:+PrintCompilation",
//            "-XX:+UseZGC",
            "-Xms2G",
            "-Xmx8G",
            "-Xss32m"
    })
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, batchSize = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void asasTest(Blackhole blackhole){
        IntStream.range(from, to)
//                .map(integerRunner::asas)
                .map(staticIntegerRunner::asas).forEach(i -> blackhole.consume(i));
    }

    @Benchmark
    @Fork(value = 1, jvmArgs = {
            "-server",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+PrintAssembly",
            "-XX:+TraceClassLoading",
            "-XX:+LogCompilation",
//            "-XX:+PrintCompilation",
//            "-XX:+UseZGC",
            "-Xms2G",
            "-Xmx8G",
            "-Xss32m"
    })
    @Warmup(iterations = 1, time = 1)
    @Measurement(iterations = 2, batchSize = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void adamTest(Blackhole blackhole){
        IntStream.range(from, to)
//                .map(integerRunner::adam)
                .map(staticIntegerRunner::adam).forEach(i -> blackhole.consume(i));
    }

}

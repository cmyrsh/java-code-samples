package nl.cmyrsh.linearchains;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class AsyncChainBuilder<T> {


    public Function<T, CompletableFuture<T>> createChain(List<String> calls, Map<String, Function<T, CompletableFuture<T>>> functionMap) {
        return calls.stream()
                .map(s -> functionMap.get(s))
                .reduce(new BinaryOperator<Function<T, CompletableFuture<T>>>() {
                    @Override
                    public Function<T, CompletableFuture<T>> apply(Function<T, CompletableFuture<T>> combined, Function<T, CompletableFuture<T>> next) {
                        return combined.andThen(new Function<CompletableFuture<T>, CompletableFuture<T>>() {
                        @Override
                            public CompletableFuture<T> apply(CompletableFuture<T> resultoflast) {
                                return resultoflast.thenCompose(new Function<T, CompletionStage<T>>() {
                                    @Override
                                    public CompletionStage<T> apply(T s) {
                                        return next.apply(s);
                                    }
                                });
                            }
                        });
                    }
                }).orElse(s -> CompletableFuture.supplyAsync(() -> s));
    }
}

package nl.cmyrsh.asyncfunctions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class GenericChain<T> {


    public Function<T, CompletableFuture<T>> createChain(List<String> calls, Map<String, Function<T, CompletableFuture<T>>> functionMap) {
        return calls.stream()
                .map(s -> functionMap.get(s))
                .reduce(new BinaryOperator<Function<T, CompletableFuture<T>>>() {
                    @Override
                    public Function<T, CompletableFuture<T>> apply(Function<T, CompletableFuture<T>> combined, Function<T, CompletableFuture<T>> next) {
                        return combined.andThen(resultoflast -> resultoflast.thenCompose(s -> next.apply(s)));
                    }
                }).orElse(s -> CompletableFuture.completedFuture(s));
    }
}

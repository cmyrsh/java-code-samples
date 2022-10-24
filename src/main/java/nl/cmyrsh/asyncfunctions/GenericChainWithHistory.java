package nl.cmyrsh.asyncfunctions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class GenericChainWithHistory<T extends Carrier> {


    public Function<T, CompletableFuture<T>> createChain(List<String> calls, Map<String, Function<T, CompletableFuture<T>>> functionMap) {
        return calls.stream()
                .map(s -> functionMap.get(s))
                .reduce(new BinaryOperator<Function<T, CompletableFuture<T>>>() {
                    @Override
                    public Function<T, CompletableFuture<T>> apply(Function<T, CompletableFuture<T>> combined, Function<T, CompletableFuture<T>> next) {
                        return combined.andThen(resultoflast -> resultoflast.thenCompose(s -> {
                            return next.apply(s);
                        }));
                    }
                }).orElse(s -> CompletableFuture.completedFuture(s));
    }

    private static Map<String, String> throwException(String message) {
        throw new IllegalStateException(message);
    }

    private static String throwExceptionMessage(String message) {
        throw new IllegalStateException(message);
    }
    public Function<T, CompletableFuture<T>> createChain(Map<String, Map<String, String>> routes, Map<String, Function<T, CompletableFuture<T>>> functionMap) {
        return routes.keySet().stream()
                .map(s -> functionMap.get(s))
                .reduce(new BinaryOperator<Function<T, CompletableFuture<T>>>() {
                    @Override
                    public Function<T, CompletableFuture<T>> apply(Function<T, CompletableFuture<T>> combined, Function<T, CompletableFuture<T>> next) {
                        return combined.andThen(resultoflast -> resultoflast.thenCompose(s -> {

                            final Map<String, String> route = routes.getOrDefault(s.previousStage(), throwException(s.previousStage()));

                            final String nextCall = route.getOrDefault(s.previousResult(), throwExceptionMessage(""));

                            return functionMap.get(nextCall).apply(s);
                        }));
                    }
                }).orElse(s -> CompletableFuture.completedFuture(s));
    }

}

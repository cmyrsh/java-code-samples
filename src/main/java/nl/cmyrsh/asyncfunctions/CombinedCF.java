package nl.cmyrsh.asyncfunctions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class CombinedCF {


    public Function<String, CompletableFuture<String>> createChain(List<String> calls, Map<String, Function<String, CompletableFuture<String>>> functionMap) {
        return calls.stream()
                .map(s -> functionMap.get(s))
                .reduce(new BinaryOperator<Function<String, CompletableFuture<String>>>() {
                    @Override
                    public Function<String, CompletableFuture<String>> apply(Function<String, CompletableFuture<String>> combined, Function<String, CompletableFuture<String>> next) {
                        return combined.andThen(new Function<CompletableFuture<String>, CompletableFuture<String>>() {
                        @Override
                            public CompletableFuture<String> apply(CompletableFuture<String> resultoflast) {
                                return resultoflast.thenCompose(new Function<String, CompletionStage<String>>() {
                                    @Override
                                    public CompletionStage<String> apply(String s) {
                                        return next.apply(s);
                                    }
                                });
                            }
                        });
                    }
                }).orElse(s -> CompletableFuture.supplyAsync(() -> s));
    }
}

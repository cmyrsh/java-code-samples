package nl.cmyrsh.complexfunctions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CombinedCF {

    final Function<Input<String>, CompletableFuture<Result>> func1 = s -> CompletableFuture.supplyAsync(
            () -> new Result() {
                @Override
                public String getStatus() {
                    return  !s.getpayLoad().isEmpty() ? "OK" : "ERROR";
                }

                @Override
                public String getStatusCode() {
                    return getStatus().equals("OK") ? "0" : "1000";
                }
            }
    );
    final Function<Input<String>, CompletableFuture<Result>> func2 = s -> CompletableFuture.supplyAsync(
            () -> new Result() {
                @Override
                public String getStatus() {
                    return  !s.getpayLoad().isEmpty() ? "OK" : "ERROR";
                }

                @Override
                public String getStatusCode() {
                    return getStatus().equals("OK") ? "0" : "1000";
                }
            }
    );
    final Function<Input<String>, CompletableFuture<Result>> func3 = s -> CompletableFuture.supplyAsync(
            () -> new Result() {
                @Override
                public String getStatus() {
                    return  !s.getpayLoad().isEmpty() ? "OK" : "ERROR";
                }

                @Override
                public String getStatusCode() {
                    return getStatus().equals("OK") ? "0" : "1000";
                }
            }
    );

    Map<String, Function<Input<String>, CompletableFuture<Result>>> cXFunctionMap = Map.of(
            "one", func1,
            "two", func2,
            "three", func3
    );
//    public Function<String, CompletableFuture<String>> createChain(List<String> calls) {
//        return calls.stream()
//                .map(s -> functionMap.get(s))
//                .reduce(new BinaryOperator<Function<String, CompletableFuture<String>>>() {
//                    @Override
//                    public Function<String, CompletableFuture<String>> apply(Function<String, CompletableFuture<String>> combined, Function<String, CompletableFuture<String>> next) {
//                        return combined.andThen(new Function<CompletableFuture<String>, CompletableFuture<String>>() {
//                            @Override
//                            public CompletableFuture<String> apply(CompletableFuture<String> resultoflast) {
//                                return resultoflast.thenCompose(new Function<String, CompletionStage<String>>() {
//                                    @Override
//                                    public CompletionStage<String> apply(String s) {
//                                        return next.apply(s);
//                                    }
//                                });
//                            }
//                        });
//                    }
//                }).orElse(s -> CompletableFuture.supplyAsync(() -> s));
//    }

//    public void createCxChain(Map<String, Map<String, String>> calls) {
//        calls.entrySet().stream()
//                .map(e -> new Node() {
//                    @Override
//                    public String name() {
//                        return e.getKey();
//                    }
//
//                    @Override
//                    public Function<Input<String>, CompletableFuture<Result>> processor() {
//                        return cXFunctionMap.get(e.getKey());
//                    }
//
//                    @Override
//                    public Map<String, Function<Input<String>, CompletableFuture<Result>>> nextOptions() {
//                        return e.getValue()
//                                .entrySet()
//                                .stream()
//                                .map(cE -> Map.entry(cE.getKey(), cXFunctionMap.get(cE.getValue())))
//                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//                    }
//                })
//                .reduce(new BinaryOperator<Node>() {
//                    @Override
//                    public Node apply(Node combined, Node next) {
//                        return new Node() {
//                            @Override
//                            public String name() {
//                                return "final";
//                            }
//
//                            @Override
//                            public Function<Input<String>, CompletableFuture<Result>> processor() {
//                                return combined.processor().andThen(new Function<CompletableFuture<Result>, CompletableFuture<Result>>() {
//                                    @Override
//                                    public CompletableFuture<Result> apply(CompletableFuture<Result> resultOfLast) {
//                                        return resultOfLast.thenCompose(new Function<Result, CompletionStage<Result>>() {
//                                            @Override
//                                            public CompletionStage<Result> apply(Result result) {
//
//
//
//                                                return next.processor();
//                                            }
//                                        });
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public Map<String, Function<Input<String>, CompletableFuture<Result>>> nextOptions() {
//                                return null;
//                            }
//                        };
//                    }
//                });
//    }
    interface Input<T> {
        List<Result> getHistory();
        T getpayLoad();
    }
    interface Result {
        String getStatus();
        String getStatusCode();
    }

    interface Node{

        String name();
        Function<Input<String>, CompletableFuture<Result>> processor();
        Map<String, Function<Input<String>, CompletableFuture<Result>>> nextOptions();
    }
}

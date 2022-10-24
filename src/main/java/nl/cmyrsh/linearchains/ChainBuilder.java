package nl.cmyrsh.linearchains;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ChainBuilder<T> {


    public Function<T, T> createChain(List<String> calls, Map<String, Function<T, T>> functionMap) {
        return calls.stream()
                .map(s -> functionMap.get(s))
                .reduce((combined, next) -> combined.andThen(next))
                .orElse(s -> s);
    }
}

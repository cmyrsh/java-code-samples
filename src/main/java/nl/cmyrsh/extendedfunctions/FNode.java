package nl.cmyrsh.extendedfunctions;

import java.util.List;
import java.util.function.Function;

public class FNode<T, R> implements Function<T, R> {

    private final Integer sequence;
    private final List<Function<T, R>> transitions;

    public FNode(Integer sequence, List<Function<T, R>> transitions) {
        this.sequence = sequence;
        this.transitions = transitions;
    }


    @Override
    public R apply(T t) {
        return null;
    }

    @Override
    public <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        return Function.super.compose(before);
    }

    @Override
    public <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        return Function.super.andThen(after);
    }
}

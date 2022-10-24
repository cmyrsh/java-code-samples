package nl.cmyrsh.linearchains;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StaticIntegerRunner {



    Map<String, Function<Integer, Integer>> functionMap = Map.of(
            "add", IntegerStaticFunctions::add,
            "subtract", IntegerStaticFunctions::subtract,
            "multiply", IntegerStaticFunctions::multiply,
            "divide", IntegerStaticFunctions::divide

    );

    List<String> sub_sub_mult_add = List.of("subtract", "subtract", "multiply", "add");
    List<String> add_sub_add_sub = List.of("add", "subtract", "add", "subtract");
    List<String> add_div_add_mult = List.of("add", "divide", "add", "multiply");


    ChainBuilder<Integer> integerChainBuilder = new ChainBuilder<>();


    final Function<Integer, Integer> _ssma;
    final Function<Integer, Integer> _asas;
    final Function<Integer, Integer> _adam;


    public StaticIntegerRunner() {
        _adam = integerChainBuilder.createChain(add_div_add_mult, functionMap);
        _asas = integerChainBuilder.createChain(add_sub_add_sub, functionMap);
        _ssma = integerChainBuilder.createChain(sub_sub_mult_add, functionMap);
    }


    public Integer ssma(Integer integer) { return _ssma.apply(integer);}

    public Integer asas(Integer integer) { return _asas.apply(integer);}

    public Integer adam(Integer integer) { return _adam.apply(integer);}

}

package nl.cmyrsh.linearchains;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntegerRunner {


    IntegerFunctions integerFunctions = new IntegerFunctions();

    Map<String, Function<Integer, Integer>> functionMap = Map.of(
            "add", integerFunctions::add,
            "subtract", integerFunctions::subtract,
            "multiply", integerFunctions::multiply,
            "divide", integerFunctions::divide

    );

    List<String> sub_sub_mult_add = List.of("subtract", "subtract", "multiply", "add");
    List<String> add_sub_add_sub = List.of("add", "subtract", "add", "subtract");
    List<String> add_div_add_mult = List.of("add", "divide", "add", "multiply");


    ChainBuilder<Integer> integerChainBuilder = new ChainBuilder<>();


    final Map<Integer, Function<Integer, Integer>> chainMap;

    public IntegerRunner() {

        chainMap = new HashMap<>();

        chainMap.put(3, integerChainBuilder.createChain(add_div_add_mult, functionMap));
        chainMap.put(4, integerChainBuilder.createChain(add_sub_add_sub, functionMap));
        chainMap.put(5, integerChainBuilder.createChain(sub_sub_mult_add, functionMap));

    }


    public void process(Integer limit) {
        Long delta = System.nanoTime();
        final String result = preparedStream(limit)
                .map(i -> chainMap.get(i).apply(i))
                .mapToObj(i -> String.format("i = %d", i))
                .collect(Collectors.joining("-"));
        delta = System.nanoTime() - delta;
        System.out.println("Delta Per Op = " + (delta / limit / 1000) + "ms " + result);
    }

    public void process_spaghetti(Integer limit) {
        IntegerFunctions ifunc = new IntegerFunctions();
        Long delta = System.nanoTime();
        final String result = preparedStream(limit)
                .map(i -> {
                    switch (i) {
                        case 3: {
                            // add_div_add_mult
                            return integerFunctions.multiply(integerFunctions.add(integerFunctions.divide(integerFunctions.add(i))));
                        }
                        case 4: {
                            // add_sub_add_sub
                            return integerFunctions.subtract(integerFunctions.add(integerFunctions.subtract(integerFunctions.add(i))));
                        }
                        case 5: {
                            // sub_sub_mult_add
                            return integerFunctions.add(integerFunctions.multiply(integerFunctions.subtract(integerFunctions.subtract(i))));
                        }
                        default: return i;
                    }
                })
                .mapToObj(i -> String.format("i = %d", i))
                .collect(Collectors.joining("-"));
        delta = System.nanoTime() - delta;
        System.out.println("Delta Per Op = " + (delta / limit / 1000) + "ms " + result);
    }

    private static IntStream preparedStream(Integer limit) {
        return IntStream.range(1, limit)
                .filter(i -> {
                    return Math.floorMod(i, 3) == 0 ||
                            Math.floorMod(i, 4) == 0 ||
                            Math.floorMod(i, 5) == 0;

                })
                .map(i -> Math.floorMod(i, 3) == 0 ? 3 : i)
                .map(i -> Math.floorMod(i, 4) == 0 ? 4 : i)
                .map(i -> Math.floorMod(i, 5) == 0 ? 5 : i);
    }


    public static void main(String[] args) {
        Integer limit = 1000;
        if(Stream.of(args).findAny().orElse("").equals("spaghetti")) {
            System.out.println("Calling Spaghetti");
            new IntegerRunner().process_spaghetti(limit);
        } else {
            System.out.println("Calling ChainBuilder");
            new IntegerRunner().process(limit);
        }

    }


}

package nl.cmyrsh;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Lambda {

    private static final Lambda INSTANCE = new Lambda();
    private final Map<String, Function<String, String>> fmap;

    private Lambda() {
        fmap = new HashMap<>();
        fmap.put("process1", this::process1);
        fmap.put("process2", this::process2);
    }

    public static Lambda getInstance() {return INSTANCE;}


    public String call(String function, String input) {
        return fmap.get(function).apply(input);
    }

    public String process1(String input) {
        return input.toLowerCase(Locale.ROOT).replace('A', 'X');
    }
    public String process2(String input) {
        return input.toLowerCase(Locale.ROOT).replace('A', 'X');
    }

    public String applyAll(String input) {
        return fmap.entrySet().stream()
                .map(e -> e.getValue().apply(input))
                .collect(Collectors.joining());
    }
}

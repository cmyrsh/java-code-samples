package nl.cmyrsh.functions;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Func1 implements Handler {

    private static final Func1 INSTANCE = new Func1();
    private final AtomicLong count;

    private Func1(){
        count = new AtomicLong();
    }

    public static Func1 getInstance() {return INSTANCE;}

    @Override
    public Handler init(Map<String, String> config) {
        return INSTANCE;
    }

    public String correctText(String text) {
        return System.nanoTime() + "-" + count.getAndIncrement() + "-" + text.replace("-", "").toUpperCase(Locale.ROOT);
    }

}

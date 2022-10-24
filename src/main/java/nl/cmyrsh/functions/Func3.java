package nl.cmyrsh.functions;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Func3 implements Handler {

    private static final Func3 INSTANCE = new Func3();
    private final AtomicLong count;

    private Func3(){
        count = new AtomicLong();
    }

    public static Func3 getInstance() {return INSTANCE;}

    @Override
    public Handler init(Map<String, String> config) {
        return INSTANCE;
    }

    public String save(String text) {
        String[] t = text.split("-", 3);
        long diff = System.nanoTime() - Long.parseLong(t[0]);
//        final String s = diff + "-" + t[1] + "-" + t[2];
        //System.out.printf("%f id %s save thread %s\n", new BigDecimal(diff).divide(new BigDecimal(1000000)).doubleValue(), t[2], Thread.currentThread().getName());
        return count.incrementAndGet() + "-" + text;
    }

}

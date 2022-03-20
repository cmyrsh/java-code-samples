package nl.cmyrsh.functions;

import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Func2 implements Handler {

    private static final Func2 INSTANCE = new Func2();
    private final Random random;
    private static final Integer MAX_DELAY = 500;
    private final AtomicLong count;


    private Func2(){
        random = new Random();
        count = new AtomicLong();
    }

    public static Func2 getInstance() {return INSTANCE;}

    @Override
    public Handler init(Map<String, String> config) {
        return INSTANCE;
    }

    public String classifyText(String text) {
        delay();
//        String[] t = text.split("-", 3);
//        long diff = System.nanoTime() - Long.parseLong(t[0]);
//        final String s = System.nanoTime() + "-" + t[1] + "-" + t[2];
        //System.out.printf("%d-%s classifyText %d thread %s\n", diff, t[1] + "-" + t[2], count.incrementAndGet(), Thread.currentThread().getName());
        return text.toUpperCase(Locale.ROOT);
    }

    private final void delay() {
        try {
//            Thread.sleep(Math.abs(random.nextInt()) % MAX_DELAY);
            Thread.sleep(Math.abs(MAX_DELAY));
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}

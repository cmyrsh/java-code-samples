package nl.cmyrsh.linearchains;

import java.util.Random;

public class IntegerFunctions {

    
    private static Random random = new Random();

    public Integer add(Integer a) {
        return Math.addExact(a, random.nextInt(100));
    }

    public Integer subtract(Integer a) {
        return Math.subtractExact(a, random.nextInt(100));
    }

    public Integer multiply(Integer a) {
        return Math.multiplyExact(a, random.nextInt(100));
    }

    public Integer divide(Integer a) {
        return Math.floorDiv(a, random.nextInt(100) + 1);
    }

    public Integer mod(Integer a) {
        return Math.floorMod(a, random.nextInt(100));
    }
}

package nl.cmyrsh.linearchains;

import java.util.Random;

public class IntegerStaticFunctions {




    private static Random random = new Random();

    public static Integer add(Integer a) {
        return Math.addExact(a, random.nextInt(a));
    }

    public static Integer subtract(Integer a) {
        return Math.subtractExact(a, random.nextInt(a));
    }

    public static  Integer multiply(Integer a) {
        return Math.multiplyExact(a, random.nextInt(a));
    }

    public static  Integer divide(Integer a) {
        return Math.floorDiv(a, random.nextInt(a));
    }

    public static  Integer mod(Integer a) {
        return Math.floorMod(a, random.nextInt(a));
    }
}

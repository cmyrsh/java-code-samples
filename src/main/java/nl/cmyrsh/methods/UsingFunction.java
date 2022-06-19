package nl.cmyrsh.methods;

import java.util.UUID;
import java.util.function.Function;

public class UsingFunction {



    private static Function<String, String> operate = UUID.randomUUID().toString()::concat;

    public static void main(String[] args) {
        String hello = "Hello World";
        System.out.println(operate.apply(hello));
    }




}

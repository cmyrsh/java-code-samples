package nl.cmyrsh.methods;

import java.util.UUID;

public class UsingMethod {



    private static String operate(String input) {
        return UUID.randomUUID().toString().concat(input);
    }

    public static void main(String[] args) {
        String hello = "Hello World";
        System.out.println(operate(hello));
    }



}

package nl.cmyrsh.asyncfunctions;

import java.util.Map;

public interface Carrier<T> {

    Map<String, T> history();
    T payload();
    String previousResult();

    String previousStage();
}

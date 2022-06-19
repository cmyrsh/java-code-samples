package nl.cmyrsh.extendedfunctions;

import java.util.List;
import java.util.stream.Stream;

public class Input {

    private final List<Result> ealierResults;

    public Input(List<Result> ealierResults) {
        this.ealierResults = ealierResults;
    }

    public Stream<Result> getEalierResults() {
        return ealierResults.stream();
    }
}

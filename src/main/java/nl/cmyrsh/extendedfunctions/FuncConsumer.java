package nl.cmyrsh.extendedfunctions;

import java.util.function.Consumer;
import java.util.function.Function;

public class FuncConsumer<Input, Result> implements Consumer<Function<Input, Result>> {



    @Override
    public void accept(Function<Input, Result> trFunction) {

    }


    public void combine(FuncConsumer<Input, Result> another) {

    }

}

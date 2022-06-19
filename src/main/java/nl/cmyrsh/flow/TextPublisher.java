package nl.cmyrsh.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicReference;

public class TextPublisher implements Flow.Publisher<String>{

    private AtomicReference<Flow.Subscriber> uniqe = new AtomicReference<>();
    @Override
    public void subscribe(Flow.Subscriber<? super String> subscriber) {
        if(!uniqe.compareAndSet(null, subscriber)) {
            throw new IllegalStateException("Only one subscriber allowed");
        }
        subscriber.onSubscribe(new Flow.Subscription() {
            @Override
            public void request(long l) {

            }

            @Override
            public void cancel() {

            }
        });
    }
}

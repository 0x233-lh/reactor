import customDataStream.EventListener;
import customDataStream.EventSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomDataStreamTest {

    @Test
    public void createTest() throws InterruptedException {
        EventSource eventSource = new EventSource();
        Flux.create(sink -> {
            eventSource.register(new EventListener() {
                @Override
                public void onEvent(EventSource.Event event) {
                    sink.next(event);
                }

                @Override
                public void onEventStopped() {
                    sink.complete();
                }
            });
        }).log().subscribe(System.out::println);

        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            TimeUnit.SECONDS.sleep(1);
            eventSource.onEvent(new EventSource.Event(new Date(), "Event-" + i));
        }

        eventSource.eventStopped();
    }

    @Test
    @DisplayName("最简单的generate的方式创建序列")
    public void generateTest() {
        AtomicInteger count = new AtomicInteger();
        Flux.generate(sink -> {
           sink.next("count: " + count.getAndIncrement());
           if (count.get() > 5) {
               sink.complete();
           }
       }).subscribe(System.out::println);

        System.out.println("count: " + count);
    }
}

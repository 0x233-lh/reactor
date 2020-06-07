import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * 调度器的测试
 */
public class SchedulerTest {

    @Test
    public void schedulingTest() {

        Flux.range(1, 10)
                .map(i ->  i * i)
                .log()
                .publishOn(Schedulers.newParallel("parallel"))
                .filter(i -> i % 2 == 0)
                .log()
//                .subscribeOn(Schedulers.newElastic("elastic"))
                .subscribeOn(Schedulers.newElastic("elastic2"))
                .log()
                .blockLast();
    }
}

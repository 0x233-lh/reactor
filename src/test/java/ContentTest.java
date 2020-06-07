import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ContentTest {

    @Test
    @DisplayName("context初步测试")
    public void contextTest() {

        String key = "message";
        Mono<String> r = Mono.just("hello")
                .flatMap(s -> Mono.subscriberContext().map(ctx -> s + " " + ctx.get(key)))
                .subscriberContext(ctx -> ctx.put(key, "world"));

        StepVerifier.create(r)
                .expectNext("hello world")
                .verifyComplete();
    }
}

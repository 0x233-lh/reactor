import com.sun.xml.internal.bind.v2.TODO;
import org.junit.jupiter.api.*;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DisplayName("Reactor API 测试")
public class ReactorApiTest {

    @Test
    @DisplayName("测试名称")
    public void test() {
        Assertions.assertTrue(true);
    }

    @Nested
    class FluxTest {
        @Test
        public void createElement() {
            Flux<String> seq1 = Flux.just("1", "2", "3");
            List<String> listSeq = Stream.of("list1", "list2").collect(Collectors.toList());

        }

        @Test
        @DisplayName("生成一个空的Flux")
        public void emptyFluxTest() {
            Flux.empty().log().subscribe();
        }

        @Test
        @DisplayName("从现有的数据中生成一个Flux")
        public void justFluxTest() {
            Flux<String> flux = Flux.just("el1");

            flux.log().subscribe(System.out::println);
        }

        // Create a Flux that emits an IllegalStateException
        @Test
        public void errorTest() throws InterruptedException {
            Flux.error(new IllegalStateException());
        }

        // Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection

        public void fooBarFluxFromValues() {
            Flux.just("foo", "bar");
        }

        // Create a Flux from a List that contains 2 values "foo" and "bar"
        @Test
        public void fooBarFluxFromList() {
            List<String> list = new ArrayList<>();
            list.add("foo");
            list.add("bar");
            Flux.fromIterable(list);
        }

        //  Create a Flux that emits increasing values from 0 to 9 each 100ms
        @Test
        public void counter() {
            Flux.interval(Duration.ofMillis(100))
                    .take(10);
        }
    }


    @Nested
    class StepVerifierTest {
        //  Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
        @Test
        public void expectFooBarComplete() {
            Flux<String> flux = Flux.just("foo", "bar");
            StepVerifier.create(flux)
                    .expectNext("foo")
                    .expectNext("bar")
                    .verifyComplete();
        }

        //  Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
        @Test
        public void expectFooBarError() {
            Flux<String> flux = Flux.just("foo", "bar");
            StepVerifier.create(flux)
                    .expectNext("foo")
                    .expectNext("bae")
                    .verifyError(RuntimeException.class);
        }

        //  Use StepVerifier to check that the flux parameter emits a User with "swhite"username
        // and another one with "jpinkman" then completes successfully.
        @Test
        public void expectSkylerJesseComplete() {
            Flux<String> flux = Flux.just("swhite", "jpinkman");
            StepVerifier.create(flux)
                    .expectNextMatches(username -> username.equals("swhite"))
                    .assertNext(username -> Assertions.assertEquals("jpinkman", "jpinkman"))
                    .verifyComplete();
        }

        //  Expect 10 elements then complete and notice how long the test takes.
        @Test
        public void expect10Elements() throws InterruptedException {
            Flux<Long> flux = Flux.interval(Duration.ofMillis(1000));

            StepVerifier.create(flux)
                    .expectNextCount(1)
                    .verifyComplete();

            TimeUnit.SECONDS.sleep(100);
        }

    }


    @Nested
    @DisplayName("转换操作符")
    class TransformTest {

        public void test() {
            Mono<User> mono = Mono.just(new User());

            mono.map(user -> {
                user.setFirstname(user.getFirstname().toUpperCase());
                user.setLastname(user.getLastname().toUpperCase());
                return user;
            });
        }


        class User {
            private String firstname;

            private String lastname;

            public String getFirstname() {
                return firstname;
            }

            public String getLastname() {
                return lastname;
            }

            public void setFirstname(String firstname) {
                this.firstname = firstname;
            }

            public void setLastname(String lastname) {
                this.lastname = lastname;
            }
        }
    }

    @Nested
    @DisplayName("融合API测试")
    class MergeTest {


        @Test
        public void mergeFluxWithInterleave() throws InterruptedException {
            Flux<String> flux2 = Flux.just("zoo", "boo");
            Flux<String> flux1 = Flux.just("foo", "bar").delayElements(Duration.ofMillis(1000));
            Flux<String> flux3 = Flux.just("voo", "coo");

            flux2.mergeWith(flux1).subscribe(System.out::println);
            TimeUnit.SECONDS.sleep(10);
        }

        @Test
        public void createFluxFromMultipleMono() {
            Mono<String> mono1 = Mono.just("foo");
            Mono<String> mono2 = Mono.just("bar");
        }
    }

    @Nested
    class CommonTest {


        public void subscribeTest() {

            Disposable subscribe = Flux.range(1, 10).subscribe(System.out::println);

            subscribe.dispose();
        }
    }

}

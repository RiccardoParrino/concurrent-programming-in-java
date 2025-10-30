package com.parrino.riccardo.starter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Example {
    
    public static void example () {
        Mono<String> mono = Mono.just("Hello Reactor");
        mono.subscribe(System.out::println);

        Flux<String> flux = Flux.just("A", "B", "C", "D");
        flux.subscribe(System.out::println);

        Flux<Integer> numbers = Flux.range(1, 5)
            .map( n -> n*n );
        numbers.subscribe(n -> System.out.println("Square: " + n));
    }

}

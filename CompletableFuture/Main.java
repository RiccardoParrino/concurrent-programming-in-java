import java.util.concurrent.CompletableFuture;

public class Main {
    
    public static void main (String [] args) {
        CompletableFutureThenAcceptBoth.example();
    }

}

class CompletableFutureRunnable {

    // runnable return void
    public static void example() {
        CompletableFuture<Void> completableFuture = 
            CompletableFuture.runAsync(
                () -> System.out.println("I'm a runnable!"));

        completableFuture.join();
    }

}

class CompletableFutureSupplier {

    // supplier can return a value
    public static void example() {
        CompletableFuture<String> completableFuture = 
            CompletableFuture.supplyAsync(
                () -> "I'm a supplier");
        
        System.out.println(
            completableFuture
                .join());
    }

}

class CompletableFutureThenApply {

    // thenApply (Function functional interface) can return a value
    public static void example() {
        CompletableFuture<String> completableFuture =
            CompletableFuture.supplyAsync(() -> "hello i'm a supplier");
        
        CompletableFuture<String> future =
            completableFuture.thenApply(String::toUpperCase);

        System.out.println(
            future.join());
    }

}

class CompletableFutureThenAccept {

    // thenAccept (Consumer functional interface) doesn't return a value
    public static void example() {
        CompletableFuture<String> completableFuture =
            CompletableFuture.supplyAsync(() -> "hello i'm a supplier");

        CompletableFuture<Void> future =
            completableFuture.thenAccept(System.out::println);
        
        future.join();
    }

}

class CompletableFutureThenRun {

    // thenRun (Runnable functional interface) doesn't return a value and doesn't accept input parameters
    public static void example() {
        CompletableFuture<String> completableFuture =
            CompletableFuture.supplyAsync(() ->  "hello i'm a supplier");
        
        CompletableFuture<Void> future = 
            completableFuture.thenRun(() -> System.out.println("Computation ended!"));

        future.join();
    }

}

class CompletableFutureThenCompose {

    // thenCompose( Function<? super T, ? extends CompletionStage<U>> fn )
    public static void example() {
        CompletableFuture<Void> completableFuture =
            CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"))
                    .thenAccept(System.out::println);
        
        completableFuture.join();
    }

}

class CompletableFutureThenCombine {

    // thenCombine(CompletableFuture, BiFunction<? super T,? super U,? extends V> fn)
    public static void example() {
        CompletableFuture<Void> completableFuture =
            CompletableFuture.supplyAsync( () -> "Hello" )
                .thenCombine(CompletableFuture.supplyAsync( () -> " World !" ), (s1, s2) -> s1 + s2)
                            .thenAccept(System.out::println);
        completableFuture.join();
    } 

}

class CompletableFutureThenAcceptBoth {

    // thenAcceptBoth( CompletableFuture, BiConsumer<? super String, ? super String> action )
    public static void example() {
        CompletableFuture<Void> completableFuture =
            CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth( CompletableFuture.supplyAsync(() -> " World!!"),
                    (s1,s2) -> System.out.println(s1 + s2) );
        completableFuture.join();
    }

}


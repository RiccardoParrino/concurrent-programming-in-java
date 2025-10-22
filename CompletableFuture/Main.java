import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    
    public static void main (String [] args) {
        CompletableFutureRunnableExecutor.example();
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

class CompletableFutureAllOf {

    public static void example() {
        CompletableFuture<String> future1 =
            CompletableFuture.supplyAsync(() -> "Hello");
        
        CompletableFuture<String> future2 = 
            CompletableFuture.supplyAsync(() -> "Beautiful");
        
        CompletableFuture<String> future3 =
            CompletableFuture.supplyAsync(() ->  "World");
        
        CompletableFuture<Void> combinedFuture =
            CompletableFuture.allOf(future1, future2, future3);

        String combined = 
            Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                    .collect(Collectors.joining(" "));

        System.out.println(combined);
    }

}

class CompletableFutureCompleteExceptionally {

    public static void example() {
        try {
            CompletableFuture<Void> completableFuture = 
                CompletableFuture.runAsync( () -> System.out.println("I'll throw an exception!") );
            
            boolean fail = true; // for some reason
            if (fail) {
                Boolean completed = completableFuture.completeExceptionally(new Exception("An exception from a CompletableFuture!"));
            }

            completableFuture.get();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

class CompletableFutureExceptionally {

    public static void example() {
        CompletableFuture<Integer> completableFuture =
            CompletableFuture.supplyAsync(() -> Integer.valueOf("3f"))
                .exceptionally(ex -> {System.out.println("Exception: Some errors occurred!"); return -1;});

        completableFuture.join();
    }

}

class CompletableFutureHandle {

    public static void example() {
        CompletableFuture<Integer> completableFuture =
            CompletableFuture.supplyAsync(() -> Integer.valueOf("3"))
                .handle( (result, exception) -> {
                    if (exception != null) {
                        System.out.println("Exception: some error occurred!");
                        return -1;
                    } else {
                        System.out.println(result);
                        return result;
                    }
                } );
    }

}

class CompletableFutureRunnableExecutor {

    // runnable return void
    public static void example() {
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            CompletableFuture<String> completableFuture = 
                CompletableFuture.supplyAsync(
                    () -> "Supplier with complete async", executorService);

            System.out.println(completableFuture.join());
            executorService.shutdown();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    
    public static void main (String [] args) {
        VirtualThreadUseCase.example();
    }

}

class VirtualThreadSimple {

    public static void example() {
        try {
            Thread thread = Thread
                .ofVirtual().start(
                    () -> System.out.println("Hello"));
            thread.join();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

class VirtualThreadBuilder {

    public static void example() {
        try {
            Thread.Builder builder = Thread.ofVirtual().name("myThread");
            Runnable task = () -> {
                System.out.println("Running thread");
            };
            Thread t = builder.start(task);
            System.out.println("Thread t name: " + t.getName());
            t.join();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

class VirtualThreadBuilderTwo {

    public static void example() {
        try {
            Thread.Builder builder = Thread.ofVirtual().name("worker-", 0);
            Runnable task  = () -> {
                System.out.println("Thread ID: " + 
                    Thread.currentThread().threadId());
            };

            Thread t1 = builder.start(task);
            t1.join();
            System.out.println(t1.getName() + " terminated");

            Thread t2 = builder.start(task);
            t2.join();
            System.out.println(t2.getName() + " terminated");
        } catch (Exception e) {

        }
    }

}

class VirtualThreadOncePerExecutor {

    public static void example() {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<?> future = executorService
                .submit(() -> System.out.println("Running thread"));
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

class VirtualThreadUseCase {

    public static void example() {
        System.out.println("Hi this is an example of usage of VirtualThread");
        try ( ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor(); ) {
            System.out.println("Starting the 5 seconds VirtualThreads");
            executorService.submit(() -> {
                try {
                    System.out.println("Reading a file or waiting for user input... (5 secs)");
                    Thread.sleep(1000*5); 
                    System.out.println("done! (5 secs)");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Starting the 3 seconds VirtualThreads");
            executorService.submit(() -> {
                try {
                    System.out.println("Reading a file or waiting for user input... (3 secs)");
                    Thread.sleep(1000*3); 
                    System.out.println("done! (3 secs)");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
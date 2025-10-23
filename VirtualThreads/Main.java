import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    
    public static void main (String [] args) {
        Long start = System.currentTimeMillis();
        int res = FibonacciSingleThreads.example(40);
        Long end = System.currentTimeMillis();
        System.out.println(res + " in " + String.valueOf(end-start) + " ms");
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

class FibonacciVirtualThreads {

    private static ExecutorService ExecutorService = Executors.newVirtualThreadPerTaskExecutor();

    public static int example(int n) {
        try{
            return FibonacciVirtualThreads.compute(n);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static int compute(int n) throws Exception {
        if ( n <= 1 ) {
            return 1;
        } else {
            CountDownLatch countDownLatch = new CountDownLatch(2);
            int resMinus1 = FibonacciVirtualThreads
                .ExecutorService
                    .submit(() -> {
                        int res = compute(n-1);
                        countDownLatch.countDown();
                        return res;
                }).get();


            int resMinus2 = FibonacciVirtualThreads
                .ExecutorService
                    .submit(() -> {
                        int res = compute(n-2); 
                        countDownLatch.countDown();
                        return res;
                    }).get();

            return resMinus1 + resMinus2;
        }
    }

}

class FibonacciSingleThreads {

    public static int example(int n) {
        return FibonacciSingleThreads.compute(n);
    }

    private static int compute(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return compute(n-1) + compute(n-2);
        }
    }

}
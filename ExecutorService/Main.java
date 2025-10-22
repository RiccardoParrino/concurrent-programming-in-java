import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main (String [] args) throws Exception {
        ForkJoinPoolExample.example();
    }

}

class FibonacciSingleThread {

    private Integer n;

    public FibonacciSingleThread (Integer n) {
        this.n = n;
    }

    public Integer compute () {
        if ( this.n <= 1 ) {
            return 1;
        } else {
            return 
                new FibonacciSingleThread(n-1).compute() + 
                    new FibonacciSingleThread(n-2).compute();
        }
    }

}

class ForkJoinPoolExample {

    public static void example() {
        ForkJoinPool pool = new ForkJoinPool();
        FibonacciTask fibonacciTask = new FibonacciTask(50);
        Integer result = pool.invoke(fibonacciTask);

        System.out.println(result);
    }

}

class FibonacciTask extends RecursiveTask<Integer> {

    private Integer n;

    public FibonacciTask(Integer n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if ( n <= 1 ) {
            return 1;
        } else {
            FibonacciTask nMinus1 = new FibonacciTask(this.n-1);
            FibonacciTask nMinus2 = new FibonacciTask(this.n-2);

            nMinus2.fork();
            Integer fibMinus1 = nMinus1.compute();
            Integer fibMinus2 = nMinus2.join();

            return fibMinus1 + fibMinus2;
        }
    }

}

class SingleThreadScheduledExecutor {

    public static void example() throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {System.out.println("I'll print after 2 seconds");},
                                                     2l,
                                                     TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }

    public static void example2() throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {System.out.println("I'm a scheduled executor service!");},1l,1l,TimeUnit.SECONDS);

        scheduledExecutorService.schedule( () -> {
                System.out.println("Shutting down...");
                scheduledExecutorService.shutdown();
            }, 
            5, 
            TimeUnit.SECONDS
        );
    }

}

class CachedThreadPool {

    public static void example() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {Thread.sleep(1000 * 10); System.out.println("future1"); return null;});
        executorService.submit(() -> {System.out.println("future2"); return null;});
        executorService.submit(() -> {System.out.println("future3"); return null;});
        executorService.submit(() -> {System.out.println("future4"); return null;});

        executorService.shutdown();
    }

}

class SingleThreadExecutor {

    public static void example() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {Thread.sleep(1000 * 10); System.out.println("future1"); return null;});
        executorService.submit(() -> {Thread.sleep(1000 *  5); System.out.println("future2"); return null;});
        executorService.submit(() -> {System.out.println("future3"); return null;});

        executorService.shutdown();
    }

    public static void example2() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {Thread.sleep(1000 * 10); System.out.println("future1"); return null;});

        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        executorService2.submit(() -> {Thread.sleep(1000 *  5); System.out.println("future2"); return null;});

        ExecutorService executorService3 = Executors.newSingleThreadExecutor();
        executorService3.submit(() -> {System.out.println("future3"); return null;});

        executorService.shutdown();
        executorService2.shutdown();
        executorService3.shutdown();
    }

}

class FixedThreadPool {

    public static void example () throws Exception {
        ExecutorService executoreService = Executors.newFixedThreadPool(2);
        Future<Void> future1 = executoreService.submit(() -> {Thread.sleep(1000 * 10); System.out.println("future1"); return null;});
        Future<Void> future2 = executoreService.submit(() -> {Thread.sleep(1000 *  5); System.out.println("future2"); return null;});
        Future<Void> future3 = executoreService.submit(() -> {System.out.println("future3"); return null;});

        executoreService.shutdown();
    }

}

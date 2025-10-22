import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main (String [] args) throws Exception {
        FixedThreadPool.example();
    }

}

class SingleThreadExecutor {

    public static void example () throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
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

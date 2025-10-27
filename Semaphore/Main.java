import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main (String [] args) throws Exception {
        SemaphoreExample semaphore = new SemaphoreExample();
        CountDownLatch countDownLatch = new CountDownLatch(6);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Runnable command1 = 
            () -> { 
                System.out.println("Executing command-1");
                semaphore.execute();
                countDownLatch.countDown();
            };
        Runnable command2 = 
            () -> { 
                System.out.println("Executing command-2");
                semaphore.execute();
                countDownLatch.countDown();
            };
        Runnable command3 = 
            () -> { 
                System.out.println("Executing command-3");
                semaphore.execute();
                countDownLatch.countDown();
            };
        Runnable command4 = 
            () -> { 
                System.out.println("Executing command-4");
                semaphore.execute();
                countDownLatch.countDown();
            };
        Runnable command5 = 
            () -> { 
                System.out.println("Executing command-5");
                semaphore.execute();
                countDownLatch.countDown();
            };
        Runnable command6 = 
            () -> { 
                System.out.println("Executing command-6");
                semaphore.execute();
                countDownLatch.countDown();
            };

        ArrayList<Runnable> array = new ArrayList<>(5);
        array.add(command1);
        array.add(command2);
        array.add(command3);
        array.add(command4);
        array.add(command5);
        array.add(command6);

        for (Runnable command : array) {
            executorService.submit(command);
        }

        System.out.println("Running all command");

        countDownLatch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
    }

}

class SemaphoreExample {

    static Semaphore semaphore = new Semaphore(1, true);

    public void execute() {
        try {
            System.out.println("Available permit: " + semaphore.availablePermits());
            System.out.println("Number of threads waiting to acquire: " + semaphore.getQueueLength());

            if (semaphore.tryAcquire(10,TimeUnit.SECONDS)) {
                try {
                    System.out.println("Working with my objects and semaphores " + Thread.currentThread().getName());
                } finally {
                    semaphore.release();
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
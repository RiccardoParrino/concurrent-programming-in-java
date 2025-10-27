import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    
    public static void main (String [] args) throws InterruptedException {
        CountDownLatchSimpleUseCase.example();
    }

}

class CountDownLatchSimpleUseCase {

    public static void example() {
        int numberOfThreads = 3;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        Random random = new Random();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < numberOfThreads; i++) {
                Thread t = new Thread(() -> {
                    try {
                        int randomInt = random.nextInt(10);
                        System.out.println("Start a thread! Will expire in " + randomInt + "secs.");
                        Thread.sleep(1000*randomInt);
                        System.out.println("Finished a thread. Expired in " + randomInt + "secs.");
                        latch.countDown();
                    } catch (Exception e) {
                        System.out.println("temp");
                    }
                });
                executorService.submit(t);
            }
            latch.await();
            System.out.println("All of threads are completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    
    /*
     * Runnable
     * Callable
     * Thread
     * Future
     */
    
    public static void main (String [] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        
        Callable<Integer> task = () -> {
            int sum = 0;
            for (int num : numbers) {
                sum += num;
                Thread.sleep(100);
            }
            return sum;
        };

        Future<Integer> future = executor.submit(task);

        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        } finally {
            executor.shutdown();
        }

    }

}

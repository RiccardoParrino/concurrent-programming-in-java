import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    
    /*
     * callable
     * 
     */
    public static void main (String [] args) throws Exception {

        ExecutorService executoreService = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = executoreService.submit(() -> {return 8;});
        Future<Integer> future2 = executoreService.submit(() -> {return 9;});

        Integer res1 = future1.get();
        Integer res2 = future2.get();

        System.out.println(res1);
        System.out.println(res2);

        executoreService.shutdown();

    }

}

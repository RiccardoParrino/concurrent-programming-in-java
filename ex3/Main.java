import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    
    // parallel programming to sum up element of an array
    public static void main (String [] args) throws Exception {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            numbers.add(i);
        
        int mid = numbers.size() / 2;
        List<Integer> firstHalf = numbers.subList(0, mid);
        List<Integer> secondHalf = numbers.subList(mid, numbers.size());

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Integer> future1 = executor.submit(new MySumTask(firstHalf));
        Future<Integer> future2 = executor.submit(new MySumTask(secondHalf));

        int sum2 = future2.get();
        System.out.println("sum2");

        int sum1 = future1.get();
        System.out.println("sum1");

        int total = sum1 + sum2;
        
        System.out.println("ciao");
        executor.shutdown();
    }

}

class MySumTask implements Callable<Integer> {

    private List<Integer> ints;

    public MySumTask(List<Integer> ints) {
        this.ints = ints;
    }

    @Override
    public Integer call() {
        return ints.stream().reduce(0, Integer::sum);
    }
    
}

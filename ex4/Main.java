import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    
    // parallel programming to sum up element of an array
    public static void main (String [] args) throws Exception {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 11; i++)
            numbers.add(i);
        
        int mid = numbers.size() / 2;
        List<Integer> firstHalf = numbers.subList(0, mid);
        List<Integer> secondHalf = numbers.subList(mid, numbers.size());

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> sumList(firstHalf));
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> sumList(secondHalf));

        CompletableFuture<Integer> totalFuture = future1.thenCombine(future2, Integer::sum);

        totalFuture.thenAccept(total -> {
            System.out.println("Total sum: " + total);
        });
    }

    private static int sumList(List<Integer> list) {
        return list.stream().reduce(0, Integer::sum);
    }

}

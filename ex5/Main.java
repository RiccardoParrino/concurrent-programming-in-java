import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class Main {
    
    private static ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main (String [] args) {
        
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 11; i++)
            numbers.add(i);
        
        int mid = numbers.size() / 2;
        List<Integer> firstHalf = numbers.subList(0, mid);
        List<Integer> secondHalf = numbers.subList(mid, numbers.size());

        CompletableFuture<Void> future1 = 
            CompletableFuture
                .supplyAsync(() -> sumList(firstHalf))
                .thenAccept(res -> concurrentHashMap.put("future1", res));

        CompletableFuture<Void> future2 = 
            CompletableFuture
                .supplyAsync(() -> sumList(secondHalf))
                .thenAccept( res -> concurrentHashMap.put("future2", res));

        CompletableFuture<Void> future3 = CompletableFuture
            .allOf(future1, future2)
            .thenRunAsync( () -> printHashMap(concurrentHashMap) );

        future3.join();
    }

    private static Integer sumList(List<Integer> list) {
        return list.stream().reduce(0, Integer::sum);
    }

    private static Void printHashMap(ConcurrentHashMap<String, Integer> concurrentHashMap) {
        for ( Enumeration<String> keys = concurrentHashMap.keys(); keys.hasMoreElements(); ) {
            String key = keys.nextElement();
            System.out.println(key + ": " + concurrentHashMap.get(key));
        }
        return null;
    }

}

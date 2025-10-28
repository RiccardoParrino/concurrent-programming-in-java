import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    
    public static void main (String [] args) {
        ConcurrentHashMapExample.example(); // correct answer
        HashMapExample.example(); // wrong answer
    }

}

class HashMapExample {

    public static void example() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("1", 0);

        Thread t1 = new Thread( () -> {
            for (int i = 0; i < 200_000; i++) {
                hashMap.merge("1", 1, Integer::sum);
            }
        } );

        Thread t2 = new Thread( () -> {
            for (int i = 0; i < 200_000; i++) {
                hashMap.merge("1", 1, Integer::sum);
            }
        } );

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        
        System.out.println(hashMap.get("1"));
    }

}

class ConcurrentHashMapExample {

    public static void example() {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("1", 0);

        Thread t1 = new Thread( () -> {
            for (int i = 0; i < 200_000; i++) {
                concurrentHashMap.merge("1", 1, Integer::sum);
            }
        } );

        Thread t2 = new Thread( () -> {
            for (int i = 0; i < 200_000; i++) {
                concurrentHashMap.merge("1", 1, Integer::sum);
            }
        } );

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        
        System.out.println(concurrentHashMap.get("1"));
    }

}
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    
    public static void main(String [] args) {
        AtomicIntegerExample.example();
    }

}

class IntegerExample {

    private static class Counter {
        Integer counter = 0;

        public void increment() {
            this.counter++;
        }

        public Integer getCounter() {
            return this.counter;
        }
    }

    public static void example() {
        Counter counter = new Counter();

        Thread t1 = new Thread( () -> {
            for (int i = 0; i < 200_000; i++) {
                counter.increment();
            }
        } );

        Thread t2 = new Thread( () -> {
            for (int i = 0; i < 200_000; i++) {
                counter.increment();
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
        
        System.out.println(counter.getCounter());
    }

}

class AtomicIntegerExample {

    public static void example() {
        AtomicInteger counter = new AtomicInteger(0);

        Thread t1 = new Thread( () -> {
            for (int i = 0; i < 200_000; i++) {
                counter.incrementAndGet();
            }
        } );

        Thread t2 = new Thread( () -> {
            for (int i = 0; i < 200_000; i++) {
                counter.incrementAndGet();
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
        
        System.out.println(counter.get());
    }
    
}
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    
    public static void main(String [] args) {

    }

}

class SafeCounterWithLock {

    private int counter = 0;

    public int getValue() {
        return this.counter;
    }

    public synchronized void increment() {
        counter++;
    }

}

class SafeCounterWithoutLock {

    private final AtomicInteger counter = new AtomicInteger(0);

    int getValue() {
        return counter.get();
    }

    void increment() {
        counter.incrementAndGet();
    }

}

class AtomicLong {

    

}
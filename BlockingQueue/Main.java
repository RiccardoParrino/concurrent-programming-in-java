import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    
    public static void main (String [] args) {
        ProducerConsumerExample.example();
    }

}

/**
 * A producer/consumer program is a classic example of a multi-threading 
 * or concurrent programming pattern where two or more processes share a common,
 * fixed-size buffer
 * 
 * Producer: produce data to put into a shared buffer or queue, 
 *           waiting if the buffer is full.
 * Consumer: consume data from the shared buffer or queue,
 *           waiting if the buffer is empty.
 * Shared buffer: a finite-size space that holds the data temporarily 
 *                between producers and consumers.
 */

class ProducerConsumerExample {

    private static final int BUFFER_SIZE = 5;
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(BUFFER_SIZE);

    public static void example() {
        Thread producerThread = new Thread(new Producer());
        Thread consumerThread = new Thread(new Consumer());

        producerThread.start();
        consumerThread.start();
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            int item = 0;
            try {
                while(true) {
                    item++;
                    System.out.println("Produced: " + item);
                    queue.put(item);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer implements Runnable {
        
        @Override
        public void run() {
            try {
                while (true) {
                    int item = queue.take();
                    System.out.println("Consumed: " + item);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    
    }

}

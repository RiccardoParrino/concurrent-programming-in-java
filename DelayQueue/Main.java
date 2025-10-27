import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    
    public static void main (String [] args) throws Exception {
        DelayQueueExample.example();
    }

}

class DelayQueueExample {

    public static void example() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 10;
        Long delayOfEachProducedMessageMilliseconds = 500L;
        
        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);
        DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        executorService.submit(producer);
        executorService.submit(consumer);

        executorService.awaitTermination(60, TimeUnit.SECONDS);
        executorService.shutdown();
    }

}

class DelayObject implements Delayed {

    private String data;
    private Long startTime;

    public DelayObject(String data, Long delayInMilliseconds) {
        this.data = data;
        this.startTime = System.currentTimeMillis() + delayInMilliseconds;
    }

    @Override
    public int compareTo(Delayed o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    @Override
    public long getDelay(TimeUnit unit) {
        Long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }
    
}

class DelayQueueProducer implements Runnable {

    private BlockingQueue<DelayObject> queue;
    private Integer numberOfelementsToProduce;
    private Long delayOfEachProducedMessageMilliseconds;

    public DelayQueueProducer (BlockingQueue<DelayObject> queue, Integer numberOfelementsToProduce, Long delayOfEachProducedMessageMilliseconds) {
        this.queue = queue;
        this.numberOfelementsToProduce = numberOfelementsToProduce;
        this.delayOfEachProducedMessageMilliseconds = delayOfEachProducedMessageMilliseconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfelementsToProduce; i++) {
            System.out.println("Producer: " + i + ": " + numberOfelementsToProduce);
            DelayObject object = new DelayObject(
                UUID.randomUUID().toString(), 
                delayOfEachProducedMessageMilliseconds
            );
            System.out.println("Put object: " + object);
            try {
                queue.put(object);
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

}

class DelayQueueConsumer implements Runnable {

    private BlockingQueue<DelayObject> queue;
    private Integer numberOfElementsToTake;
    public AtomicInteger numberOfConsumedElements = new AtomicInteger(0);

    public DelayQueueConsumer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToTake) {
        this.queue = queue;
        this.numberOfElementsToTake = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToTake; i++) {
            try {
                System.out.println("Consumer: " + i);
                DelayObject object = queue.take();
                numberOfConsumedElements.incrementAndGet();
                System.out.println("Consumer take: " + object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
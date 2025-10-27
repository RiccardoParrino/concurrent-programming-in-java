import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String [] args) {
        int numberOfWorkers = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfWorkers, () -> {
            System.out.println("All workers finished phase 1 moving to phase 2!");
        });

        for (int i = 0; i < numberOfWorkers; i++) {
            new Thread(new Worker(barrier, i)).start();
        }
    }

    static class Worker implements Runnable {

        private final CyclicBarrier barrier;
        private final int id;

        public Worker(CyclicBarrier barrier, int id) {
            this.barrier = barrier;
            this.id = id;
        }

        @Override
        public void run() {

            try {
                System.out.println("Worker " + id + " doing phase 1 work...");
                Thread.sleep((long) (Math.random() * 2000));

                System.out.println("Worker " + id + " waiting at barrier");
                barrier.await(); // Wait for others

                System.out.println("Worker " + id + " starting phase 2 work...");
                Thread.sleep((long) (Math.random() * 2000));
                
                System.out.println("Worker " + id + " waiting at barrier");
                barrier.await(); // Wait for others and barrier is reused

                System.out.println("Worker " + id + " starting phase 3 work...");
                Thread.sleep((long) (Math.random() * 2000));

                System.out.println("Worker " + id + " done!");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

    }

}


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main (String [] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // try use one or other example to see the effects
        // CyclingVariable example = new NonVolatileExample();
        CyclingVariable example = new VolatileExample(); 

        MyFirstThread myFirstThread = new MyFirstThread(example);
        MySecondThread mySecondThread = new MySecondThread(example);

        executorService.submit(myFirstThread);
        executorService.submit(mySecondThread);

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (Exception e) {}

    }

}

interface CyclingVariable {
    boolean getCycle();
    void setCycle(boolean cycle);
}

class MyFirstThread implements Runnable {

    private CyclingVariable example;

    public MyFirstThread(CyclingVariable example) {
        this.example = example;
    }

    @Override
    public void run() {
        while( this.example.getCycle() ) {
        }
        System.out.println("Exiting from cycle!");
    }    

}

class MySecondThread implements Runnable{

    private CyclingVariable example;

    public MySecondThread(CyclingVariable example) {
        this.example = example;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000*5); // wait for 5 seconds
            this.example.setCycle(false);
            System.out.println("I'm the second thread: I've changed the non-volatile variable!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }  
}

class NonVolatileExample implements CyclingVariable {

    private boolean cycle = true;

    public boolean getCycle() {
        return this.cycle;
    }

    public void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

}

class VolatileExample implements CyclingVariable {

    private volatile boolean cycle = true;

    public boolean getCycle() {
        return this.cycle;
    }

    public void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

}
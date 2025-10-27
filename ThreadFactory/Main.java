import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {
    
    public static void main (String [] args) {
        DefaultThreadFactoryExample.example();
    }

}

class MyThreadExtendsThread extends Thread {

    @Override
    public void run() {
        System.out.println("This is a thread");
    }

}

class MyThreadImplementsRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("This is a thread");
    }

}

class MyThreadImplementsThreadFactory implements ThreadFactory {

    private int count = 0;

    public int getCount() {
        return count;
    }

    // newThread is a factory method
    // provided by ThreadFactory
    @Override
    public Thread newThread(Runnable command) {
        count++;
        return new Thread(command);
    }

}

class ThreadFactoryUseCase {

    public static void example() {
        MyThreadImplementsThreadFactory threadFactory = new MyThreadImplementsThreadFactory();

        Runnable command1 = () -> System.out.println("Command 1 executed");
        Runnable command2 = () -> System.out.println("Command 2 executed");
        Runnable command3 = () -> System.out.println("Command 3 executed");
        Runnable command4 = () -> System.out.println("Command 4 executed");
        Runnable command5 = () -> System.out.println("Command 5 executed");
        Runnable command6 = () -> System.out.println("Command 6 executed");

        ArrayList<Runnable> array = new ArrayList<>(5);
        array.add(command1);
        array.add(command2);
        array.add(command3);
        array.add(command4);
        array.add(command5);
        array.add(command6);

        for (Runnable command : array) {
            threadFactory.newThread(command).start();
        }

        System.out.println(
            "Total number of threads created using CustomThreadFactory = "
            + threadFactory.getCount()
        );
    }

}

class DefaultThreadFactoryExample {

    public static void example () {
        // Thread group-1 
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        for (int i = 0; i < 10; i++) {
            Thread thread = threadFactory.newThread(() -> System.out.println("I'm a new Thread!"));
            System.out.println("Name given by threadFactory = " + thread.getName());
            thread.start();
        }

        // Thread group-2
        ThreadFactory threadFactory2 = Executors.defaultThreadFactory();
        for (int i = 0; i < 10; i++) {
            Thread thread = threadFactory2.newThread(() -> System.out.println("I'm a new Thread!"));
            System.out.println("Name given by threadFactory = " + thread.getName());
            thread.start();
        }
    }

}

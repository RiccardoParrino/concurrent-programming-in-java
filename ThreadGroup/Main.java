
public class Main {
    
    public static void main (String [] args) {
        UtilityThreadGroup.example();
    }

}

class NewThread extends Thread {

    public NewThread(String threadName, ThreadGroup threadGroup) {
        super(threadGroup, threadName);
        start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(super.getThreadGroup().getName() + " " + super.currentThread().getName() + " " + i + " " + super.isInterrupted() );
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}

class SimpleThreadGroupExample {

    public static void example() {
        ThreadGroup t1 = new ThreadGroup("thread-group-1");

        NewThread t11 = new NewThread("core-1", t1);
        NewThread t21 = new NewThread("core-2", t1);
        NewThread t31 = new NewThread("core-3", t1);
        NewThread t41 = new NewThread("core-4", t1);

        ThreadGroup t2 = new ThreadGroup("thread-group-2");

        NewThread t12 = new NewThread("core-1", t2);
        NewThread t22 = new NewThread("core-2", t2);
        NewThread t32 = new NewThread("core-3", t2);
        NewThread t42 = new NewThread("core-4", t2);
        NewThread t52 = new NewThread("core-5", t2);

        System.out.println("Number of active thread: " + t1.activeCount());
        System.out.println("Number of active thread: " + t2.activeCount());
    }

}

class UtilityThreadGroup {

    public static void example() {
        ThreadGroup g = new ThreadGroup("Parent thread");
        ThreadGroup g_child = new ThreadGroup(g, "child-thread");

        System.out.println("Maximum priority of ParentThreadGroup = " + g.getMaxPriority());

        NewThread t1 = new NewThread("one", g);
        NewThread t2 = new NewThread("two", g);

        Thread[] list = new Thread[g.activeCount()];

        System.out.println(t1.getName());
        System.out.println(t2.getName());
        System.out.println(g_child.getParent().getName());
        System.out.println(g.enumerate(list, false));
        System.out.println(list.length);

        // try {
        //     Thread.sleep(100);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // g.interrupt();

        System.out.println(g);
    }

}
public class Main {
    
    public static void main (String [] args) throws Exception {
        MyState myState = new MyState();
        MyThread myThread1 = new MyThread("thread-0", myState);
        MyThread myThread2 = new MyThread("thread-1", myState);
        
        Thread t1 = new Thread(myThread1);
        Thread t2 = new Thread(myThread2);

        t1.start();
        t2.start();
    }

}

class MyState {

    private Integer currentState;

    public MyState() {
        this.currentState = 0;
    }

    public void addState() throws Exception {
        wait(10 * 1000); // wait for ten seconds
        this.currentState += 1;
        System.out.println(this.currentState);
    }

    @Override
    public String toString() {
        return String.valueOf(this.currentState);
    }

}

class MyThread implements Runnable {
    
    private String name;
    private MyState myState;

    public MyThread(String name, MyState myState) {
        this.name = name;
        this.myState = myState;
    }

    @Override
    public void run() {
        System.out.println(this.name);
        this.doSomething();
    }

    public void doSomething() {
        try {
            this.myState.addState();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
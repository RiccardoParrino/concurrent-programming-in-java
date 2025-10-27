public class Main {
    
    public static void main (String [] args) {
        DaemonExample.example();
    }

}

class DaemonExample {

    public static void example () {
        Thread daemonThread = new Thread( () -> {
            while(true) {
                System.out.println("Daemon thread running...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        daemonThread.setDaemon(true);
        daemonThread.start();

        int i = 0;
        while (i < 100) {
            i = i + 1;
            System.out.println("printing: " + i);
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Main thread finished.");
    }

}
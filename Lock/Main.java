import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Main {
    
    public static void main(String [] args) {

    }

}

class SimpleLockExample {

    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}
/**
 * lock.tryLock()
 * lock.lockInterruptibly()
 * 
 */

class SimpleFairnessControlExample {

    private int count = 0;
    private final Lock fairLock = new ReentrantLock(true);

    public void increment() {
        fairLock.lock();
        try {
            count++;
        } finally {
            fairLock.unlock();
        }
    }

    public int getCount() {
        return count;
    }

}

class BoundedBuffer {

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Object[] items = new Object[10];
    private int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putptr] = x;
            if (++putptr == items.length)
                putptr = 0;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length)
                takeptr = 0;
            count--;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

}
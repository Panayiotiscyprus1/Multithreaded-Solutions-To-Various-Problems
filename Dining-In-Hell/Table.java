import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Table {
    final int N = 6;
    boolean b0 = false;
    boolean b2 = false;
    private final Queue<Integer> waitingQueue = new LinkedList<>();

    public Table() {
        for (int i = 0; i < N; i++) waitingQueue.add(i);
    }

    //called by feeder, return target to feed
    public synchronized int Feed(int feeder) throws InterruptedException {
        if (feeder == 0){
            while (b0 == false) {
                wait();
                b0 = !b0; // toggle b0 after being notified
            }
        }
        if (feeder == 2){
            while (b2 == false) {
                wait();
                b2 = !b2; // toggle b0 after being notified
            }
        }
        while (waitingQueue.peek() != null && waitingQueue.peek() == feeder) {
            wait();
        }
        
        int target = waitingQueue.poll();
        notifyAll(); // head changed -> wake waiters
        return target;
    }
    //called by feeder, simulate feeding -> not synced to allow concurrent feeding
    public void Feeding(int feeder, int target) throws InterruptedException {
        System.out.println("Thread " + feeder + " feeds thread " + target);
        if (feeder == 0) b0 = !b0; // toggle b0 after feeding
        if (feeder == 2) b2 = !b2; // toggle b0 after feeding
        Thread.sleep(ThreadLocalRandom.current().nextInt(500, 9000));
    }

    // called by feeder, after feeding is done, add target back to waiting queue
    public synchronized void Done(int target) {
        waitingQueue.add(target);
        notifyAll();
    }
}
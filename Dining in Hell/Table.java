import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Table {
    final int N = 5;
    private final Queue<Integer> waitingQueue = new LinkedList<>();

    public void initTable() {
        for (int i = 0; i < N; i++) waitingQueue.add(i);
    }

    public synchronized int Feed(int feeder) throws InterruptedException {
        while (waitingQueue.peek() != null && waitingQueue.peek() == feeder) {
            wait();
        }
        int target = waitingQueue.poll();   // take head (someone else)
        notifyAll();                        // head changed -> wake waiters
        return target;
    }

    public void Feeding(int feeder, int target) throws InterruptedException {
        System.out.println("Thread " + feeder + " feeds thread " + target);
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
    }

    public synchronized void Done(int target) {
        waitingQueue.add(target);
        notifyAll();
    }
}
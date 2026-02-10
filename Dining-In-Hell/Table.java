import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Table {
    final int N = 6;
    private final Queue<Integer> waitingQueue = new LinkedList<>();

    public Table() {
        for (int i = 0; i < N; i++) waitingQueue.add(i);
    }

    public synchronized int Feed(int feeder) throws InterruptedException {
        while (waitingQueue.peek() != null && waitingQueue.peek() == feeder) {
            wait();
        }
        
        int target = waitingQueue.poll();
        notifyAll(); // head changed -> wake waiters
        return target;
    }

    public void Feeding(int feeder, int target) throws InterruptedException {
        System.out.println("Thread " + feeder + " feeds thread " + target);
        Thread.sleep(ThreadLocalRandom.current().nextInt(500, 3500));
    }

    public synchronized void Done(int target) {
        waitingQueue.add(target);
        notifyAll();
    }
}
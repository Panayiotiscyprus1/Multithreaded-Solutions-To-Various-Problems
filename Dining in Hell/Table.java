import java.util.*;

public class Table{

    final int N=5;

    private Queue<Integer> waitingQueue = new LinkedList<>();

    public void initTable(){
        for(int i = 0; i < 5; i++) waitingQueue.add(i);
    }

    public synchronized Feed(int feeder){
        
        while(waitingQueue.peek() != feeder){
            wait();
        }

        waitingQueue.poll();
        System.out.println("Thread " + feeder + "feeds thread " + target);
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000));

    }

    public synchronized Done(int feeder){
        waitingQueue.add(feeder);
        notifyAll();
    }

}
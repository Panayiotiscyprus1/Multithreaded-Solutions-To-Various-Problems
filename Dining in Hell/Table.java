import java.util.*;

public class Table{

    final int N=5;

    private Queue<Integer> waitingQueue = new LinkedList<>();
    private boolean[] beingFed = new boolean[N];


    public synchronized requestToFeed(int feeder, int target){
        
        feedersQ.add(feeder);

        while(feedersQ.peek != feeder || beingFed[target]){
            wait();
        }
    }

}
import java.util.ArrayDeque;
import java.util.Deque;

public class Table{

    final int N=5;

    private Deque<Integer> q = new ArrayDeque<>();
    private boolean[] beingFed = new boolean[N];



    public synchronized requestToFeed(int personID){
        if(beingFed[personID] == true){
            wait();
        }else{
            try {
                int person_id = q.removeFirst();
                beingFed[personID] = true;
            }catch (Exception e) {
                System.out.println("queue is empty");
            }
        }

    }

}
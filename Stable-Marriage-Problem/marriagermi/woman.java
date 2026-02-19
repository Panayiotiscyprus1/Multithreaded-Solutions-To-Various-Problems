import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class woman extends UnicastRemoteObject implements womanI {

    private int id;
    private int partnerId;
    private int[] preferences = new int[5];
    private int[] rank = new int[5];

    public woman(int id) throws RemoteException {
        // export object
        super();
        this.id = id;
        this.partnerId = -1;
        orderPref();
    }

    // Generate random preference order and ranking
    public void orderPref() {
        List<Integer> men = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            men.add(i);
        }

        Collections.shuffle(men);

        for (int pos = 0; pos < 5; pos++) {
            int manId = men.get(pos);
            preferences[pos] = manId;
            rank[manId] = pos;
        }
    }

    public synchronized String onProposal(int manId) throws RemoteException {

        if (partnerId == -1) {
            partnerId = manId;
            return "ACCEPT";
        }

        if (rank[manId] < rank[partnerId]) {
            Man old = (Man)Naming.lookup(partnerId);
            old.onResponse("DUMP");
            
            partnerId = manId;
            return "ACCEPT";
        }

        return "REJECT";
    }

    public int getId() throws RemoteException {
        return id;
    }

    public int getPartnerId() throws RemoteException {
        return partnerId;
    }
}

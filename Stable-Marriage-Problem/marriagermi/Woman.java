import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.rmi.Naming;

public class Woman extends UnicastRemoteObject implements womanI {
    final int N = Driver.N;
    private int id;
    private int partnerId;
    private int[] preferences = new int[N];
    private int[] rank = new int[N];

    public Woman(int id) throws RemoteException {
        // export object
        super();
        this.id = id;
        this.partnerId = -1;
        orderPref();
    }

    // Generate random preference order and ranking
    public void orderPref() {
        List<Integer> men = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            men.add(i);
        }

        Collections.shuffle(men);

        for (int pos = 0; pos < N; pos++) {
            int manId = men.get(pos);
            preferences[pos] = manId;
            rank[manId] = pos;
        }
    }

    public String onProposal(int manId) throws RemoteException {
        int oldToDump = -1;
        boolean accept = false;

        synchronized (this) {
            if (partnerId == -1) {
                partnerId = manId;
                Driver.inc();
                Driver.trace("W" + id + " accepts M" + manId);
                accept = true;
            }

            else if (rank[manId] < rank[partnerId]) {
                oldToDump = partnerId;
                partnerId = manId;
                Driver.trace("W"+id+" ACCEPT M"+manId);
                accept = true;
            }
        }

        if (oldToDump != -1){
            try{
                manI old = (manI) Naming.lookup("rmi://localhost/Man" + oldToDump);
                Driver.trace("W"+ id + " DUMP M"+ oldToDump +" (better M" + manId +")");
                old.onResponse("DUMP", this.id);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if (accept) return "ACCEPT";
        
        Driver.trace("W"+id+" REJECT M"+manId);
        return "REJECT";
    }

    public int getId() throws RemoteException {
        return id;
    }

    public int getPartnerId() throws RemoteException {
        return partnerId;
    }
}

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.Remote;

public class Man extends UnicastRemoteObject implements manI {
    final int N = Driver.N;
    public final int id;
    public int[] preferences = new int[N];
    private int partnerId;
    private int proposalIndex = -1;

    public Man(int id) throws Exception {
        super();
        this.id = id;
        orderPreferences();
        this.partnerId = -1;

    }

    public int getId() {
        return this.id;
    }

    public int getPartnerId() {
        return this.partnerId;
    }

    public void onResponse(String response, int womanId) throws RemoteException {
        if (response.equals("ACCEPT")) {
            this.partnerId = womanId;
            Driver.trace("M" + id +" ENGAGED to W"+ womanId);
        } else if (response.equals("REJECT") || response.equals("DUMP")) {
            this.partnerId = -1;
            proposeNext();
        }
    }

    public void orderPreferences() {
        // Create a list to hold numbers from 0 to preferences.length - 1
        List<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < preferences.length; i++) {
            tempList.add(i);
        }
    
        // Shuffle the list to randomize the order
        Collections.shuffle(tempList);
    
        // Copy the shuffled numbers back into the preferences array
        for (int i = 0; i < preferences.length; i++) {
            preferences[i] = tempList.get(i);
        }
    }

    public void proposeNext() throws RemoteException {
        proposalIndex++;
        if (proposalIndex >= preferences.length) return; 
        int wId = preferences[proposalIndex];
        try{
        womanI w = (womanI) Naming.lookup("rmi://localhost/Woman" + wId);
        Driver.trace("M"+id+" -> W"+wId+" PROPOSE");
        String resp = w.onProposal(this.id);
        onResponse(resp, wId);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

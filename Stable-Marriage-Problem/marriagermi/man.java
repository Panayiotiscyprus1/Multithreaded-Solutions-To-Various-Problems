import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Man extends UnicastRemoteObject implements manI {
    public final int id;
    public final int[] preferences;
    private int partnerId;
    private int proposalIndex = -1;

    public Man(int id, int[] preferences) throws Exception {
        super();
        this.id = id;
        this.preferences = preferences;
        this.partnerId = -1;
    }

    public int getId() {
        return this.id;
    }

    public int getPartnerId() {
        return this.partnerId;
    }

    public void onResponse(String response, int womanId) {
        if (response.equals("ACCEPT")) {
            this.partnerId = womanId;
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

    public void proposeNext(){
        proposalIndex++;
        Woman current = (Woman)Naming.lookup(preferences(proposalIndex));
        current.onProposal(this.id);
    }
}

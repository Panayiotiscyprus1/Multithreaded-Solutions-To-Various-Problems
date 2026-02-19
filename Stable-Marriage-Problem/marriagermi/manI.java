import java.rmi.Remote;
import java.rmi.RemoteException;

public interface manI extends Remote {
    // Woman -> Man message
    // response could be "ACCEPT", "REJECT", "DUMP"
    void onResponse(String response, String womanId) throws RemoteException;
    String getId() throws RemoteException;
    String getPartnerId() throws RemoteException;
}

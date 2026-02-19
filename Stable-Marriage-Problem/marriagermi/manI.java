import java.rmi.Remote;
import java.rmi.RemoteException;

public interface manI extends Remote {
    // Woman -> Man message
    // response could be "ACCEPT", "REJECT", "DUMP"
    void onResponse(String response, int womanId) throws RemoteException;
    int getId() throws RemoteException;
    int getPartnerId() throws RemoteException;
}

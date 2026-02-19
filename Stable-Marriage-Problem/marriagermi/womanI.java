import java.rmi.Remote;
import java.rmi.RemoteException;

public interface womanI extends Remote {
    String onProposal(int manId) throws RemoteException;
    int getId() throws RemoteException;
    int getPartnerId() throws RemoteException;
}

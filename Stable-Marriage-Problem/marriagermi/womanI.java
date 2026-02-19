package marriagermi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface womanI extends Remote {
    String onProposal(String manId) throws RemoteException;
    String getId() throws RemoteException;
    String getPartnerId() throws RemoteException;
}

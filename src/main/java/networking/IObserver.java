package networking;


import model.Concert;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
     void concertUpdated(Concert concert) throws RemoteException;
}

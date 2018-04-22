package controller;

import javafx.collections.FXCollections;
import model.Concert;
import networking.IObserver;
import networking.IServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIController implements IObserver, Serializable {
    private IServer server;

    private List<Concert> concertModel;


    public RMIController(IServer server) {
        this.concertModel = FXCollections.observableArrayList(new ArrayList<Concert>());
        this.server = server;
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            System.out.println("Error exporting object " + e);
        }
    }

    public void setConcertModel(List<Concert> concertModel) {
        this.concertModel = concertModel;
    }

    public void concertUpdated(Concert concert) throws RemoteException {
        System.out.println("updatad");
        for (Concert c : this.concertModel)
            if (c.getId() == concert.getId()) {
                this.concertModel.remove(c);
                break;
            }
        this.concertModel.add(concert);
    }

}

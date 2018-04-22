package controller;

import javafx.collections.FXCollections;
import model.Artist;
import model.Concert;
import model.Location;
import model.Ticket;
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

    public ArrayList<Artist> getArtists() throws RemoteException{
        return this.server.getArtists();
    }

    public ArrayList<Concert> getConcerts() throws RemoteException{
        return this.server.getConcerts();
    }


    public ArrayList<Location> getLocation() throws RemoteException{
        return this.server.getLocation();
    }

    public ArrayList<Concert> getConcertsByArtist(Artist artist) throws RemoteException{
        return this.server.getConcertsByArtist(artist);
    }

    public ArrayList<Concert> getConcertsByDate(String date) throws RemoteException{
        return this.getConcertsByDate(date);
    }

    public ArrayList<Concert> getConcertsByArtistAndDate(Artist artist, String date) throws RemoteException{
        return this.getConcertsByArtistAndDate(artist,date);
    }

    public void saveTicket(Ticket ticket) throws RemoteException{
        this.server.saveTicket(ticket);
    }

    public void updateConcert(Concert concert) throws RemoteException{
        this.server.updateConcert(concert);
    }

    public void logout(IObserver client) throws RemoteException{
        this.server.logout(this);
    }

    public boolean login(String username,String password) throws RemoteException{
        return this.server.login(username,password,this);
    }

}

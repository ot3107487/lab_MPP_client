package networking;

import model.Artist;
import model.Concert;
import model.Location;
import model.Ticket;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IServer extends Remote {
    public boolean login(String userName, String password, IObserver client) throws RemoteException;

    public ArrayList<Artist> getArtists() throws RemoteException;

    public ArrayList<Concert> getConcerts() throws RemoteException;


    public ArrayList<Location> getLocation() throws RemoteException;

    public ArrayList<Concert> getConcertsByArtist(Artist artist) throws RemoteException;

    public ArrayList<Concert> getConcertsByDate(String date) throws RemoteException;

    public ArrayList<Concert> getConcertsByArtistAndDate(Artist artist, String date) throws RemoteException;

    public void saveTicket(Ticket ticket) throws RemoteException;

    public void updateConcert(Concert concert) throws RemoteException;

    public void logout(IObserver client) throws RemoteException;
}

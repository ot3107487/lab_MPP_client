package service;

import model.Artist;
import model.Concert;
import networking.Response;
import networking.Request;
import networking.RequestType;
import networking.WrapperArtistAndDate;
import utils.ConnectionProperties;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConcertService extends Service<Integer, Concert> {
    public ConcertService() {
    }

    @Override
    public void put(Concert concert) {
        Socket client = ConnectionProperties.getSocketConncetion();

        //opening streams  - mandatory to open first the output and flush, and then the input
        try {
            ConnectionProperties.connectionInUse=true;
            ObjectOutputStream out = ConnectionProperties.getOutputStream(client);
            ObjectInputStream in = ConnectionProperties.getInputStream(client);
            out.flush();
            Request newRequest = new Request(concert, RequestType.UPDATE_CONCERT);
            System.out.println("Sending object ..." + newRequest);

            out.writeObject(newRequest);
            out.flush();

            System.out.println("Waiting for response...");
            Object response = null;
            try {
                response = in.readObject();
            } catch (ClassNotFoundException e) {
                System.out.println("Error deserializing " + e);
            }
            Response ar = (Response) response;
            ConnectionProperties.connectionInUse=false;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Concert> getConcertsByArtist(Artist artist) {
        Socket client = ConnectionProperties.getSocketConncetion();

        //opening streams  - mandatory to open first the output and flush, and then the input
        try {
            ConnectionProperties.connectionInUse=true;
            ObjectOutputStream out = ConnectionProperties.getOutputStream(client);
            ObjectInputStream in = ConnectionProperties.getInputStream(client);
            out.flush();
            Request newRequest = new Request(artist, RequestType.GET_CONCERTS_BY_ARTIST);
            System.out.println("Sending object ..." + newRequest);

            out.writeObject(newRequest);
            out.flush();

            System.out.println("Waiting for response...");
            Object response = null;
            try {
                response = in.readObject();
            } catch (ClassNotFoundException e) {
                System.out.println("Error deserializing " + e);
            }
            Response ar = (Response) response;
            ConnectionProperties.connectionInUse=false;
            return (ArrayList<Concert>) ar.getData();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ConnectionProperties.connectionInUse=false;
        return new ArrayList<>();
    }

    public ArrayList<Concert> getConcertsByDate(String date) {
        Socket client = ConnectionProperties.getSocketConncetion();
        //opening streams  - mandatory to open first the output and flush, and then the input
        try {
            ConnectionProperties.connectionInUse=true;
            ObjectOutputStream out = ConnectionProperties.getOutputStream(client);
            ObjectInputStream in = ConnectionProperties.getInputStream(client);
            out.flush();
            Request newRequest = new Request(date, RequestType.GET_CONCERTS_BY_DATE);
            System.out.println("Sending object ..." + newRequest);

            out.writeObject(newRequest);
            out.flush();

            System.out.println("Waiting for response...");
            Object response = null;
            try {
                response = in.readObject();
            } catch (ClassNotFoundException e) {
                System.out.println("Error deserializing " + e);
            }
            Response ar = (Response) response;
            ConnectionProperties.connectionInUse=false;
            return (ArrayList<Concert>) ar.getData();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ConnectionProperties.connectionInUse=false;
        return new ArrayList<>();
    }

    public ArrayList<Concert> getConcertsByArtistAndDate(Artist artist, String date) {
        Socket client = ConnectionProperties.getSocketConncetion();

        //opening streams  - mandatory to open first the output and flush, and then the input
        try {
            ConnectionProperties.connectionInUse=true;
            ObjectOutputStream out = ConnectionProperties.getOutputStream(client);
            ObjectInputStream in = ConnectionProperties.getInputStream(client);
            out.flush();
            Request newRequest = new Request(new WrapperArtistAndDate(artist, date), RequestType.GET_CONCERTS_BY_ARTIST_AND_DATE);
            System.out.println("Sending object ..." + newRequest);

            out.writeObject(newRequest);
            out.flush();

            System.out.println("Waiting for response...");
            Object response = null;
            try {
                response = in.readObject();
            } catch (ClassNotFoundException e) {
                System.out.println("Error deserializing " + e);
            }
            Response ar = (Response) response;
            ConnectionProperties.connectionInUse=false;
            return (ArrayList<Concert>) ar.getData();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ConnectionProperties.connectionInUse=false;
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Concert> getAll() {
        Socket client = ConnectionProperties.getSocketConncetion();
        //opening streams  - mandatory to open first the output and flush, and then the input
        try {
            ConnectionProperties.connectionInUse=true;
            ObjectOutputStream out = ConnectionProperties.getOutputStream(client);
            ObjectInputStream in = ConnectionProperties.getInputStream(client);
            out.flush();
            Request newRequest = new Request(null, RequestType.GET_CONCERTS);
            System.out.println("Sending object ..." + newRequest);

            out.writeObject(newRequest);
            out.flush();

            System.out.println("Waiting for response...");
            Object response = null;
            try {
                response = in.readObject();
            } catch (ClassNotFoundException e) {
                System.out.println("Error deserializing " + e);
            }
            Response ar = (Response) response;
            ConnectionProperties.connectionInUse=false;
            return (ArrayList<Concert>) ar.getData();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ConnectionProperties.connectionInUse=false;
        return new ArrayList<>();
    }
}

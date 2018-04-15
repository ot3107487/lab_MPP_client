package service;

import model.Ticket;
import model.Ticket;
import networking.Request;
import networking.RequestType;
import networking.Response;
import utils.ConnectionProperties;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class TicketService extends Service<Integer, Ticket> {
    @Override
    public void save(Ticket ticket) {
        Socket client = ConnectionProperties.getSocketConncetion();

        //opening streams  - mandatory to open first the output and flush, and then the input
        try {
            ConnectionProperties.connectionInUse=true;
            ObjectOutputStream out = ConnectionProperties.getOutputStream(client);
            ObjectInputStream in = ConnectionProperties.getInputStream(client);
            out.flush();
            Request newRequest = new Request(ticket, RequestType.SAVE_TICKET);
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
            ConnectionProperties.connectionInUse=false;
            Response ar = (Response) response;


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

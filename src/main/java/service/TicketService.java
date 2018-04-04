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
        try (Socket client = new Socket(ConnectionProperties.SERVER_HOST, ConnectionProperties.SERVER_PORT)) {
            System.out.println("Connection obtained.  ");

            //opening streams  - mandatory to open first the output and flush, and then the input
            try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
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
                Response ar = (Response) response;

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

package service;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import networking.IObserver;
import networking.Identifiable;
import networking.Request;
import networking.RequestType;
import utils.ConnectionProperties;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginService {

    public LoginService() {

    }

    public boolean login(String userName, String password) {
        try(Socket client=new Socket(ConnectionProperties.SERVER_HOST,ConnectionProperties.SERVER_PORT)) {
            System.out.println("Connection obtained.  ");

            //opening streams  - mandatory to open first the output and flush, and then the input
            try(ObjectOutputStream out=new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream in=new ObjectInputStream(client.getInputStream())) {
                out.flush();
                String credentials=userName+" "+password;
                Identifiable identifiable=new Identifiable(credentials,client.getLocalPort());
                Request newRequest=new Request(identifiable, RequestType.LOGIN);
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
                return (Boolean)response;

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

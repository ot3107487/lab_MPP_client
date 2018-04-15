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
        Socket client = ConnectionProperties.getSocketConncetion();
        System.out.println(client);
        //opening streams  - mandatory to open first the output and flush, and then the input
        try {
            ConnectionProperties.connectionInUse=true;
            ObjectOutputStream out = ConnectionProperties.getOutputStream(client);
            ObjectInputStream in = ConnectionProperties.getInputStream(client);
            out.flush();
            String credentials = userName + " " + password;
            Identifiable identifiable = new Identifiable(credentials);
            Request newRequest = new Request(identifiable, RequestType.LOGIN);
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
            return (Boolean) response;


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ConnectionProperties.connectionInUse=false;
        return false;
    }
}

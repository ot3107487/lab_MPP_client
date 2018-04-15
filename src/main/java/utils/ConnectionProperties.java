package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionProperties {
    public static int SERVER_PORT = 55555;
    public static String SERVER_HOST = "localhost";
    private static Socket client = null;
    private static ObjectOutputStream out = null;
    private static ObjectOutputStream outUpdating = null;
    private static ObjectInputStream inUpdating = null;
    private static ObjectInputStream in = null;
    public static boolean connectionInUse=false;

    private static Socket updatesSocket=null;

    private ConnectionProperties() {
    }

    public static Socket getUpdatesSocket() {
        if (updatesSocket == null)
            try {
                updatesSocket = new Socket(SERVER_HOST, SERVER_PORT);
                updatesSocket.setReuseAddress(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        ;
        return updatesSocket;
    }

    public static Socket getSocketConncetion() {
        if (client == null)
            try {
                client = new Socket(SERVER_HOST, SERVER_PORT);
                client.setReuseAddress(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        ;
        return client;
    }

    public static ObjectOutputStream getOutputStream(Socket socket) {
        if (out == null)
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return out;
    }

    public static ObjectInputStream getInputStream(Socket socket) {
        if (in == null)
            try {
                in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return in;
    }

    public static ObjectOutputStream getOutUpdating(Socket socket) {
        if (outUpdating == null)
            try {
                outUpdating = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return outUpdating;
    }

    public static ObjectInputStream getInUpdating(Socket socket) {
        if (inUpdating == null)
            try {
                inUpdating = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return inUpdating;
    }
}

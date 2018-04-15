package service;

import model.Concert;
import networking.IObserver;
import networking.Request;
import networking.RequestType;
import networking.Response;
import utils.ConnectionProperties;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ProxyListener {
    private final Socket socket;
    private final IObserver client;
    private boolean alive = true;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    public ProxyListener(IObserver client) {

//        this.socket = ConnectionProperties.getSocketConncetion();
        this.socket = ConnectionProperties.getUpdatesSocket();
        this.client = client;
        System.out.println(this.socket);
        in = ConnectionProperties.getInUpdating(socket);
        out = ConnectionProperties.getOutUpdating(socket);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void listen() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request newRequest = new Request(null, RequestType.UPDATES);
                System.out.println("Sending object ..." + newRequest);
                try {
                    out.flush();
                    out.writeObject(newRequest);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (alive) {
                    Object res = null;
                    try {
                        //Thread.sleep(7000);
                        if (!ConnectionProperties.connectionInUse) {
                            res = in.readObject();
                            if (res instanceof Response) {
                                Response response = (Response) res;
                                Object data = response.getData();
                                if (data instanceof Concert)
                                    client.concertUpdated((Concert) data);
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    ConnectionProperties.connectionInUse = false;
                }
            }
        });
        thread.start();
    }
}

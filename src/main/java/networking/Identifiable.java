package networking;

import java.io.Serializable;

public class Identifiable implements Serializable {
    private String credentials;
    private int clientPort;

    public Identifiable(String credentials, int clientPort) {
        this.credentials = credentials;
        this.clientPort = clientPort;
    }

    public String getCredentials() {
        return credentials;
    }

    public int getClientPort() {
        return clientPort;
    }
}

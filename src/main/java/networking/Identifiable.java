package networking;

import java.io.Serializable;
import java.net.Socket;

public class Identifiable implements Serializable {
    private String credentials;

    public Identifiable(String credentials) {
        this.credentials = credentials;
    }

    public String getCredentials() {
        return credentials;
    }
}

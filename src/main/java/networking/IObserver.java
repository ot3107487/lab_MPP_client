package networking;


import model.Concert;

import java.io.Serializable;

public interface IObserver {
     void concertUpdated(Concert concert);
}

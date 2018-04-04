package networking;

import model.Artist;

import java.io.Serializable;

public class WrapperArtistAndDate implements Serializable {
    private Artist artist;
    private String date;

    public WrapperArtistAndDate(Artist artist, String date) {
        this.artist = artist;
        this.date = date;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getDate() {
        return date;
    }
}

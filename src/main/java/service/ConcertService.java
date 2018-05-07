package service;

import model.Artist;
import model.Concert;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ConcertService extends Service<Concert> {
    private String concertsAPI;

    public ConcertService(String urlApi) {
        super(urlApi);
        this.concertsAPI=urlApi+"/concerts";
    }

    private ArrayList<Concert> JSONArrayToConcerts(JSONArray myResponse,Artist artist){
        ArrayList<Concert> list=new ArrayList<>();
        for(int i=0;i<myResponse.length();i++){
            JSONObject concertJSON=myResponse.getJSONObject(i);
            String date =concertJSON.getString("date");
            int id=concertJSON.getInt("id");
            int numberOfTickets=concertJSON.getInt("numberOfTickets");
            int soldTickets=concertJSON.getInt("soldTickets");
            Concert concert=new Concert(id,artist.getId(),date,0,numberOfTickets,soldTickets);
            list.add(concert);
        }
        return list;
    }

    public ArrayList<Concert> getConcertsByArtist(Artist artist){
        try {
            String api=this.concertsAPI+"/?idArtist="+artist.getId();
            URL url=new URL(api);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET"); // default
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print in String
            System.out.println(response.toString());
            //Read JSON response and print
            JSONArray myResponse = new JSONArray(response.toString());
            return JSONArrayToConcerts(myResponse,artist);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Concert elem) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void delete(Concert elem) {

    }

    @Override
    public void put(Concert elem) {

    }

    @Override
    public ArrayList<Concert> getAll() {
        return null;
    }

    @Override
    public Concert findById(int id) {
        return null;
    }
}

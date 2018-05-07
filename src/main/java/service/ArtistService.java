package service;

import model.Artist;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ArtistService extends Service<Artist> {
    private String artistsAPI;

    public ArtistService(String urlApi) {
        super(urlApi);
        this.artistsAPI = urlApi + "/artists";

    }

    private JSONObject artistToJSON(Artist elem){
        JSONObject artist = new JSONObject();
        artist.put("id", elem.getId());
        artist.put("firstName", elem.getFirstName());
        artist.put("lastName", elem.getLastName());
        return artist;
    }

    private ArrayList<Artist> JSONArrayToArtists(JSONArray myResponse){
        ArrayList<Artist> list = new ArrayList<>();
        for (int i = 0; i < myResponse.length(); i++) {
            JSONObject artstJSON = myResponse.getJSONObject(i);
            String firstName = artstJSON.getString("firstName");
            String lastName = artstJSON.getString("lastName");
            int id = artstJSON.getInt("id");
            Artist artist = new Artist(id, lastName, firstName);
            list.add(artist);
        }
        return list;
    }

    @Override
    public void save(Artist elem) {
        try {
            URL url = new URL(artistsAPI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); // default
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(artistToJSON(elem).toString());
            wr.flush();


            //receive
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void delete(Artist elem) {
        try {
            URL url=new URL(artistsAPI);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE"); // default
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(artistToJSON(elem).toString());
            wr.flush();


            //receive
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'DELETE' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(Artist elem) {
        try {
            URL url = new URL(artistsAPI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT"); // default
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(artistToJSON(elem).toString());
            wr.flush();


            //receive
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'PUT' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<Artist> getAll() {
        try {
            URL url = new URL(artistsAPI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
            return JSONArrayToArtists(myResponse);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Artist findById(int id) {
        return null;
    }
}

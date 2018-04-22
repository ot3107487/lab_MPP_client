package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Artist;
import model.Concert;
import model.Location;
import model.Ticket;
import networking.IServer;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {
    @FXML
    TableView tableArtists;
    @FXML
    TableColumn columnArtistFirstName;
    @FXML
    TableColumn columnArtistLastName;

    @FXML
    TableView tableConcerts;
    @FXML
    TableColumn columnConcertDate;
    @FXML
    TableColumn columnConcertLocation;
    @FXML
    TableColumn columnConcertNumberOfTickets;
    @FXML
    TableColumn columnConcertSoldTickets;

    @FXML
    TableView tableLocations;
    @FXML
    TableColumn columnLocationId;
    @FXML
    TableColumn columnLocationName;

    @FXML
    TextArea filterDate;
    @FXML
    CheckBox checkFilter;

    @FXML
    TextArea textNume;
    @FXML
    TextArea textPrenume;
    @FXML
    TextArea textNrBilete;
    @FXML
    Label labelNrBilete;

    Stage loginStage;
    Stage thisStage;

    RMIController controller;
    IServer server;

    public void setServer(IServer server) {
        this.server = server;
        try {

            modelArtists = FXCollections.observableArrayList(server.getArtists());
            tableArtists.setItems(modelArtists);

            modelConcerts = FXCollections.observableArrayList(server.getConcerts());
            tableConcerts.setItems(modelConcerts);


            modelLocations = FXCollections.observableArrayList(server.getLocation());
            tableLocations.setItems(modelLocations);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }


    private ObservableList<Artist> modelArtists;
    private ObservableList<Concert> modelConcerts;
    private ObservableList<Location> modelLocations;


    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }


    public void getConcertsOfArtist(MouseEvent event) {
        Artist artist = (Artist) tableArtists.getSelectionModel().getSelectedItem();
        //if (checkFilter.isSelected()) {
        try {
            modelConcerts = FXCollections.observableArrayList(server.getConcertsByArtistAndDate(artist, filterDate.getText()));

            //} else
            modelConcerts = FXCollections.observableArrayList(server.getConcertsByArtist(artist));
            tableConcerts.setItems(modelConcerts);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getArtistByDate(MouseEvent event) { // TO DO
        String date = filterDate.getText();
        ArrayList<Concert> concertInThatDate = null;
//        try {
//            concertInThatDate = server.getConcertsByDate(date);
//
//            Set<Artist> artists = new HashSet<>();
//
//            for (Concert concert : concertInThatDate)
//                artists.add(artistService.findById(concert.getIdArtist()));
//            ArrayList<Artist> finalArtists = new ArrayList<>();
//            finalArtists.addAll(artists);
//            modelArtists = FXCollections.observableArrayList(finalArtists);
//            tableArtists.setItems(modelArtists);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }


    }

    public void printTicket(MouseEvent event) {
        Concert concert = (Concert) tableConcerts.getSelectionModel().getSelectedItem();
        try {
            if (concert != null) {
                labelNrBilete.setVisible(false);
                String buyer = textNume.getText() + ' ' + textPrenume.getText();
                int nrLocuri = Integer.parseInt(textNrBilete.getText());
                int locuriInTotal = concert.getNumberOfTickets();
                int locuriCumparate = concert.getSoldTickets();
                if (locuriInTotal - locuriCumparate < nrLocuri)
                    labelNrBilete.setVisible(true);
                else {
                    Ticket ticket = new Ticket(0, concert.getId(), nrLocuri, buyer);
                    server.saveTicket(ticket);
                    concert.setSoldTickets(locuriCumparate + nrLocuri);
                    server.updateConcert(concert);
                    //concertUpdated(concert);
                    System.out.println("Ticket cumparat cu succes");
                    System.out.println(ticket);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void logout(MouseEvent event) {
        thisStage.close();
        try {
            server.logout(controller);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        loginStage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //artisti
        columnArtistFirstName.setCellValueFactory(new PropertyValueFactory<Artist, String>("firstName"));
        columnArtistLastName.setCellValueFactory(new PropertyValueFactory<Artist, String>("lastName"));
        //concerte
        columnConcertDate.setCellValueFactory(new PropertyValueFactory<Concert, String>("date"));
        columnConcertLocation.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("idLocation"));
        columnConcertNumberOfTickets.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("numberOfTickets"));
        columnConcertSoldTickets.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("soldTickets"));
        //legenda locatii
        columnLocationId.setCellValueFactory(new PropertyValueFactory<Location, Integer>("id"));
        columnLocationName.setCellValueFactory(new PropertyValueFactory<Location, String>("name"));


        tableConcerts.setRowFactory(tv -> new TableRow<Concert>() {
            @Override
            public void updateItem(Concert item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getNumberOfTickets() == item.getSoldTickets()) {
                    setStyle("-fx-background-color: tomato;");
                } else {
                    setStyle("");
                }
            }
        });
        labelNrBilete.setVisible(false);
    }


    public void setController(RMIController controller) {
        this.controller = controller;
        this.controller.setConcertModel(this.modelConcerts);
    }
}

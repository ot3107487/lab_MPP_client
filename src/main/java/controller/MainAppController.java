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
import networking.IObserver;
import service.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainAppController implements IObserver,Initializable {
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

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    ArtistService artistService;
    ConcertService concertService;
    LocationService locationService;
    TicketService ticketService;

    private ObservableList<Artist> modelArtists;
    private ObservableList<Concert> modelConcerts;
    private ObservableList<Location> modelLocations;

    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
        ArrayList<Artist> artists = artistService.getAll();
        modelArtists = FXCollections.observableArrayList(artists);
        tableArtists.setItems(modelArtists);
    }

    public void setConcertService(ConcertService concertService) {
        this.concertService = concertService;
        modelConcerts = FXCollections.observableArrayList(concertService.getAll());
        tableConcerts.setItems(modelConcerts);
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
        ArrayList<Location> locations = locationService.getAll();
        modelLocations = FXCollections.observableArrayList(locations);
        tableLocations.setItems(modelLocations);
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

//    @FXML
//    public void initialize() {
//        //artisti
//        columnArtistFirstName.setCellValueFactory(new PropertyValueFactory<Artist, String>("firstName"));
//        columnArtistLastName.setCellValueFactory(new PropertyValueFactory<Artist, String>("lastName"));
//        //concerte
//        columnConcertDate.setCellValueFactory(new PropertyValueFactory<Concert, String>("date"));
//        columnConcertLocation.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("idLocation"));
//        columnConcertNumberOfTickets.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("numberOfTickets"));
//        columnConcertSoldTickets.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("soldTickets"));
//        //legenda locatii
//        columnLocationId.setCellValueFactory(new PropertyValueFactory<Location, Integer>("id"));
//        columnLocationName.setCellValueFactory(new PropertyValueFactory<Location, String>("name"));
//
//
//        tableConcerts.setRowFactory(tv -> new TableRow<Concert>() {
//            @Override
//            public void updateItem(Concert item, boolean empty) {
//                super.updateItem(item, empty);
//                if (item == null) {
//                    setStyle("");
//                } else if (item.getNumberOfTickets() == item.getSoldTickets()) {
//                    setStyle("-fx-background-color: tomato;");
//                } else {
//                    setStyle("");
//                }
//            }
//        });
//        labelNrBilete.setVisible(false);
//    }

    public void getConcertsOfArtist(MouseEvent event) {
        Artist artist = (Artist) tableArtists.getSelectionModel().getSelectedItem();
        //if (checkFilter.isSelected()) {
            modelConcerts = FXCollections.observableArrayList(concertService.getConcertsByArtistAndDate(artist, filterDate.getText()));
        //} else
            modelConcerts = FXCollections.observableArrayList(concertService.getConcertsByArtist(artist));
        tableConcerts.setItems(modelConcerts);
    }

    public void getArtistByDate(MouseEvent event) {
        String date = filterDate.getText();
        ArrayList<Concert> concertInThatDate = concertService.getConcertsByDate(date);
        Set<Artist> artists = new HashSet<>();
        for (Concert concert : concertInThatDate)
            artists.add(artistService.findById(concert.getIdArtist()));
        ArrayList<Artist> finalArtists = new ArrayList<>();
        finalArtists.addAll(artists);
        modelArtists = FXCollections.observableArrayList(finalArtists);
        tableArtists.setItems(modelArtists);


    }

    public void printTicket(MouseEvent event) {
        Concert concert = (Concert) tableConcerts.getSelectionModel().getSelectedItem();
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
                ticketService.save(ticket);
                concert.setSoldTickets(locuriCumparate + nrLocuri);
                concertService.put(concert);
                concertUpdated(concert);
                System.out.println("Ticket cumparat cu succes");
                System.out.println(ticket);
            }
        }
    }


    public void logout(MouseEvent event) {
        thisStage.close();
        loginStage.show();
    }

    @Override
    public void concertUpdated(Concert concert) {
        for (Concert c : modelConcerts)
            if (c.getId() == concert.getId()) {
                modelConcerts.remove(c);
                break;
            }
        modelConcerts.add(concert);

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
}

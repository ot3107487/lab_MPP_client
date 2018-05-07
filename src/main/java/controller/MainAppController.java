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
import service.ConcertService;
import service.Service;

import java.net.URL;
import java.rmi.RemoteException;
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
    TableColumn columnConcertNumberOfTickets;
    @FXML
    TableColumn columnConcertSoldTickets;


    @FXML
    TextArea textNume;
    @FXML
    TextArea textPrenume;

    Stage loginStage;
    Stage thisStage;

    private Service<Artist> artistService;
    private ConcertService concertService;

    public void setConcertService(ConcertService concertService) {
        this.concertService = concertService;
    }

    public void setArtistService(Service<Artist> service) {
        this.artistService = service;
        modelArtists = FXCollections.observableArrayList(artistService.getAll());
        tableArtists.setItems(modelArtists);
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }


    private ObservableList<Artist> modelArtists;
    private ObservableList<Concert> modelConcerts;;

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }


    public void getConcertsOfArtist(MouseEvent event) {
        Artist artist = (Artist) tableArtists.getSelectionModel().getSelectedItem();
        modelConcerts = FXCollections.observableArrayList(concertService.getConcertsByArtist(artist));
        tableConcerts.setItems(modelConcerts);
        textPrenume.setText(artist.getFirstName());
        textNume.setText(artist.getLastName());
    }


    public void logout(MouseEvent event) {
        thisStage.close();
        loginStage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //artisti
        columnArtistFirstName.setCellValueFactory(new PropertyValueFactory<Artist, String>("firstName"));
        columnArtistLastName.setCellValueFactory(new PropertyValueFactory<Artist, String>("lastName"));
        //concerte
        columnConcertDate.setCellValueFactory(new PropertyValueFactory<Concert, String>("date"));
        columnConcertNumberOfTickets.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("numberOfTickets"));
        columnConcertSoldTickets.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("soldTickets"));
        //legenda locatii


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
    }

    public void addArtist(MouseEvent event) {
        String firstname= this.textPrenume.getText();
        String lastname=this.textNume.getText();
        Artist artist=new Artist(0,lastname,firstname);
        modelArtists.add(artist);
        artistService.save(artist);
    }

    public void deleteArtist(MouseEvent event) {
        Artist artist = (Artist) tableArtists.getSelectionModel().getSelectedItem();
        modelArtists.remove(artist);
        artistService.delete(artist);
    }

    public void editArtist(MouseEvent event) {
        Artist artist = (Artist) tableArtists.getSelectionModel().getSelectedItem();
        Artist newArtist=new Artist(artist.getId(),textPrenume.getText(),textNume.getText());
        modelArtists.remove(artist);
        modelArtists.add(newArtist);
        artistService.put(newArtist);
    }

    public void refresh(MouseEvent event) {
        modelArtists = FXCollections.observableArrayList(artistService.getAll());
        tableArtists.setItems(modelArtists);
    }
}

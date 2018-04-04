package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Artist;
import model.Location;
import model.Ticket;
import networking.IObserver;
import service.*;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField txtUser;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label labelCredentials;

    private Stage loginStage;

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    private LoginService service;

    private MainAppController ctr;
    private AnchorPane pane;

    public void setService(LoginService service) {
        this.service = service;
        this.labelCredentials.setVisible(false);
    }

    public void login(MouseEvent event) {
        String userName = txtUser.getText();
        String password = txtPassword.getText();
        if (service.login(userName, password)) {
            goToMainAppView();
            labelCredentials.setVisible(false);
        } else
            labelCredentials.setVisible(true);

    }

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String resource = "/views/mainAppView.fxml";
        loader.setLocation(getClass().getResource(resource));
        this.pane = (AnchorPane) loader.load();
        this.ctr = (MainAppController) loader.getController();
    }

    private void goToMainAppView() {
        try {
            ArtistService artistService = new ArtistService();
            ConcertService concertService = new ConcertService();
            LocationService locationService = new LocationService();
            TicketService ticketService = new TicketService();


            this.ctr.setArtistService(artistService);
            this.ctr.setConcertService(concertService);
            this.ctr.setLocationService(locationService);
            this.ctr.setLoginStage(this.loginStage);
            this.ctr.setTicketService(ticketService);
            Stage stage = new Stage();
            this.ctr.setThisStage(stage);
            stage.setScene(new Scene(this.pane));
            stage.setTitle("Bilete");
            loginStage.close();
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

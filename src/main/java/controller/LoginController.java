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
import service.ArtistService;
import service.ConcertService;
import service.LoginService;
import service.Service;

import java.io.IOException;
import java.rmi.RemoteException;

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

    private LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    private AnchorPane pane;

    public void login(MouseEvent event) throws RemoteException {
        String userName = txtUser.getText();
        String password = txtPassword.getText();
        if (loginService.login(userName, password)) {
            goToMainAppView();
            labelCredentials.setVisible(false);
        } else
            labelCredentials.setVisible(true);

    }

    @FXML
    public void initialize() throws IOException {
        labelCredentials.setVisible(false);
    }

    private void goToMainAppView() {
        try {

            FXMLLoader loader = new FXMLLoader();
            String resource = "/views/mainAppView.fxml";
            loader.setLocation(getClass().getResource(resource));
            this.pane = (AnchorPane) loader.load();
            MainAppController ctr = (MainAppController) loader.getController();
            Service<Artist> artistService=new ArtistService(loginService.getUrlAPI());
            ConcertService concertService=new ConcertService(loginService.getUrlAPI());
            ctr.setConcertService(concertService);
            ctr.setArtistService(artistService);
            ctr.setLoginStage(this.loginStage);
            Stage stage = new Stage();
            ctr.setThisStage(stage);
            stage.setScene(new Scene(this.pane));
            stage.setTitle("Bilete");
            loginStage.close();
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

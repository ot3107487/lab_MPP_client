import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.LoginService;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties clientProps = new Properties();
        try {
            clientProps.load(Main.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String api = clientProps.getProperty("api");
        try {
            LoginService loginService=new LoginService(api);
            FXMLLoader loader = new FXMLLoader();
            String resource = "/views/loginView.fxml";
            loader.setLocation(getClass().getResource(resource));
            AnchorPane pane = (AnchorPane) loader.load();
            LoginController controller = loader.getController();
            controller.setLoginService(loginService);
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(pane));
            controller.setLoginStage(stage);
            stage.show();

        } catch (Exception e) {
            System.err.println("Chat Initialization  exception:" + e);
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

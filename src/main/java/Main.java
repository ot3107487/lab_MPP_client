import controller.LoginController;
import controller.MainAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import networking.IServer;
import service.LoginService;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class Main extends Application {
    private static String defaultServer = "localhost";

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
        //System.setProperty("java.security.policy", "file:client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        String name = clientProps.getProperty("rmi.serverID", "Server");
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        try {

            Registry registry = LocateRegistry.getRegistry(serverIP);
            IServer server = (IServer) registry.lookup(name);
            System.out.println("Obtained a reference to remote chat server");


            LoginService loginService = new LoginService();
            FXMLLoader loader = new FXMLLoader();
            String resource = "/views/loginView.fxml";
            loader.setLocation(getClass().getResource(resource));
            AnchorPane pane = (AnchorPane) loader.load();
            LoginController controller = loader.getController();
            controller.setService(loginService);
            controller.setServer(server);
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

import controller.LoginController;
import controller.RMIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import networking.IServer;

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
        System.setProperty("java.security.policy", "file:client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        String name = clientProps.getProperty("rmi.serverID", "Server");
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        try {

            Registry registry = LocateRegistry.getRegistry(serverIP);
            IServer server = (IServer) registry.lookup(name);
            System.out.println("Obtained a reference to remote chat server");
            RMIController rmiController=new RMIController(server);

            FXMLLoader loader = new FXMLLoader();
            String resource = "/views/loginView.fxml";
            loader.setLocation(getClass().getResource(resource));
            AnchorPane pane = (AnchorPane) loader.load();
            LoginController controller = loader.getController();
            controller.setRmiController(rmiController);
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

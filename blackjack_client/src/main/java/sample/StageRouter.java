package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;

public class StageRouter {

    public final static String SIGN_IN = "SignInFrame.fxml";
    public final static String REGISTER = "RegistrationFrame.fxml";
    public final static String LOBBY = "LobbyFrame.fxml";
    public final static String PLAY = "PlayFrame.fxml";
    public final static String SETTINGS = "SettingsFrame.fxml";

    private final Stage stage;

    public StageRouter(Stage stage) {
        this.stage = stage;
    }

    public void goTo(String resource) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(resource));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
            stage.setX(screenDimension.getWidth() / 2 - scene.getWidth() / 2);
            stage.setY(screenDimension.getHeight() / 2 - scene.getHeight() / 2);
            stage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}

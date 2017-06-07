package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.Button;
import java.io.IOException;

/**
 * Created by Ol'ko on 22.05.2017.
 */

public class PlayController {
    Stage stage;
    @FXML
    public javafx.scene.control.Button stand;
    @FXML
    public javafx.scene.control.Button hit;
    @FXML
    public javafx.scene.control.Button rate;
    @FXML
    public void exitButton(){
        System.exit(0);
    }
    public void toMenu() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LobbyFrame.fxml"));
        Parent root = loader.load();
        LobbyFrameController controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void hitButton(){

    }
    public void standButton(){

    }
    public void rateButton(){
        hit.setVisible(true);
        stand.setVisible(true);
        rate.setVisible(false);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.awt.Button;
import java.io.IOException;

/**
 * Created by Ol'ko on 22.05.2017.
 */

public class PlayController {
    Stage stage;
    int bet = 0;
    @FXML
    public TextArea gameLog;
    @FXML
    public javafx.scene.control.Button stand;
    @FXML
    public javafx.scene.control.Button hit;
    @FXML
    public javafx.scene.control.Button deal;
    @FXML
    public ImageView ten;
    @FXML
    public ImageView twenty;
    @FXML
    public ImageView fifty;
    @FXML
    public ImageView oneHundert;
    @FXML
    public ImageView twoHundert;
    @FXML
    public ImageView fiveHundert;
    @FXML
    public Label betLabel;

    @FXML
    public void initialize(){
        deal.setDisable(true);
        stand.setVisible(false);
        hit.setVisible(false);

    }

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
    public void dealButton(){
        hit.setVisible(true);
        stand.setVisible(true);
        deal.setVisible(false);
        ten.setVisible(false);
        twenty.setVisible(false);
        fifty.setVisible(false);
        oneHundert.setVisible(false);
        twoHundert.setVisible(false);
        fiveHundert.setVisible(false);
        betLabel.setText("$ " + String.valueOf(bet));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void tenButtonPress(){
        deal.setDisable(false);
        bet += 10;
    }
    public void twentyButtonPress(){
        deal.setDisable(false);
        bet += 20;
    }
    public void fiftyButtonPress(){
        deal.setDisable(false);
        bet += 50;
}
    public void oneHundertButtonPress(){
        deal.setDisable(false);
        bet += 100;
    }
    public void twoHundertButtonPress(){
        deal.setDisable(false);
        bet += 200;
    }
    public void fiveHundertButtonPress(){
        deal.setDisable(false);
        bet += 500;
    }
}



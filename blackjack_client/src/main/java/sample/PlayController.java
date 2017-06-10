package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Ol'ko on 22.05.2017.
 */

public class PlayController {
    Stage stage;
    String playerName;
    int bet = 0;
    @FXML
    public TextArea gameLog;
    @FXML
    public javafx.scene.control.Button stand;
    @FXML
    public javafx.scene.control.Button hit;
    @FXML
    public javafx.scene.control.Button btnStandUp;
    @FXML
    public Label betLabel;
    @FXML
    public javafx.scene.control.Button btnSitDown;
    @FXML
    public javafx.scene.control.Button btnFirstbet;
    @FXML
    public javafx.scene.control.Button btnSecondbet;
    @FXML
    public javafx.scene.control.Button btnThirdbet;

    @FXML
    public void initialize(){
        stand.setVisible(false);
        hit.setVisible(false);
        btnFirstbet.setVisible(false);
        btnSecondbet.setVisible(false);
        btnThirdbet.setVisible(false);
        String name = setPlayerName(playerName);
        System.out.println(name);

    }

    public String setPlayerName(String playerName){
        this.playerName = playerName;
        return playerName;
    }

    @FXML
    public void exitButton(){
        btnSitDown.setVisible(true);
        btnStandUp.setVisible(false);
        gameLog.appendText("Player stand up!\n" + "_______________" + "\n");
        btnFirstbet.setVisible(false);
        btnSecondbet.setVisible(false);
        btnThirdbet.setVisible(false);
        hit.setVisible(false);
        stand.setVisible(false);
        bet = 0;
        betLabel.setText("$ " + String.valueOf(bet));
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
        gameLog.appendText("card add\n");
        btnFirstbet.setVisible(true);
        btnSecondbet.setVisible(true);
        btnThirdbet.setVisible(true);
        hit.setVisible(false);
        stand.setVisible(false);
    }
    public void standButton(){
        gameLog.appendText("open card\n");
        btnFirstbet.setVisible(true);
        btnSecondbet.setVisible(true);
        btnThirdbet.setVisible(true);
        hit.setVisible(false);
        stand.setVisible(false);
    }
    public void dealButton(){
        gameLog.appendText("Player sit down!\n");
        btnSitDown.setVisible(false);
        btnStandUp.setVisible(true);
        btnFirstbet.setVisible(true);
        btnSecondbet.setVisible(true);
        btnThirdbet.setVisible(true);
        betLabel.setText("$ " + String.valueOf(bet));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void actionBtnFirstbet(){
        hit.setVisible(true);
        stand.setVisible(true);
        btnFirstbet.setVisible(false);
        btnSecondbet.setVisible(false);
        btnThirdbet.setVisible(false);
        bet += 10;
        //logger
        gameLog.appendText("Bet \t" + String.valueOf(bet) + "\n");
        betLabel.setText("$ " + String.valueOf(bet));

    }
    @FXML
    public void actionBtnSecondbet(){
        hit.setVisible(true);
        stand.setVisible(true);
        btnFirstbet.setVisible(false);
        btnSecondbet.setVisible(false);
        btnThirdbet.setVisible(false);
        bet += 20;
        //logger
        gameLog.appendText("Bet \t" + String.valueOf(bet) + "\n");
        betLabel.setText("$ " + String.valueOf(bet));
    }
    @FXML
    public void actionBtnThirdbet(){
        hit.setVisible(true);
        stand.setVisible(true);
        btnFirstbet.setVisible(false);
        btnSecondbet.setVisible(false);
        btnThirdbet.setVisible(false);
        bet += 50;
        //logger
        gameLog.appendText("Bet \t" + String.valueOf(bet) + "\n");
        betLabel.setText("$ " + String.valueOf(bet));

    }
}



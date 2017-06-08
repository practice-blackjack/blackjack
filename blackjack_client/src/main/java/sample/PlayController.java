package sample;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import nulp.pist21.blackjack.client.endpoint.LobbyEndpoint;
import nulp.pist21.blackjack.client.endpoint.PlayGameEndpoint;
import nulp.pist21.blackjack.client.endpoint.WatchGameEndpoint;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Created by Ol'ko on 22.05.2017.
 */

public class PlayController {

    private final ProgramData programData = ProgramData.get();

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

    private WatchGameEndpoint watchGameEndpoint;
    private PlayGameEndpoint playGameEndpoint;

    private TableInfo currentTable;

    @FXML
    public void initialize(){
        deal.setDisable(false);
        stand.setVisible(false);
        hit.setVisible(false);
        ten.setVisible(false);
        twenty.setVisible(false);
        fifty.setVisible(false);
        oneHundert.setVisible(false);
        twoHundert.setVisible(false);
        fiveHundert.setVisible(false);
        gameLog.setText("Game started!\n");

        watchGameEndpoint = programData.getWatchGameEndpoint();
        watchGameEndpoint.onUpdateMessageListener((TableFullInfoMessage tableFullInfoMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tableFullInfoMessage));
        });
        watchGameEndpoint.onUserActionMessageListener((UserActionMessage userActionMessage) -> {
            System.out.println("server > " + JSON.toJSONString(userActionMessage));
        });
        watchGameEndpoint.onResultListener((ResultMessage resultMessage) -> {
            System.out.println("server > " + JSON.toJSONString(resultMessage));
        });
        playGameEndpoint = programData.getPlayGameEndpoint();
        playGameEndpoint.onWaitActionMessageListener((WaitMessage waitMessage) -> {
            System.out.println("server > " + JSON.toJSONString(waitMessage));
            TableInfo tableInfo = waitMessage.getTableInfo();
            int place = waitMessage.getPlace();
            Scanner scn = new Scanner(System.in);
            switch (waitMessage.getWaitType()) {
                case "bet":
                    deal.setVisible(true);
                    hit.setVisible(false);
                    stand.setVisible(false);
                    ten.setVisible(true);
                    twenty.setVisible(true);
                    fifty.setVisible(true);
                    oneHundert.setVisible(false);
                    twoHundert.setVisible(false);
                    fiveHundert.setVisible(false);
                    int bet = scn.nextInt();
                    playGameEndpoint.sendActionMessage(tableInfo, place, bet);
                    break;
                case "hit_or_stand":
                    deal.setVisible(false);
                    hit.setVisible(true);
                    stand.setVisible(true);
                    ten.setVisible(false);
                    twenty.setVisible(false);
                    fifty.setVisible(false);
                    oneHundert.setVisible(false);
                    twoHundert.setVisible(false);
                    fiveHundert.setVisible(false);
                    String hitOrStand = scn.nextLine();
                    playGameEndpoint.sendActionMessage(tableInfo, place, hitOrStand);
                    break;
            }
        });

        watchGameEndpoint.onEntryListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
            int place = 0;
            playGameEndpoint.sendSitMessage(currentTable, place);
        });
        watchGameEndpoint.onExitListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
            watchGameEndpoint.close();
            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("LobbyFrame.fxml"));
                    Parent root = loader.load();
                    LobbyFrameController controller = loader.getController();
                    controller.setStage(stage);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }catch (IOException e) {

                }
            });
        });
        playGameEndpoint.onSitListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
            //todo:
        });
        playGameEndpoint.onStandListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
            playGameEndpoint.close();
            watchGameEndpoint.sendExitMessage(currentTable);
        });
        currentTable = programData.getCurrentTable();
        watchGameEndpoint.sendEntryMessage(currentTable);
    }

    @FXML
    public void exitButton(){
        //System.exit(0);
    }
    public void toMenu() {
        playGameEndpoint.sendStandMessage(currentTable);
    }
    public void hitButton(){
        playGameEndpoint.sendActionMessage(currentTable, 0, "hit");
        gameLog.appendText("card add\n");
    }
    public void standButton(){
        playGameEndpoint.sendActionMessage(currentTable, 0, "stand");
        gameLog.appendText("open card\n");
    }
    public void dealButton(){
        playGameEndpoint.sendActionMessage(currentTable, 0, bet);

        betLabel.setText("$ " + String.valueOf(bet));
        //logger
        gameLog.appendText("Bet \t" + String.valueOf(bet) + "\n");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void tenButtonPress(){
        deal.setDisable(false);
        bet = 10;
    }
    public void twentyButtonPress(){
        deal.setDisable(false);
        bet = 20;
    }
    public void fiftyButtonPress(){
        deal.setDisable(false);
        bet = 50;
}
    public void oneHundertButtonPress(){
        deal.setDisable(false);
        bet = 100;
    }
    public void twoHundertButtonPress(){
        deal.setDisable(false);
        bet = 200;
    }
    public void fiveHundertButtonPress(){
        deal.setDisable(false);
        bet = 500;
    }
}



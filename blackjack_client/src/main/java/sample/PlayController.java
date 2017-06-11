package sample;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

import static nulp.pist21.blackjack.message.MessageConstant.*;

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

    private WatchGameEndpoint watchGameEndpoint;
    private PlayGameEndpoint playGameEndpoint;

    private TableInfo currentTable;
    private int place = 0;

    @FXML
    public void initialize(){
        stand.setVisible(false);
        hit.setVisible(false);
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
                case ACTION_WAIT_BET:
                    hit.setVisible(false);
                    stand.setVisible(false);
                    int bet = scn.nextInt();
                    playGameEndpoint.sendActionMessage(tableInfo, place, bet);
                    break;
                case ACTION_WAIT_HIT_OR_STAND:
                    hit.setVisible(true);
                    stand.setVisible(true);
                    String hitOrStand = scn.nextLine();
                    playGameEndpoint.sendActionMessage(tableInfo, place, hitOrStand);
                    break;
            }
        });
        watchGameEndpoint.onEntryListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
            playGameEndpoint.sendSitMessage(currentTable, place);
        });
        watchGameEndpoint.onExitListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
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
        playGameEndpoint.onSitListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
            //todo:
        });
        playGameEndpoint.onStandListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
            playGameEndpoint.close();
            watchGameEndpoint.sendExitMessage(currentTable);
        });
        currentTable = programData.getCurrentTable();
        watchGameEndpoint.sendEntryMessage(currentTable);
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
    public void toMenu() {
        playGameEndpoint.sendStandMessage(currentTable, place);
    }
    public void hitButton(){
        playGameEndpoint.sendActionMessage(currentTable, 0, ACTION_HIT);
        gameLog.appendText("card add\n");
        btnFirstbet.setVisible(true);
        btnSecondbet.setVisible(true);
        btnThirdbet.setVisible(true);
        hit.setVisible(false);
        stand.setVisible(false);
    }
    public void standButton(){
        playGameEndpoint.sendActionMessage(currentTable, 0, ACTION_STAND);
        gameLog.appendText("open card\n");
        btnFirstbet.setVisible(true);
        btnSecondbet.setVisible(true);
        btnThirdbet.setVisible(true);
        hit.setVisible(false);
        stand.setVisible(false);
    }
    public void dealButton(){
        playGameEndpoint.sendActionMessage(currentTable, 0, bet);
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



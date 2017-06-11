package sample;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nulp.pist21.blackjack.client.endpoint.PlayGameEndpoint;
import nulp.pist21.blackjack.client.endpoint.WatchGameEndpoint;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.Player;
import nulp.pist21.blackjack.model.TableFullInfo;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.deck.Card;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

import static nulp.pist21.blackjack.message.MessageConstant.*;

public class PlayController {

    private final static int STATE_NULL = -1;
    private final static int STATE_ENTRY = 0;
    private final static int STATE_WAIT = 1;
    private final static int STATE_BET = 2;
    private final static int STATE_HIT_OR_STAND = 3;

    private final ProgramData programData = ProgramData.get();
    private WatchGameEndpoint watchGameEndpoint;
    private PlayGameEndpoint playGameEndpoint;

    @FXML public TextField placeField;
    @FXML public Button sitDownButton;
    @FXML public Button standUpButton;
    @FXML public Button actionStandButton;
    @FXML public Button actionHitButton;
    @FXML public Button betOneButton;
    @FXML public Button betTwoButton;
    @FXML public Button betThreeButton;
    @FXML public Label betLabel;
    @FXML public TextArea chatTextArea;

    private TableInfo currentTable;
    private int currentPlace;

    @FXML
    public void initialize(){
        watchGameEndpoint = programData.getWatchGameEndpoint();
        watchGameEndpoint.onEntryListener((BooleanMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            if (message.isOk()) {
                toState(STATE_ENTRY);
                initPlayGameEndpoint();
            }
        });
        watchGameEndpoint.onExitListener((BooleanMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            if (message.isOk()) {
                toState(STATE_NULL);
                watchGameEndpoint.close();
                Platform.runLater(() -> {
                    programData.getStageRouter().goTo(StageRouter.LOBBY);
                });
            }
        });
        watchGameEndpoint.onUpdateMessageListener((TableFullInfoMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            TableFullInfo update = message.getTableFullInfo();
            StringBuilder msg = new StringBuilder();
            msg.append("--- table ---\n");
            msg.append("dealer : ");
            for (Card c : update.getDealerHand()) {
                if (c != null) {
                    msg.append(c.toString() + " ");
                }
            }
            msg.append("\n");
            for (Player p : update.getPlayers()) {
                if (p != null) {
                    msg.append("user " + p.getName() + " [" + p.getCash() + "] : ");
                    for (Card c : p.getHand()) {
                        if (c != null) {
                            msg.append(c.toString() + " ");
                        }
                    }
                }
                msg.append("\n");
            }
            chatTextArea.appendText(msg.toString() + "\n");
        });
        watchGameEndpoint.onUserActionMessageListener((UserActionMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            currentTable = message.getTableInfo();
            int place = message.getPlace();
            String action = message.getAction();
            int bet = message.getBet();
            chatTextArea.appendText("user " + place + ": " + action + (action.equals(ACTION_BET) ? bet : "") + "\n");
        });
        watchGameEndpoint.onResultListener((ResultMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            chatTextArea.appendText("result: todo\n");
            //todo:
        });

        Stage stage = programData.getStage();
        stage.setTitle("Black Jack / Table");
        toState(STATE_NULL);
        currentTable = programData.getCurrentTable();
        watchGameEndpoint.sendEntryMessage(currentTable);
        betOneButton.setText(String.valueOf(currentTable.getMin()));
        betTwoButton.setText(String.valueOf((currentTable.getMin() + currentTable.getMax()) / 2));
        betThreeButton.setText(String.valueOf(currentTable.getMax()));
    }

    private void initPlayGameEndpoint() {
        playGameEndpoint = programData.getPlayGameEndpoint();
        playGameEndpoint.onSitListener((BooleanMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            if (message.isOk()) {
                toState(STATE_WAIT);
            }
        });
        playGameEndpoint.onStandListener((BooleanMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            if (message.isOk()) {
                toState(STATE_ENTRY);
                playGameEndpoint.close();
            }
        });
        playGameEndpoint.onWaitActionMessageListener((WaitMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            currentTable = message.getTableInfo();
            switch (message.getWaitType()) {
                case ACTION_WAIT_BET:
                    toState(STATE_BET);
                    break;
                case ACTION_WAIT_HIT_OR_STAND:
                    toState(STATE_HIT_OR_STAND);
                    break;
            }
        });
    }

    @FXML
    public void toLobbyButtonClick() {
        if (playGameEndpoint != null && playGameEndpoint.isOpen()) {
            playGameEndpoint.sendStandMessage(currentTable, currentPlace);
        }
        watchGameEndpoint.sendExitMessage(currentTable);
    }

    @FXML
    public void sitDownButtonClick(){
        try {
            currentPlace = Integer.parseInt(placeField.getText());
            if (currentPlace < 0 || currentPlace >= currentTable.getMaxPlayerCount()) {
                throw new NumberFormatException();
            }
            playGameEndpoint.sendSitMessage(currentTable, currentPlace);
            toState(STATE_WAIT);
        } catch (NumberFormatException e) {

        }
    }

    @FXML
    public void standUpButtonClick(){
        playGameEndpoint.sendStandMessage(currentTable, currentPlace);
        toState(STATE_WAIT);
    }

    @FXML
    public void actionHitButtonClick(){
        playGameEndpoint.sendActionMessage(currentTable, currentPlace, ACTION_HIT);
        toState(STATE_WAIT);
    }

    @FXML
    public void actionStandButtonClick(){
        playGameEndpoint.sendActionMessage(currentTable, currentPlace, ACTION_STAND);
        toState(STATE_WAIT);
    }

    @FXML
    public void betOneButtonClick(){
        int bet = currentTable.getMin();
        playGameEndpoint.sendActionMessage(currentTable, currentPlace, bet);
        toState(STATE_WAIT);
    }
    @FXML
    public void betTwoButtonClick(){
        int bet = currentTable.getMax();
        playGameEndpoint.sendActionMessage(currentTable, currentPlace, bet);
        toState(STATE_WAIT);
    }
    @FXML
    public void betThreeButtonClick(){
        int bet = (currentTable.getMax() + currentTable.getMin()) / 2;
        playGameEndpoint.sendActionMessage(currentTable, currentPlace, bet);
        toState(STATE_WAIT);
    }

    private void toState(int state) {
        sitDownButton.setVisible(state == STATE_ENTRY);
        placeField.setVisible(state == STATE_ENTRY);
        standUpButton.setVisible(state != STATE_ENTRY && state != STATE_NULL);
        actionHitButton.setVisible(state == STATE_HIT_OR_STAND);
        actionStandButton.setVisible(state == STATE_HIT_OR_STAND);
        betOneButton.setVisible(state == STATE_BET);
        betTwoButton.setVisible(state == STATE_BET);
        betThreeButton.setVisible(state == STATE_BET);
    }

}



package sample;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nulp.pist21.blackjack.client.endpoint.PlayGameEndpoint;
import nulp.pist21.blackjack.client.endpoint.WatchGameEndpoint;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.Player;
import nulp.pist21.blackjack.model.TableFullInfo;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.deck.IDeck;

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
    @FXML public Canvas canvas;

    Point2D[] points = new Point2D[]{
            new Point2D(290, 80),
            new Point2D(450, 50),
            new Point2D(510, 130),
            new Point2D(380, 200),
            new Point2D(180, 200),
            new Point2D(50, 130),
            new Point2D(100, 50),
    };

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
            drawTable();
            drawDiller();
            StringBuilder msg = new StringBuilder();
            msg.append("--- table ---\n");
            msg.append("dealer : ");
            drawDillerCard(update.getDealerHand());
            for (Card c : update.getDealerHand()) {
                if (c != null) {
                    msg.append(c.toString()).append(" ");
                }
            }
            msg.append("\n");
            Player[] players = update.getPlayers();
            for (int i = 0; i < players.length; i++) {
                Player p = players[i];
                if (p != null) {
                    if (currentPlace == i) {
                        drawMainCard(i, p.getHand());
                        drawMainPlayer(i, p.getName(), p.getCash());
                    } else {
                        drawCard(i + 1, p.getHand());
                        drawPlayer(i, p.getName(), p.getCash());
                    }
                    msg.append("user ").append(p.getName()).append(" [").append(p.getCash()).append("] : ");
                    for (Card c : p.getHand()) {
                        if (c != null) {
                            msg.append(c.toString()).append(" ");
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

    public void drawTable(){
        Image imgDeck;
        Image imgCoints;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        imgDeck = new Image(getClass().getResourceAsStream("img/table.png"));
        imgCoints = new Image(getClass().getResourceAsStream("img/fishka.png"));
        gc.drawImage(imgDeck,50, -30, 500, canvas.getHeight());
        gc.drawImage(imgCoints, 280, canvas.getHeight()/3, 40, 50);
        gc.setLineWidth(1.5);
        gc.setFont(Font.font("Calibri", 16));
        gc.setFill(Color.GREY);
        gc.setStroke(Color.WHITE);
        for (int i = 1; i < points.length; i++) {
            Point2D point = points[i];
            gc.fillOval(point.getX(), point.getY(), 40, 40);
            gc.strokeText("" + i , point.getX() + 16, point.getY() + 23);
        }
    }

    public void drawDiller(){
        Image imgDiler;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        imgDiler = new Image(getClass().getResourceAsStream("img/dealer.png"));
        gc.drawImage(imgDiler, 255, 10, 90, 80);
    }

    public void drawPlayer(int place, String playerName, int playerCash){
        Image imgPlayerPhoto;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Point2D point = points[place];
        gc.setLineWidth(1);
        gc.setFont(Font.font(13));
        gc.setFill(Color.LIGHTGREY);
        gc.setStroke(Color.BLACK);
        imgPlayerPhoto = new Image(getClass().getResourceAsStream("img/image.jpg"));
        gc.fillRect(point.getX()-40,point.getY(),120,40);
        gc.drawImage(imgPlayerPhoto, point.getX() - 50, point.getY(), 40,40);
        gc.strokeText(playerName, point.getX() -5, point.getY() + 14);
        gc.strokeLine(point.getX() - 10, point.getY() + 20, point.getX() + 80 , point.getY() + 20);
        gc.strokeText("$ " + playerCash, point.getX() -5, point.getY() + 35);
    }

    public void drawMainPlayer(int place, String playerName, int playerCash){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Point2D point = points[place];
        gc.setLineWidth(1);
        gc.setFont(Font.font(14));
        gc.setFill(Color.GREY);
        gc.setStroke(Color.WHITE);
        gc.fillRect(point.getX()-30,point.getY(),100,40);
        gc.strokeText(playerName, point.getX() -25, point.getY() + 14);
        gc.strokeLine(point.getX() - 30, point.getY() + 20, point.getX() + 70 , point.getY() + 20);
        gc.strokeText("$ " + playerCash, point.getX() -25, point.getY() + 35);
    }

    public void drawCard(int place, Card[] cards){
        Image imgCard;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Point2D point = points[place];
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            imgCard = new Image(getClass().getResourceAsStream("img/cards/" + card.getSuit() + card.getValue() + ".jpg"));
            gc.drawImage(imgCard, point.getX() - 50 + 20 * i, point.getY() - 30, 50, 70);
        }
    }

    public void drawMainCard(int place, Card[] cards){
        Image imgCard;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Point2D point = points[place];
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            imgCard = new Image(getClass().getResourceAsStream("img/cards/" +card.getSuit() + card.getValue() + ".jpg"));
            gc.drawImage(imgCard, point.getX() - 30 + 20 * i, point.getY() - 30, 45, 60);
        }
    }

    public void drawDillerCard(Card[] cards){
        Image imgCard;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Point2D point = points[0];

        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            imgCard = new Image(getClass().getResourceAsStream("img/cards/" + card.getSuit() + card.getValue() + ".jpg"));
            gc.drawImage(imgCard, point.getX() - 30 + 20 * i, point.getY() + 3, 30, 40);
        }
        imgCard = new Image(getClass().getResourceAsStream("img/cards/413.jpg"));
        gc.drawImage(imgCard, point.getX() - 30 , point.getY() + 3, 20, 40);
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
        currentPlace = -1;
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
        drawTable();
        drawDiller();
    }

}



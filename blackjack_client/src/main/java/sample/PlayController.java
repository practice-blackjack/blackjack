package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.deck.IDeck;
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
    //----------------------------------------------------
    @FXML
    public Canvas canvas;
    GraphicsContext gc;
    Image imgDeck;
    Image imgDiler;
    Image imgCoints;
    Image imgPlayerPhoto;
    Image imgCard;
    int addX = 10;
    Point2D[] points = new Point2D[]{
            new Point2D(450, 50),
            new Point2D(510, 130),
            new Point2D(380, 200),
            new Point2D(180, 200),
            new Point2D(50, 130),
            new Point2D(100, 50),
            new Point2D(290, 80)
    };
    IDeck deck = new EndlessDeck();
    //--------------------------------------------------
    @FXML
    public void initialize(){
        stand.setVisible(false);
        hit.setVisible(false);
        btnFirstbet.setVisible(false);
        btnSecondbet.setVisible(false);
        btnThirdbet.setVisible(false);
        String name = setPlayerName(playerName);
        System.out.println(name);
        drawTable();
    }
    public void drawTable(){
        //--------------------------------------CANVAS------------------------------------
        gc = canvas.getGraphicsContext2D();
        imgDeck = new Image(getClass().getResourceAsStream("img/table.png"));
        imgCoints = new Image(getClass().getResourceAsStream("img/fishka.png"));
        gc.drawImage(imgDeck,50, -30, 500, canvas.getHeight());
        gc.drawImage(imgCoints, 280, canvas.getHeight()/3, 40, 50);
        gc.setLineWidth(1.5);
        gc.setFont(Font.font("Calibri", 16));
        gc.setFill(Color.GREY);
        gc.setStroke(Color.WHITE);
        for (int i = 0; i < points.length - 1; i++) {
            Point2D point = points[i];
            gc.fillOval(point.getX(), point.getY(), 40, 40);
            gc.strokeText("" + (i + 1), point.getX() + 16, point.getY() + 23);
        }
    }
    //-------------------------------------------------------------------------------------------------------
    //-----------------------------CANVAS-------------------------------------
    public void drawDiller(){
        gc = canvas.getGraphicsContext2D();
        imgDiler = new Image(getClass().getResourceAsStream("img/dealer.png"));
        gc.drawImage(imgDiler, 255, 10, 90, 80);
    }
    public void drawPlayer(int place, String playerName, int playerCash){
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
        //----------------------------------------POINTS----------------------------------------------------------------
    }

    public void drawMainPlayer(int place, String playerName, int playerCash){
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
        Point2D point = points[place];
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            System.out.println("img/cards/" +card.getSuit() + card.getValue() + ".jpg");
            imgCard = new Image(getClass().getResourceAsStream("img/cards/" +card.getSuit() + card.getValue() + ".jpg"));
            gc.drawImage(imgCard, point.getX() - 50 + 20 * i, point.getY() - 30, 50, 70);
        }
        // points drawer
        //drawPoints(place, cards);
    }

    public void drawMainCard(int place, Card[] cards){
        Point2D point = points[place];
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            System.out.println("MAIN:   img/cards/" +card.getSuit() + card.getValue() + ".jpg");
            imgCard = new Image(getClass().getResourceAsStream("img/cards/" +card.getSuit() + card.getValue() + ".jpg"));
            gc.drawImage(imgCard, point.getX() - 30 + 20 * i, point.getY() - 30, 45, 60);
        }
    }

    public void addMainCard(int place, Card[] cards){
        Point2D point = points[place];
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            System.out.println("ADD:  img/cards/" +card.getSuit() + card.getValue() + ".jpg");
            imgCard = new Image(getClass().getResourceAsStream("img/cards/" +card.getSuit() + card.getValue() + ".jpg"));
            gc.drawImage(imgCard, point.getX() - 3 + addX + 20 * i, point.getY() - 30, 45, 60);
            addX += 15;
            System.out.println(addX);
        }
    }
    public void drawDillerCard(int place, Card[] cards){
        Point2D point = points[place];

        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            System.out.println("MAIN:   img/cards/" + card.getSuit() + card.getValue() + ".jpg");
            imgCard = new Image(getClass().getResourceAsStream("img/cards/" + card.getSuit() + card.getValue() + ".jpg"));
            gc.drawImage(imgCard, point.getX() - 30 + 20 * i, point.getY() + 3, 30, 40);
        }
        imgCard = new Image(getClass().getResourceAsStream("img/cards/413.jpg"));
        gc.drawImage(imgCard, point.getX() - 30 , point.getY() + 3, 20, 40);
    }
    /*public void drawPoints(int place, Card[] cards){
        Point2D point = points[place];
        gc.setFill(Color.GREEN);
        for(int i = 0; i < cards.length; i++){
            Card card = cards[i];
            gc.fillText("" + card.getValue() + "", point.getX() + 50, point.getY() + 50);

        }
    }*/

    //-------------------------------------------------------------------------


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
        betLabel.setVisible(false);
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        drawTable();
    }
    public void toMenu() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LobbyFrame.fxml"));
        Parent root = loader.load();
        LobbyFrameController controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void hitButton(){
        gameLog.appendText("card add\n");
        addMainCard(1, new Card[]{deck.next()});
        drawMainPlayer(1, "BlackPlayer", 2500);
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
//------------draw cards--------------------------------------------
        drawCard(0, new Card[]{deck.next(), deck.next()});
        drawCard(2, new Card[]{deck.next(), deck.next()});
        drawCard(3, new Card[]{deck.next(), deck.next()});
        drawCard(4, new Card[]{deck.next(), deck.next()});
        drawCard(5, new Card[]{deck.next(), deck.next()});
        drawMainCard(1, new Card[]{deck.next(), deck.next()});
        drawDillerCard(6, new Card[]{deck.next(), deck.next()});
// -----------draw players------------------------------------------
        drawPlayer(2, "Milk", 200);
        drawPlayer(4, "Karl", 150);
        drawPlayer(0, "Zello", 570);
        drawPlayer(3, "Lukas", 570);
        drawPlayer(5, "Blank", 325);
        drawMainPlayer(1, "BlackPlayer", 2500);
        drawDiller();
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
        bet = 10;
        //logger
        gameLog.appendText("Bet \t" + String.valueOf(bet) + "\n");
        betLabel.setText("$ " + String.valueOf(bet));
        betLabel.setVisible(true);
    }
    @FXML
    public void actionBtnSecondbet(){
        hit.setVisible(true);
        stand.setVisible(true);
        btnFirstbet.setVisible(false);
        btnSecondbet.setVisible(false);
        btnThirdbet.setVisible(false);
        bet = 20;
        //logger
        gameLog.appendText("Bet \t" + String.valueOf(bet) + "\n");
        betLabel.setText("$ " + String.valueOf(bet));
        betLabel.setVisible(true);
    }
    @FXML
    public void actionBtnThirdbet(){
        hit.setVisible(true);
        stand.setVisible(true);
        btnFirstbet.setVisible(false);
        btnSecondbet.setVisible(false);
        btnThirdbet.setVisible(false);
        bet = 50;
        //logger
        gameLog.appendText("Bet \t" + String.valueOf(bet) + "\n");
        betLabel.setText("$ " + String.valueOf(bet));
        betLabel.setVisible(true);
    }
}



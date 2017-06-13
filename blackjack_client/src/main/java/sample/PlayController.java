package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
    //----------------------------------------------------
    @FXML
    public Canvas canvas;
    GraphicsContext gc;
    Image imgDeck;
    Image imgDiler;
    Image imgCoints;
    Image imgPlayerPhoto;
    Point2D[] points = new Point2D[]{

            new Point2D(450, 50),
            new Point2D(510, 130),
            new Point2D(380, 200),
            new Point2D(180, 200),
            new Point2D(50, 130),
            new Point2D(100, 50),
    };
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



 //--------------------------------------CANVAS------------------------------------
        gc = canvas.getGraphicsContext2D();
        imgDeck = new Image(getClass().getResourceAsStream("img/table.png"));
        imgDiler = new Image(getClass().getResourceAsStream("img/dealer.png"));
        imgCoints = new Image(getClass().getResourceAsStream("img/fishka.png"));
        gc.drawImage(imgDeck,50, -30, 500, canvas.getHeight());
        gc.drawImage(imgDiler, 255, 10, 90, 80);
        gc.drawImage(imgCoints, 280, canvas.getHeight()/3, 40, 50);
        gc.setLineWidth(1.5);
        gc.setFont(Font.font("Calibri", 16));
        gc.setFill(Color.GREY);
        gc.setStroke(Color.WHITE);
        for (int i = 0; i < points.length; i++) {
            Point2D point = points[i];
            gc.fillOval(point.getX(), point.getY(), 40, 40);
            gc.strokeText("" + (i + 1), point.getX() + 16, point.getY() + 23);
        }
//-------------------------------------------------------------------------------------------------------
    }

    public void drawPlayer(int place, String playerName, int playerCash){
        //-----------------------------CANVAS-------------------------------------

        Point2D point = points[place];
        gc.setLineWidth(1);
        gc.setFont(Font.font(12));
        imgPlayerPhoto = new Image(getClass().getResourceAsStream("img/image.jpg"));
        gc.fillRect(point.getX()-50,point.getY(),120,40);
        gc.drawImage(imgPlayerPhoto, point.getX() - 50, point.getY(), 40,40);
        gc.strokeText("Name: " + playerName, point.getX() -5, point.getY() + 12);
        gc.strokeText("Cash $: " + playerCash, point.getX() -5, point.getY() + 35);
        //-------------------------------------------------------------------------
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


        drawPlayer(2, "Milk", 200);
        drawPlayer(4, "Karl", 150);
        drawPlayer(5, "Blank", 325);
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



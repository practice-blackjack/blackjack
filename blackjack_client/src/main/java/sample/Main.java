package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    private final ProgramData programData;

    public Main() {
        this.programData = ProgramData.get();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        programData.setStage(primaryStage);
        programData.getStageRouter().goTo(StageRouter.SIGN_IN);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
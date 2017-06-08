package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PlayFrame.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Black Jack 21");

        PlayController controller = loader.getController();
        controller.setStage(primaryStage);
        Dimension screenDimention = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setX( screenDimention.getWidth()/2 - 150);
        primaryStage.setY(screenDimention.getHeight()/2 - 150);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
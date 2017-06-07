package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Created by Ol'ko on 22.05.2017.
 */
public class SignInController {
    @FXML
    public PasswordField pf;
    public TextField lf;
    public Hyperlink regLink;
    Stage stage;

    // singIn start
    @FXML
    public void signInButton()throws IOException{
       if(pf.getText().equals("root") && lf.getText().equals("root")) {
           LobbyFrameController(stage);
       }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    // open registration frame
    @FXML
    public void registration() throws IOException{
        RegframeController(stage);
    }
    protected void RegframeController(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RegistrationFrame.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    protected void LobbyFrameController(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LobbyFrame.fxml"));
        Parent root = loader.load();
        LobbyFrameController controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}

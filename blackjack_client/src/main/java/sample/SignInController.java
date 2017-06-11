package sample;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nulp.pist21.blackjack.client.endpoint.InitEndpoint;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.model.User;

import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Ol'ko on 22.05.2017.
 */
public class SignInController {

    private final ProgramData programData = ProgramData.get();

    @FXML
    public Label lblInfoSignIn;
    @FXML
    public PasswordField pf;
    public TextField lf;
    public Hyperlink regLink;
    Stage stage;

    private InitEndpoint initEndpoint;

    @FXML
    public void initialize() {
        initEndpoint = programData.getInitEndpoint();
    }

    // singIn start
    @FXML
    public void signInButton()throws IOException{
        String login = lf.getText();
        String password = pf.getText();
        login(login, password);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    private void login(String name, String password) {
        initEndpoint.onLoginListener((TokenMessage tokenMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tokenMessage));
            long token = tokenMessage.getToken();
            programData.setToken(token);
            Platform.runLater(() -> {
                if (token != -1) {
                    LobbyFrameController();
                    lblInfoSignIn.setText("Login is successfully");
                } else {
                    lblInfoSignIn.setText("Error! You print error login or password!");
                }
            });
        });
        User user = new User(name, password);
        initEndpoint.sendLoginMessage(user);
    }

    // open registration frame
    @FXML
    public void registration() {
        RegframeController();
    }

    protected void RegframeController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RegistrationFrame.fxml"));
            Parent root = loader.load();
            RegistrationController controller = loader.getController();
            controller.setStage(stage);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

        }
    }

    protected void LobbyFrameController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LobbyFrame.fxml"));
            Parent root = loader.load();
            LobbyFrameController controller = loader.getController();
            controller.setStage(stage);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

        }

    }
}

package sample;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nulp.pist21.blackjack.client.endpoint.InitEndpoint;
import nulp.pist21.blackjack.message.BooleanMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.model.User;
import java.io.IOException;

/**
 * Created by Ol'ko on 22.05.2017.
 */

public class RegistrationController {

    private final ProgramData programData = ProgramData.get();

    private Stage stage;
    @FXML
    private TextField login;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button backToSignIn;
    @FXML
    private Label info;

    private InitEndpoint initEndpoint;

    @FXML
    public void initialize() {
        initEndpoint = programData.getInitEndpoint();
        initEndpoint.onLoginListener((TokenMessage tokenMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tokenMessage));
            long token = tokenMessage.getToken();
            programData.setToken(token);
            Platform.runLater(() -> {
                if (token != -1) {
                    LobbyFrameController();
                    info.setText("Login is successfully");
                } else {
                    info.setText("Error! You print error login or password!");
                }
            });
        });
        initEndpoint.onRegisterListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
            if (booleanMessage.isOk()) {
                Platform.runLater(() -> {
                    info.setText("Registration is successfully");
                    info.setTextFill(Color.GREEN);
                    RegframeController();
                });
                String log = login.getText();
                String pass = password.getText();
                initEndpoint.sendLoginMessage(new User(log, pass));
            } else {
                Platform.runLater(() -> {
                    info.setText("Registration is NOT successfully. Retype your password");
                    info.setTextFill(Color.RED);
                });
            }
        });
    }

    protected void RegframeController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LobbyFrame.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e) {

        }
    }

    @FXML
    public void actionBackToSignIn(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SignInFrame.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            SignInController controller = loader.getController();
            controller.setStage(stage);

            stage.setTitle("SignIn Frame Frame");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    public void registerButton() {
        String log = login.getText();
        String pass = password.getText();
        String confPass = confirmPassword.getText();
        if (pass.equals(confPass)) {
        } else {
            info.setText("Registration is NOT successfully. Retype your password");
            info.setTextFill(Color.RED);
        }

    }
    public void setStage(Stage stage) {
        this.stage = stage;

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


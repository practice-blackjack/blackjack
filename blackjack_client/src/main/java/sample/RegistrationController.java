package sample;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nulp.pist21.blackjack.client.endpoint.InitEndpoint;
import nulp.pist21.blackjack.message.BooleanMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.model.User;

public class RegistrationController {

    private final ProgramData programData = ProgramData.get();
    private InitEndpoint initEndpoint;

    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label infoLabel;

    @FXML
    public void initialize() {
        initEndpoint = programData.getInitEndpoint();
        initEndpoint.onLoginListener((TokenMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            long token = message.getToken();
            programData.setToken(token);
            Platform.runLater(() -> {
                if (token != -1) {
                    infoLabel.setTextFill(Color.GREEN);
                    infoLabel.setText("Login is successfully");
                    programData.initLobby();
                    programData.getStageRouter().goTo(StageRouter.LOBBY);
                } else {
                    infoLabel.setTextFill(Color.RED);
                    infoLabel.setText("Error! You print error loginField or passwordField!");
                }
            });
        });
        initEndpoint.onRegisterListener((BooleanMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            if (message.isOk()) {
                Platform.runLater(() -> {
                    infoLabel.setTextFill(Color.GREEN);
                    infoLabel.setText("Registration is successfully");
                });
                String login = this.loginField.getText();
                String password = this.passwordField.getText();
                initEndpoint.sendLoginMessage(new User(login, password));
            } else {
                Platform.runLater(() -> {
                    infoLabel.setTextFill(Color.RED);
                    infoLabel.setText("Registration is NOT successfully. Retype your passwordField");
                });
            }
        });

        Stage stage = programData.getStage();
        stage.setTitle("Black Jack / Registration");
    }

    @FXML
    public void toSignInButtonClick(){
        programData.getStageRouter().goTo(StageRouter.SIGN_IN);
    }

    @FXML
    public void registerButtonClick() {
        String login = this.loginField.getText();
        String password = this.passwordField.getText();
        String confirmPassword = this.confirmPasswordField.getText();
        if (password.equals(confirmPassword)) {
            initEndpoint.sendRegisterMessage(new User(login, password));
        } else {
            infoLabel.setText("Registration is NOT successfully. Retype your passwordField");
            infoLabel.setTextFill(Color.RED);
        }
    }
}


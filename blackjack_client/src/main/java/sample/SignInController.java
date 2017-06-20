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
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.model.User;

import java.awt.*;
import java.io.IOException;

public class SignInController {

    private final ProgramData programData = ProgramData.get();
    private InitEndpoint initEndpoint;

    @FXML public Label infoLabel;
    @FXML public PasswordField passwordField;
    @FXML public TextField loginField;

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
                    infoLabel.setText("Error! You print error login or password!");
                }
            });
        });

        Stage stage = programData.getStage();
        stage.setTitle("Black Jack / Sign In");
    }

    @FXML
    public void signInButtonClick() {
        String login = loginField.getText();
        String password = passwordField.getText();
        initEndpoint.sendLoginMessage(new User(login, password));
    }

    @FXML
    public void toRegistrationButtonClick() {
        programData.getStageRouter().goTo(StageRouter.REGISTER);
    }

}

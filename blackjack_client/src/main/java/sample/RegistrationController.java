package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Ol'ko on 22.05.2017.
 */

public class RegistrationController {
    Stage primaryStage;
    @FXML
    private TextField login;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label info;

    public void setStage(Stage stage){
        this.primaryStage = stage;
    }

    protected void RegframeController(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LobbyFrame.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void registerButton() throws IOException {
        String log = login.getText();
        String mail = email.getText();
        String pass = password.getText();
        String confPass = confirmPassword.getText();
        if (pass.equals(confPass)) {
            info.setText("Registration is successfully");
            info.setTextFill(Color.GREEN);
            RegframeController(primaryStage);

        } else {
            info.setText("Registration is NOT successfully. Retype your password");
            info.setTextFill(Color.RED);
        }
    }
}


package sample;
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

import java.io.IOException;

/**
 * Created by Ol'ko on 22.05.2017.
 */

public class RegistrationController {
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
            info.setText("Registration is successfully");
            info.setTextFill(Color.GREEN);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("LobbyFrame.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                LobbyFrameController controller = loader.getController();
                controller.setStage(stage);

                stage.setTitle("Lobby Frame");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();


            }catch (Exception ex){
                ex.printStackTrace();
            }

        } else {
            info.setText("Registration is NOT successfully. Retype your password");
            info.setTextFill(Color.RED);
        }

    }
    public void setStage(Stage stage) {
        this.stage = stage;

    }
}


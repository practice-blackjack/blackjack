package sample;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by Ol'ko on 22.05.2017.
 */
public class SettingsController {

    Stage stage;

    @FXML
    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    public void fileOpen(){
        System.out.println("ok");

    }
}

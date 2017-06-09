package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Ol'ko on 29.05.2017.
 */
public class LobbyFrameController {
    @FXML
    private javafx.scene.control.ListView<String> listView;
    @FXML
    public javafx.scene.control.TextField nameField;
    @FXML
    public javafx.scene.control.TextField rateField;
    @FXML
    public javafx.scene.control.TextField peopleField;
    @FXML
    public javafx.scene.control.Label lblPlayerName;
    @FXML
    public javafx.scene.control.Label lblCash;

    public String[] tableName = new String[10];
    public Integer[] MinRate = new Integer[10];
    public Integer[] MaxRate = new Integer[10];
    public String[] people = new String[10];

    private Stage stage;

    @FXML
    public void initialize(){
        String playerName = "BlackPlayer";
        Integer cash = 2500;
        lblPlayerName.setText(playerName);
        lblCash.setText((cash).toString());
        // parsing ListView

        tableName[0] = "Mexico";
        tableName[1] = "New York";
        tableName[2] = "Praha";
        tableName[3] = "London";
        tableName[4] = "Costa Rica";

        MinRate[0] = 10;
        MinRate[1] = 50;
        MinRate[2] = 100;
        MinRate[3] = 300;
        MinRate[4] = 500;

        MaxRate[0] = 50;
        MaxRate[1] = 100;
        MaxRate[2] = 200;
        MaxRate[3] = 500;
        MaxRate[4] = 1000;

        people[0] = "1/4";
        people[1] = "2/6";
        people[2] = "3/5";
        people[3] = "1/7";
        people[4] = "0/5";

        ObservableList<String> data = FXCollections.observableArrayList(
                tableName[0] + "\t\t\tRate: \t" + MinRate[0] + "-" + MaxRate[0] + "\n\t\t\t\tPeople: \t" + people[0], tableName[1] +
                        "\t\t\tRate: \t" + MinRate[1] + "-" + MaxRate[1] +"\n\t\t\t\tPeople: \t" + people[1], tableName[2] + "\t\t\tRate: \t"
                        + MinRate[2] + "-" + MaxRate[2] + "\n\t\t\t\tPeople: \t" + people[2], tableName[3] + "\t\t\tRate: \t" + MinRate[3] +
                        "-" + MaxRate[3] + "\n\t\t\t\tPeople: \t" + people[3], tableName[4] + "\t\tRate: \t" + MinRate[4] + "-" + MaxRate[4] +
                        "\n\t\t\t\tPeople:" + " \t" + people[4]
        );
        listView.setItems(data);
        // end parsing
        //select item
    }
    public void select(){
        listView.setOnMouseClicked(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    if (mouseEvent.getClickCount() == 2) {
                    Integer index = listView.getSelectionModel().getSelectedIndex();
                    nameField.setText(tableName[index]);
                    rateField.setText(MinRate[index].toString() + "-" + MaxRate[index]);
                    peopleField.setText(people[index]);
                    try{
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("PlayFrame.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);

                        PlayController controller = loader.getController();
                        controller.setStage(stage);

                        stage.setTitle("Game Frame");
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();


                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                } else if (mouseEvent.getClickCount() == 1) {
                    Integer index = listView.getSelectionModel().getSelectedIndex();
                    nameField.setText(tableName[index]);
                    rateField.setText(MinRate[index].toString() + "-" + MaxRate[index]);
                    peopleField.setText(people[index]);
                }
            }
        });
    }
  @FXML
  public void settingsButton(){

  }

    public void setStage(Stage stage) {
        this.stage = stage;
        select();
    }
}

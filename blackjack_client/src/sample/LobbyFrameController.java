package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public String[] tableName = new String[10];
    public String[] rate = new String[10];
    public String[] people = new String[10];
    private Stage stage;

    @FXML
    public void initialize(){
        // parsing ListView

        tableName[0] = "Mexico";
        tableName[1] = "New York";
        tableName[2] = "Praha";
        tableName[3] = "London";
        tableName[4] = "Costa Rica";

        rate[0] = "5-50$";
        rate[1] = "51-70$";
        rate[2] = "71-100$";
        rate[3] = "101-200$";
        rate[4] = "201-500$";

        people[0] = "1/4";
        people[1] = "2/6";
        people[2] = "3/5";
        people[3] = "1/7";
        people[4] = "0/5";

        ObservableList<String> data = FXCollections.observableArrayList(
                tableName[0] + "\t\t\tRate: \t" + rate[0] + "\n\t\t\t\tPeople: \t" + people[0], tableName[1] +
                        "\t\t\tRate: \t" + rate[1] + "\n\t\t\t\tPeople: \t" + people[1], tableName[2] + "\t\t\tRate: \t"
                        + rate[2] + "\n\t\t\t\tPeople: \t" + people[2], tableName[3] + "\t\t\tRate: \t" + rate[3] +
                        "\n\t\t\t\tPeople: \t" + people[3], tableName[4] + "\t\tRate: \t" + rate[4] + "\n\t\t\t\tPeople: \t" + people[4]
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
                    rateField.setText(rate[index]);
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
                    rateField.setText(rate[index]);
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

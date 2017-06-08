package sample;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nulp.pist21.blackjack.client.endpoint.LobbyEndpoint;
import nulp.pist21.blackjack.message.TableListMessage;
import nulp.pist21.blackjack.model.TableInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ol'ko on 29.05.2017.
 */
public class LobbyFrameController {

    private final ProgramData programData = ProgramData.get();

    @FXML
    private javafx.scene.control.ListView<String> listView;
    @FXML
    public javafx.scene.control.TextField nameField;
    @FXML
    public javafx.scene.control.TextField rateField;
    @FXML
    public javafx.scene.control.TextField peopleField;
    private Stage stage;

    private ObservableList<String> listItems;
    private List<TableInfo> tables;

    private LobbyEndpoint lobbyEndpoint;

    @FXML
    public void initialize(){
        listItems = FXCollections.observableArrayList();
        listView.setItems(listItems);
        lobbyEndpoint = programData.getLobbyEndpoint();
        lobbyEndpoint.onUpdateMessageListener((TableListMessage tableListMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tableListMessage));
        });
        lobbyEndpoint.onTableListListener((TableListMessage tableListMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tableListMessage));
            tables = tableListMessage.getTableList();
            List<String> collect = tables.stream().map(ti -> ti.getName() + "\t\t\tRate: \t" + ti.getMin() + "-" + ti.getMax() + "\n\t\t\t\tPeople: \t" + ti.getPlayerCount() + "/" + ti.getMaxPlayerCount()).collect(Collectors.toList());
            Platform.runLater(() ->{
                listItems.clear();
                listItems.addAll(collect);
                listView.refresh();
            });
        });
        lobbyEndpoint.sendTableListMessage();
    }
    public void select(){
        listView.setOnMouseClicked((javafx.scene.input.MouseEvent mouseEvent) -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                TableInfo currentTable = tables.get(index);
                programData.setCurrentTable(currentTable);
                nameField.setText(currentTable.getName());
                rateField.setText(currentTable.getMin() + "-" + currentTable.getMax());
                peopleField.setText(currentTable.getPlayerCount() + "/" + currentTable.getMaxPlayerCount());
            }
            if (mouseEvent.getClickCount() == 2) {
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

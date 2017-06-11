package sample;
import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nulp.pist21.blackjack.client.endpoint.InitEndpoint;
import nulp.pist21.blackjack.client.endpoint.LobbyEndpoint;
import nulp.pist21.blackjack.message.BooleanMessage;
import nulp.pist21.blackjack.message.MessageFunction;
import nulp.pist21.blackjack.message.TableListMessage;
import nulp.pist21.blackjack.message.UserMessage;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class LobbyFrameController {

    private final ProgramData programData = ProgramData.get();
    private InitEndpoint initEndpoint;
    private LobbyEndpoint lobbyEndpoint;

    @FXML private ListView<String> tableListView;
    @FXML public TextField nameField;
    @FXML public TextField rateField;
    @FXML public TextField peopleField;
    @FXML public Label userNameLabel;
    @FXML public Label userCashLabel;
    @FXML public Label infoLabel;

    private ObservableList<String> tableListViewItems;
    private List<TableInfo> tables;

    @FXML
    public void initialize(){
        initEndpoint = programData.getInitEndpoint();
        initEndpoint.onLogoutListener((BooleanMessage message) -> {
            Platform.runLater(() -> {
                if (message.isOk()) {
                    infoLabel.setTextFill(Color.GREEN);
                    infoLabel.setText("Logout is successfully");
                    programData.getStageRouter().goTo(StageRouter.SIGN_IN);
                } else {
                    infoLabel.setTextFill(Color.RED);
                    infoLabel.setText("Logout error");
                }
            });
        });
        lobbyEndpoint = programData.getLobbyEndpoint();
        lobbyEndpoint.onMyDataListener((UserMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            Platform.runLater(() -> {
                User currentUser = message.getUser();
                userNameLabel.setText(currentUser.getName());
                userCashLabel.setText(String.valueOf(currentUser.getCash()));
            });
        });
        lobbyEndpoint.onTableListListener((TableListMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            tables = message.getTableList();
            //todo:
            List<String> collect = tables.stream().map(ti -> ti.getName() + "\t\t\tRate: \t" + ti.getMin() + "-" + ti.getMax() + "\n\t\t\t\tPeople: \t" + ti.getPlayerCount() + "/" + ti.getMaxPlayerCount()).collect(Collectors.toList());
            Platform.runLater(() ->{
                tableListViewItems.clear();
                tableListViewItems.addAll(collect);
                tableListView.refresh();
                if(tableListViewItems.size() > 0) {
                    tableListView.getSelectionModel().select(0);
                }
            });
        });
        lobbyEndpoint.onUpdateMessageListener((TableListMessage message) -> {
            System.out.println("server > " + JSON.toJSONString(message));
            tables = message.getTableList();
            //todo:
            List<String> collect = tables.stream().map(ti -> ti.getName() + "\t\t\tRate: \t" + ti.getMin() + "-" + ti.getMax() + "\n\t\t\t\tPeople: \t" + ti.getPlayerCount() + "/" + ti.getMaxPlayerCount()).collect(Collectors.toList());
            Platform.runLater(() ->{
                tableListViewItems.clear();
                tableListViewItems.addAll(collect);
                tableListView.refresh();
            });
        });

        Stage stage = programData.getStage();
        stage.setTitle("Black Jack / Lobby");
        tableListViewItems = FXCollections.observableArrayList();
        tableListView.setItems(tableListViewItems);
        lobbyEndpoint.sendMyDataMessage();
        lobbyEndpoint.sendTableListMessage();
    }

    @FXML
    public void tableListViewClick(){
        tableListView.setOnMouseClicked((javafx.scene.input.MouseEvent mouseEvent) -> {
            int index = tableListView.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                TableInfo currentTable = tables.get(index);
                nameField.setText(currentTable.getName());
                rateField.setText(currentTable.getMin() + "-" + currentTable.getMax());
                peopleField.setText(currentTable.getPlayerCount() + "/" + currentTable.getMaxPlayerCount());
                if (mouseEvent.getClickCount() == 2) {
                    programData.setCurrentTable(currentTable);
                    programData.getStageRouter().goTo(StageRouter.PLAY);
                }
            }
        });
    }

    @FXML
    public void logoutButtonClick() {
        initEndpoint.sendLogoutMessage();
    }

    @FXML
    public void settingsButtonClick(){
        //todo:
    }

}

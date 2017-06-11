package nulp.pist21.blackjack.client;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.client.endpoint.InitEndpoint;
import nulp.pist21.blackjack.client.endpoint.LobbyEndpoint;
import nulp.pist21.blackjack.client.endpoint.PlayGameEndpoint;
import nulp.pist21.blackjack.client.endpoint.WatchGameEndpoint;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static nulp.pist21.blackjack.message.MessageConstant.ACTION_WAIT_BET;
import static nulp.pist21.blackjack.message.MessageConstant.ACTION_WAIT_HIT_OR_STAND;

public class Main {

    private WebSocketContainer container;

    private InitEndpoint initEndpoint;
    private LobbyEndpoint lobbyEndpoint;
    private WatchGameEndpoint watchGameEndpoint;
    private PlayGameEndpoint playGameEndpoint;

    private long token;

    private List<TableInfo> tableList = new ArrayList<>();

    public Main() throws URISyntaxException, IOException, DeploymentException {
        container = ContainerProvider.getWebSocketContainer();
        initInit();
    }

    private void initInit() {
        try {
            initEndpoint = new InitEndpoint();
            container.connectToServer(initEndpoint, new URI("ws://localhost:8080/blackjack/init"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void initLobby() {
        try {
            lobbyEndpoint = new LobbyEndpoint(token);
            lobbyEndpoint.onTokenCheckerMessageListener((BooleanMessage booleanMessage) -> {
                System.out.println("server > " + JSON.toJSONString(booleanMessage));
                if (!booleanMessage.isOk()) {
                    lobbyEndpoint.sendTokenMessage();
                }
            });
            container.connectToServer(lobbyEndpoint, new URI("ws://localhost:8080/blackjack/lobby"));
            lobbyEndpoint.onUpdateMessageListener((TableListMessage tableListMessage) -> {
                System.out.println("server > " + JSON.toJSONString(tableListMessage));
            });
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void initWatchGame() throws URISyntaxException {
        try {
            watchGameEndpoint = new WatchGameEndpoint(token);
            watchGameEndpoint.onTokenCheckerMessageListener((BooleanMessage booleanMessage) -> {
                System.out.println("server > " + JSON.toJSONString(booleanMessage));
                if (!booleanMessage.isOk()) {
                    watchGameEndpoint.sendTokenMessage();
                }
            });
            container.connectToServer(watchGameEndpoint, new URI("ws://localhost:8080/blackjack/game/watch"));
            watchGameEndpoint.onUpdateMessageListener((TableFullInfoMessage tableFullInfoMessage) -> {
                System.out.println("server > " + JSON.toJSONString(tableFullInfoMessage));
            });
            watchGameEndpoint.onUserActionMessageListener((UserActionMessage userActionMessage) -> {
                System.out.println("server > " + JSON.toJSONString(userActionMessage));
            });
            watchGameEndpoint.onResultListener((ResultMessage resultMessage) -> {
                System.out.println("server > " + JSON.toJSONString(resultMessage));
            });
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void initPlayGame() throws URISyntaxException {
        try {
            playGameEndpoint = new PlayGameEndpoint(token);
            playGameEndpoint.onTokenCheckerMessageListener((BooleanMessage booleanMessage) -> {
                System.out.println("server > " + JSON.toJSONString(booleanMessage));
                if (!booleanMessage.isOk()) {
                    playGameEndpoint.sendTokenMessage();
                }
            });
            container.connectToServer(playGameEndpoint, new URI("ws://localhost:8080/blackjack/game/play"));
            playGameEndpoint.onWaitActionMessageListener((WaitMessage waitMessage) -> {
                System.out.println("server > " + JSON.toJSONString(waitMessage));
                TableInfo tableInfo = waitMessage.getTableInfo();
                int place = waitMessage.getPlace();
                Scanner scn = new Scanner(System.in);
                System.out.println("[action] you >");
                switch (waitMessage.getWaitType()) {
                    case ACTION_WAIT_BET:
                        int bet = scn.nextInt();
                        playGameEndpoint.sendActionMessage(tableInfo, place, bet);
                        break;
                    case ACTION_WAIT_HIT_OR_STAND:
                        String hitOrStand = scn.nextLine();
                        playGameEndpoint.sendActionMessage(tableInfo, place, hitOrStand);
                        break;
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void register(String name, String password) {
        initEndpoint.onRegisterListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
        });
        User user = new User(name, password);
        initEndpoint.sendRegisterMessage(user);
    }

    private void login(String name, String password) {
        initEndpoint.onLoginListener((TokenMessage tokenMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tokenMessage));
            token = tokenMessage.getToken();
            initLobby();
        });
        User user = new User(name, password);
        initEndpoint.sendLoginMessage(user);
    }

    private void logout() {
        initEndpoint.onLogoutListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
            if (playGameEndpoint != null) playGameEndpoint.close();
            if (watchGameEndpoint != null) watchGameEndpoint.close();
            if (lobbyEndpoint != null) lobbyEndpoint.close();
            initEndpoint.close();
            initInit();
        });
        initEndpoint.sendLogoutMessage();
    }

    private void getMyData() {
        lobbyEndpoint.onMyDataListener((UserMessage userMessage) -> {
            System.out.println("server > " + JSON.toJSONString(userMessage));
        });
        lobbyEndpoint.sendMyDataMessage();
    }

    private void getUserData(String name) {
        lobbyEndpoint.onUserDataListener((UserMessage userMessage) -> {
            System.out.println("server > " + JSON.toJSONString(userMessage));
        });
        User user = new User(name);
        lobbyEndpoint.sendUserDataMessage(user);
    }

    private void getTableList() {
        lobbyEndpoint.onTableListListener((TableListMessage tableListMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tableListMessage));
            tableList = tableListMessage.getTableList();
            try {
                initWatchGame();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
        lobbyEndpoint.sendTableListMessage();
    }

    private void entryTable(int table) throws URISyntaxException {
        lobbyEndpoint.close();
        watchGameEndpoint.onEntryListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
            try {
                initPlayGame();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
        TableInfo tableInfo = tableList.get(table);
        if (tableInfo != null) {
            watchGameEndpoint.sendEntryMessage(tableInfo);
        }
    }

    private void exitTable(int table) throws URISyntaxException {
        watchGameEndpoint.onExitListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
            initLobby();
            watchGameEndpoint.close();
        });
        TableInfo tableInfo = tableList.get(table);
        if (tableInfo != null) {
            watchGameEndpoint.sendExitMessage(tableInfo);
        }
    }

    private void sitTable(int table, int place) throws URISyntaxException {
        playGameEndpoint.onSitListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
        });
        TableInfo tableInfo = tableList.get(table);
        if (tableInfo != null) {
            playGameEndpoint.sendSitMessage(tableInfo, place);
        }
    }

    private void standTable(int table, int place) {
        playGameEndpoint.onStandListener((BooleanMessage booleanMessage) -> {
            System.out.println("server > " + JSON.toJSONString(booleanMessage));
            playGameEndpoint.close();
        });
        TableInfo tableInfo = tableList.get(table);
        if (tableInfo != null) {
            playGameEndpoint.sendStandMessage(tableInfo, place);
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException, DeploymentException {
        Scanner scn = new Scanner(System.in);
        Main client = new Main();
        while (true) {
            System.out.println("[command] you >");
            String[] command = scn.nextLine().split(" ");
            switch (command[0]) {
                case "register":
                    client.register(command[1], command[2]);
                    break;
                case "login":
                    client.login(command[1], command[2]);
                    break;
                case "logout":
                    client.logout();
                    break;
                case "me":
                    client.getMyData();
                    break;
                case "user":
                    client.getUserData(command[1]);
                    break;
                case "lobby":
                    client.getTableList();
                    break;
                case "entry":
                    client.entryTable(Integer.parseInt(command[1]));
                    break;
                case "exit":
                    client.exitTable(Integer.parseInt(command[1]));
                    break;
                case "sit":
                    client.sitTable(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    break;
                case "stand":
                    client.standTable(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    break;
            }
        }
    }

}

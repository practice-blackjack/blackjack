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
import java.util.Scanner;

public class Main {

    private WebSocketContainer container;

    private InitEndpoint initEndpoint;
    private LobbyEndpoint lobbyEndpoint;
    private WatchGameEndpoint watchGameEndpoint;
    private PlayGameEndpoint playGameEndpoint;

    private MessageFunction<StringMessage> tokenChecker = (StringMessage stringMessage) -> {
        System.out.println("server > " + JSON.toJSONString(stringMessage));
    };

    private long token;

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
            lobbyEndpoint.onTokenCheckerMessageListener(tokenChecker);
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
            watchGameEndpoint.onTokenCheckerMessageListener(tokenChecker);
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
            playGameEndpoint.onTokenCheckerMessageListener(tokenChecker);
            container.connectToServer(playGameEndpoint, new URI("ws://localhost:8080/blackjack/game/play"));
            playGameEndpoint.onWaitActionMessageListener((WaitMessage waitMessage) -> {
                System.out.println("server > " + JSON.toJSONString(waitMessage));
                TableInfo tableInfo = waitMessage.getTableInfo();
                int place = waitMessage.getPlace();
                Scanner scn = new Scanner(System.in);
                switch (waitMessage.getWaitType()) {
                    case "bet":
                        int bet = scn.nextInt();
                        playGameEndpoint.sendActionMessage(tableInfo, place, bet);
                        break;
                    case "hit_or_stand":
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
        initEndpoint.onRegisterListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
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
        initEndpoint.onLogoutListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
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
        });
        lobbyEndpoint.sendTableListMessage();
    }

    private void entryTable() throws URISyntaxException {
        initWatchGame();
        lobbyEndpoint.close();
        watchGameEndpoint.onEntryListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
        });
        TableInfo tableInfo = new TableInfo("", 0, 0, 0,0);
        watchGameEndpoint.sendEntryMessage(tableInfo);
    }

    private void exitTable() throws URISyntaxException {
        watchGameEndpoint.onExitListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
            initLobby();
            watchGameEndpoint.close();
        });
        TableInfo tableInfo = new TableInfo("", 0, 0, 0,0);
        watchGameEndpoint.sendExitMessage(tableInfo);
    }

    private void sitTable() throws URISyntaxException {
        initPlayGame();
        playGameEndpoint.onSitListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
        });
        TableInfo tableInfo = new TableInfo("", 0, 0, 0,0);
        int place = 1;
        playGameEndpoint.sendSitMessage(tableInfo, place);
    }

    private void standTable() {
        playGameEndpoint.onStandListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
            playGameEndpoint.close();
        });
        TableInfo tableInfo = new TableInfo("", 0, 0, 0,0);
        playGameEndpoint.sendStandMessage(tableInfo);
    }

    public static void main(String[] args) throws URISyntaxException, IOException, DeploymentException {
        Scanner scn = new Scanner(System.in);
        Main client = new Main();
        while (true) {
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
                    client.entryTable();
                    break;
                case "exit":
                    client.exitTable();
                    break;
                case "sit":
                    client.sitTable();
                    break;
                case "stand":
                    client.standTable();
                    break;
            }
        }
    }

}

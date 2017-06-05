package nulp.pist21.blackjack.client;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.client.endpoint.*;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.Table;
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

    private RegisterEndpoint registerEndpoint;
    private LoginEndpoint loginEndpoint;
    private UserDataEndpoint userDataEndpoint;
    private LobbyEndpoint lobbyEndpoint;
    private TableEndpoint tableEndpoint;
    private GameActionEndpoint gameActionEndpoint;

    private long token;

    public Main() {
        container = ContainerProvider.getWebSocketContainer();
    }

    private void register(String name, String password) {
        registerEndpoint = new RegisterEndpoint();
        try {
            container.connectToServer(registerEndpoint, new URI("ws://localhost:8080/app/register"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        registerEndpoint.onMessageListener((StringMessage stringMessage) -> {
            System.out.println("server > " + stringMessage.getMessage());
            registerEndpoint.close();
        });
        User user = new User(name, password);
        UserMessage userMessage = new UserMessage("register user", user);
        registerEndpoint.sendMessage(userMessage);
    }

    private void login(String name, String password) {
        if (loginEndpoint != null) loginEndpoint.close();
        loginEndpoint = new LoginEndpoint();
        try {
            container.connectToServer(loginEndpoint, new URI("ws://localhost:8080/app/login"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        loginEndpoint.onMessageListener((TokenMessage tokenMessage) -> {
            System.out.println("server > " + tokenMessage.getMessage() + " " + tokenMessage.getToken());
            token = tokenMessage.getToken();
        });
        User user = new User(name, password);
        UserMessage userMessage = new UserMessage("login user", user);
        loginEndpoint.sendMessage(userMessage);
    }

    private void exit() {
        if (loginEndpoint != null) loginEndpoint.close();
        loginEndpoint = new LoginEndpoint();
        try {
            container.connectToServer(loginEndpoint, new URI("ws://localhost:8080/app/login"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void getMyData() {
        userDataEndpoint = new UserDataEndpoint(new TokenMessage("", token));
        try {
            container.connectToServer(userDataEndpoint, new URI("ws://localhost:8080/app/userdata"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        userDataEndpoint.onMessageListener((UserMessage userMessage) -> {
            System.out.println("server > " + JSON.toJSONString(userMessage));
            userDataEndpoint.close();
        });
        UserMessage userMessage = new UserMessage("get my data", new User(""));
        userDataEndpoint.sendMessage(userMessage);
    }

    private void getUserData(String name) {
        userDataEndpoint = new UserDataEndpoint(new TokenMessage("", token));
        try {
            container.connectToServer(userDataEndpoint, new URI("ws://localhost:8080/app/userdata"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        userDataEndpoint.onMessageListener((UserMessage userMessage) -> {
            System.out.println("server > " + JSON.toJSONString(userMessage));
            userDataEndpoint.close();
        });
        UserMessage userMessage = new UserMessage("get user data", new User(name));
        userDataEndpoint.sendMessage(userMessage);
    }

    private void getTableList() {
        lobbyEndpoint = new LobbyEndpoint(new TokenMessage("", token));
        try {
            container.connectToServer(lobbyEndpoint, new URI("ws://localhost:8080/app/lobby"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        lobbyEndpoint.onMessageListener((TableListMessage tableListMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tableListMessage));
            lobbyEndpoint.close();
        });
        StringMessage stringMessage = new StringMessage("get table list");
        lobbyEndpoint.sendMessage(stringMessage);
    }

    private void getTable() {
        tableEndpoint = new TableEndpoint(new TokenMessage("", token));
        try {
            container.connectToServer(tableEndpoint, new URI("ws://localhost:8080/app/table"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        tableEndpoint.onMessageListener((TableFullInfoMessage tableFullInfoMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tableFullInfoMessage));
            tableEndpoint.close();
        });
        TableSmallInfoMessage tableSmallInfoMessage = new TableSmallInfoMessage("get table", new TableInfo("name", 10, 4, 20, 50));
        tableEndpoint.sendMessage(tableSmallInfoMessage);
        action();
    }

    private void action() {
        gameActionEndpoint = new GameActionEndpoint(new TokenMessage("", token));
        try {
            container.connectToServer(gameActionEndpoint, new URI("ws://localhost:8080/app/game"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        Scanner scn = new Scanner(System.in);
        gameActionEndpoint.onMessageListener((WaitMessage waitMessage) -> {
            System.out.println("server > " + JSON.toJSONString(waitMessage));
            Table table = waitMessage.getTable();
            String[] command = scn.nextLine().split(" ");
            if (command[0].equals("game")) {
                String action = command[1];
                int bet = Integer.parseInt(command[2]);
                GameActionMessage gameActionMessage = new GameActionMessage("user game action", table, action, bet);
                gameActionEndpoint.sendMessage(gameActionMessage);
            }
        });
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        Main client = new Main();
        while (true) {
            String[] command = scn.nextLine().split(" ");
            switch (command[0]) {
                case "reg":
                    client.register(command[1], command[2]);
                    break;
                case "log":
                    client.login(command[1], command[2]);
                    break;
                case "exit":
                    client.exit();
                    break;
                case "me":
                    client.getMyData();
                    break;
                case "user":
                    client.getUserData(command[1]);
                    break;
                case "lobby":
                    client.getTableList(/*todo: filter*/);
                    break;
                case "table":
                    client.getTable(/*todo: filter*/);
                    break;
            }
        }
    }

}

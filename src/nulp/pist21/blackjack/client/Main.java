package nulp.pist21.blackjack.client;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.client.endpoint.LobbyEndpoint;
import nulp.pist21.blackjack.client.endpoint.UserDataEndpoint;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.TableListMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.message.UserMessage;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.client.endpoint.RegisterEndpoint;
import nulp.pist21.blackjack.client.endpoint.LoginEndpoint;

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

    public void getMyData() {
        userDataEndpoint = new UserDataEndpoint(new TokenMessage("", token));
        try {
            container.connectToServer(userDataEndpoint, new URI("ws://localhost:8080/app/userdata"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        userDataEndpoint.onMessageListener((UserMessage userMessage) -> {
            User user = userMessage.getUser();
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
            User user = userMessage.getUser();
            System.out.println("server > " + JSON.toJSONString(userMessage));
            userDataEndpoint.close();
        });
        UserMessage userMessage = new UserMessage("get user data", new User(name));
        userDataEndpoint.sendMessage(userMessage);
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
                case "lobby":
                    client.getTableList(/*todo: filter*/);
                    break;
                case "user":
                    client.getUserData(command[1]);
                    break;
            }
        }
    }

}

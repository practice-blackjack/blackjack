package nulp.pist21.blackjack.client;

import com.alibaba.fastjson.JSON;
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

    private RegisterEndpoint registerEndpoint;
    private LoginEndpoint loginEndpoint;

    public Main() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        registerEndpoint = new RegisterEndpoint();
        loginEndpoint = new LoginEndpoint();
        try {
            container.connectToServer(registerEndpoint, new URI("ws://localhost:8080/app/register"));
            container.connectToServer(loginEndpoint, new URI("ws://localhost:8080/app/login"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void register(String name, String password) {
        User user = new User(name, password);
        String jsonUser = JSON.toJSONString(user);
        registerEndpoint.sendMessage(jsonUser);
    }

    private void login(String name, String password) {
        User user = new User(name, password);
        String jsonUser = JSON.toJSONString(user);
        loginEndpoint.sendMessage(jsonUser);
    }

    public void getMyData() {

    }

    private void getUserData(String name) {
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
                case "me":
                    client.getMyData();
                    break;
                case "user":
                    client.getUserData(command[1]);
                    break;
            }
        }
    }
}

package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.User;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class GameActionEndpointTest {

    private TokenMessage message;
    private WebSocketContainer container;

    @SuppressWarnings("Duplicates")
    @Before
    public void before() throws URISyntaxException, IOException, DeploymentException {
        User user = new User("user", "password");
        container = ContainerProvider.getWebSocketContainer();
        RegisterEndpoint registerEndpoint = new RegisterEndpoint();
        container.connectToServer(registerEndpoint, new URI("ws://localhost:8080/app/register"));
        registerEndpoint.onMessageListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
            registerEndpoint.close();
        });
        registerEndpoint.sendMessage(new UserMessage("register user", user));
        LoginEndpoint loginEndpoint = new LoginEndpoint();
        container.connectToServer(loginEndpoint, new URI("ws://localhost:8080/app/login"));
        loginEndpoint.onMessageListener((TokenMessage tokenMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tokenMessage));
            message = tokenMessage;
        });
        loginEndpoint.sendMessage(new UserMessage("login user", user));
    }

    @Test
    public void gameActionBet() {
        GameActionEndpoint gameActionEndpoint = new GameActionEndpoint(message);
        try {
            container.connectToServer(gameActionEndpoint, new URI("ws://localhost:8080/app/game"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        gameActionEndpoint.onMessageListener((TableMessage tableMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tableMessage));
            gameActionEndpoint.close();
        });
        GameActionMessage gameActionMessage = new GameActionMessage("user game action", GameActionMessage.BET, 10);
        gameActionEndpoint.sendMessage(gameActionMessage);
    }

    @Test
    public void gameActionStand() {
        GameActionEndpoint gameActionEndpoint = new GameActionEndpoint(message);
        try {
            container.connectToServer(gameActionEndpoint, new URI("ws://localhost:8080/app/game"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        gameActionEndpoint.onMessageListener((TableMessage tableMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tableMessage));
            gameActionEndpoint.close();
        });
        GameActionMessage gameActionMessage = new GameActionMessage("user game action", GameActionMessage.STAND, 0);
        gameActionEndpoint.sendMessage(gameActionMessage);
    }

    @Test
    public void gameActionHit() {
        GameActionEndpoint gameActionEndpoint = new GameActionEndpoint(message);
        try {
            container.connectToServer(gameActionEndpoint, new URI("ws://localhost:8080/app/game"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        gameActionEndpoint.onMessageListener((TableMessage tableMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tableMessage));
            gameActionEndpoint.close();
        });
        GameActionMessage gameActionMessage = new GameActionMessage("user game action", GameActionMessage.HIT, 0);
        gameActionEndpoint.sendMessage(gameActionMessage);
    }

}
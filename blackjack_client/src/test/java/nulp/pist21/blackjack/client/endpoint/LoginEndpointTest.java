package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.message.UserMessage;
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

public class LoginEndpointTest {

    private WebSocketContainer container;
    private LoginEndpoint loginEndpoint;

    @Before
    public void before() throws URISyntaxException, IOException, DeploymentException {
        container = ContainerProvider.getWebSocketContainer();
        loginEndpoint = new LoginEndpoint();
        container.connectToServer(loginEndpoint, new URI("ws://localhost:8080/app/login"));
    }

    @Test
    public void login() throws Exception {
        loginEndpoint.onMessageListener((TokenMessage tokenMessage) -> {
            System.out.println("server > " + JSON.toJSONString(tokenMessage));
            loginEndpoint.close();
        });
        User user = new User("user", "password");
        UserMessage userMessage = new UserMessage("login user", user);
        loginEndpoint.sendMessage(userMessage);
    }

    @Test
    public void exit() {
        if (loginEndpoint != null) loginEndpoint.close();
        loginEndpoint = new LoginEndpoint();
        try {
            container.connectToServer(loginEndpoint, new URI("ws://localhost:8080/app/login"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
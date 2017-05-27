package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.UserMessage;
import nulp.pist21.blackjack.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

import static org.junit.Assert.*;

public class RegisterEndpointTest {

    @Test
    public void register() throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        RegisterEndpoint registerEndpoint = new RegisterEndpoint();
        container.connectToServer(registerEndpoint, new URI("ws://localhost:8080/app/register"));
        registerEndpoint.onMessageListener((StringMessage stringMessage) -> {
            System.out.println("server > " + JSON.toJSONString(stringMessage));
            registerEndpoint.close();
        });
        User user = new User("user", "password");
        UserMessage userMessage = new UserMessage("register user", user);
        registerEndpoint.sendMessage(userMessage);
    }

}
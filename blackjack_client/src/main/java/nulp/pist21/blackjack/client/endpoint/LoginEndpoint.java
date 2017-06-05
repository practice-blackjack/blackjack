package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.MessageFunction;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.message.UserMessage;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class LoginEndpoint {

    private Session session;
    private MessageFunction<TokenMessage> function;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        TokenMessage tokenMessage = JSON.parseObject(message, TokenMessage.class);
        if (function != null) {
            function.apply(tokenMessage);
        }
    }

    public void onMessageListener(MessageFunction<TokenMessage> function) {
        this.function = function;
    }

    public void sendMessage(UserMessage message) {
        String json = JSON.toJSONString(message);
        session.getAsyncRemote().sendText(json);
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
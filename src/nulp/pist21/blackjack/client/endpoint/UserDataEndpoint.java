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
public class UserDataEndpoint {

    private Session session;
    private MessageFunction<UserMessage> function;
    private TokenChecker tokenChecker;

    public UserDataEndpoint(TokenMessage message) {
        tokenChecker = new TokenChecker(message);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        tokenChecker.send(session);
    }

    @OnMessage
    public void onMessage(String message) {
        if (tokenChecker.receive(message)) return;
        UserMessage userMessage = JSON.parseObject(message, UserMessage.class);
        if (function != null) {
            function.apply(userMessage);
        }
    }

    public void onMessageListener(MessageFunction<UserMessage> function) {
        this.function = function;
    }

    public void sendMessage(UserMessage message) {
        if (session != null && session.isOpen()) {
            String json = JSON.toJSONString(message);
            session.getAsyncRemote().sendText(json);
        }
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

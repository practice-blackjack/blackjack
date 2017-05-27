package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class GameActionEndpoint {

    private Session session;
    private MessageFunction<WaitMessage> function;
    private TokenChecker tokenChecker;

    public GameActionEndpoint(TokenMessage message) {
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
        WaitMessage waitMessage = JSON.parseObject(message, WaitMessage.class);
        if (function != null) {
            function.apply(waitMessage);
        }
    }

    public void onMessageListener(MessageFunction<WaitMessage> function) {
        this.function = function;
    }

    public void onTokenCheckerMessageListener(MessageFunction<StringMessage> function) {
        tokenChecker.onMessageListener(function);
    }

    public void sendMessage(GameActionMessage message) {
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

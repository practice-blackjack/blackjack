package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class CardEndpoint {

    private Session session;
    private MessageFunction<CardMessage> function;
    private TokenChecker tokenChecker;

    public CardEndpoint(TokenMessage message) {
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
        CardMessage waitMessage = JSON.parseObject(message, CardMessage.class);
        if (function != null) {
            function.apply(waitMessage);
        }
    }

    public void onMessageListener(MessageFunction<CardMessage> function) {
        this.function = function;
    }

    public void onTokenCheckerMessageListener(MessageFunction<StringMessage> function) {
        tokenChecker.onMessageListener(function);
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
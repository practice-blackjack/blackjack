package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class TableEndpoint {

    private Session session;
    private MessageFunction<TableFullInfoMessage> function;
    private TokenChecker tokenChecker;

    public TableEndpoint(TokenMessage message) {
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
        TableFullInfoMessage tableFullInfoMessage = JSON.parseObject(message, TableFullInfoMessage.class);
        if (function != null) {
            function.apply(tableFullInfoMessage);
        }
    }

    public void onMessageListener(MessageFunction<TableFullInfoMessage> function) {
        this.function = function;
    }

    public void onTokenCheckerMessageListener(MessageFunction<StringMessage> function) {
        tokenChecker.onMessageListener(function);
    }

    public void sendMessage(TableSmallInfoMessage message) {
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

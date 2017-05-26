package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class LobbyEndpoint {

    private Session session;
    private MessageFunction<TableListMessage> function;
    private boolean init = true;
    private TokenMessage initMessage;

    public LobbyEndpoint(TokenMessage message) {
        initMessage = message;
    }

    public void sendInitMessage() {
        String json = JSON.toJSONString(initMessage);
        session.getAsyncRemote().sendText(json);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sendInitMessage();
    }

    @OnMessage
    public void onMessage(String message) {
        if (init) {
            StringMessage stringMessage = JSON.parseObject(message, StringMessage.class);
            if (stringMessage.getMessage().equals("ok")) {
                init = false;
            }
            return;
        }
        TableListMessage tableListMessage = JSON.parseObject(message, TableListMessage.class);
        if (function != null) {
            function.apply(tableListMessage);
        }
    }

    public void onMessageListener(MessageFunction<TableListMessage> function) {
        this.function = function;
    }

    public void sendMessage(StringMessage message) {
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

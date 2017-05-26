package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/lobby")
public class LobbyEndpoint {

    private Session session;
    private MessageFunction<StringMessage> function;
    private final ProgramData programData = ProgramData.get();
    private TokenChecker tokenChecker;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("lobby open");
        this.session = session;
        tokenChecker = new TokenChecker(session);
    }

    @OnClose
    public void onClose() {
        System.out.println("lobby close");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("lobby message " + message);
        if (tokenChecker.receive(message)) return;
        StringMessage userMessage = JSON.parseObject(message, StringMessage.class);
        if (function != null) {
            function.apply(userMessage);
        }
        if (this.session != null && this.session.isOpen()) {
            TableListMessage tableListMessage;
            tableListMessage = new TableListMessage("table list");
            sendMessage(tableListMessage);
        }
    }

    public void onMessageListener(MessageFunction<StringMessage> function) {
        this.function = function;
    }

    public void sendMessage(TableListMessage message) {
        String json = JSON.toJSONString(message);
        System.out.println("lobby send " + json);
        session.getAsyncRemote().sendText(json);
    }
}

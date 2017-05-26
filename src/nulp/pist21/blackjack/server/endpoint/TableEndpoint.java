package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.Table;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/table")
public class TableEndpoint {

    private Session session;
    private MessageFunction<SelectTableMessage> function;
    private final ProgramData programData = ProgramData.get();
    private TokenChecker tokenChecker;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("table open");
        this.session = session;
        tokenChecker = new TokenChecker(session);
    }

    @OnClose
    public void onClose() {
        System.out.println("table close");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("table message " + message);
        if (tokenChecker.receive(message)) return;
        SelectTableMessage selectTableMessage = JSON.parseObject(message, SelectTableMessage.class);
        if (function != null) {
            function.apply(selectTableMessage);
        }

        //todo:
        Table table = new Table();

        if (this.session != null && this.session.isOpen()) {
            TableMessage tableMessage;
            tableMessage = new TableMessage("table data", table);
            sendMessage(tableMessage);
        }
    }

    public void onMessageListener(MessageFunction<SelectTableMessage> function) {
        this.function = function;
    }

    public void sendMessage(TableMessage message) {
        String json = JSON.toJSONString(message);
        System.out.println("table send " + json);
        session.getAsyncRemote().sendText(json);
    }

}

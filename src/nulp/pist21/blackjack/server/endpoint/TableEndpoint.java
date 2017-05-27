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
    private MessageFunction<TableSmallInfoMessage> function;
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
        TableSmallInfoMessage tableSmallInfoMessage = JSON.parseObject(message, TableSmallInfoMessage.class);
        if (function != null) {
            function.apply(tableSmallInfoMessage);
        }

        //todo:
        Table table = new Table();

        if (this.session != null && this.session.isOpen()) {
            TableFullInfoMessage tableFullInfoMessage;
            tableFullInfoMessage = new TableFullInfoMessage("table data", table);
            sendMessage(tableFullInfoMessage);
        }
    }

    public void onMessageListener(MessageFunction<TableSmallInfoMessage> function) {
        this.function = function;
    }

    public void onTokenCheckerMessageListener(MessageFunction<TokenMessage> function) {
        tokenChecker.onMessageListener(function);
    }

    public void sendMessage(TableFullInfoMessage message) {
        String json = JSON.toJSONString(message);
        System.out.println("table send " + json);
        session.getAsyncRemote().sendText(json);
    }

}

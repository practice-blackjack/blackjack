package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.Table;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.List;

@ServerEndpoint("/game")
public class GameActionEndpoint {

    private Session session;
    private MessageFunction<GameActionMessage> function;
    private final ProgramData programData = ProgramData.get();
    private TokenChecker tokenChecker;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("game open");
        this.session = session;
        tokenChecker = new TokenChecker(session);
    }

    @OnClose
    public void onClose() {
        System.out.println("game close");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("game message " + message);
        if (tokenChecker.receive(message)) return;
        GameActionMessage gameActionMessage = JSON.parseObject(message, GameActionMessage.class);
        if (function != null) {
            function.apply(gameActionMessage);
        }
        if (this.session != null && this.session.isOpen()) {
            TableMessage tableMessage = new TableMessage("table state", new Table());
            sendMessage(tableMessage);
        }
    }

    public void onMessageListener(MessageFunction<GameActionMessage> function) {
        this.function = function;
    }

    public void onTokenCheckerMessageListener(MessageFunction<TokenMessage> function) {
        tokenChecker.onMessageListener(function);
    }

    public void sendMessage(TableMessage message) {
        String json = JSON.toJSONString(message);
        System.out.println("game send " + json);
        session.getAsyncRemote().sendText(json);
    }

}
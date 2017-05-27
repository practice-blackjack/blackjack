package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class WatchGameEndpoint {

    private MessageFunction<StringMessage> tokenCheckerFunction;
    private MessageFunction<TableFullInfoMessage> updateFunction;
    private MessageFunction<UserActionMessage> userActionFunction;
    private MessageFunction<StringMessage> entryFunction;
    private MessageFunction<StringMessage> exitFunction;

    private Session session;
    private final long token;

    public WatchGameEndpoint(long token) {
        this.token = token;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "token":
                if (tokenCheckerFunction != null) tokenCheckerFunction.apply(JSON.parseObject(message, StringMessage.class));
                break;
            case "update":
                if (updateFunction != null) updateFunction.apply(JSON.parseObject(message, TableFullInfoMessage.class));
                break;
            case "user_action":
                if (userActionFunction != null) userActionFunction.apply(JSON.parseObject(message, UserActionMessage.class));
                break;
            case "entry":
                if (entryFunction != null) entryFunction.apply(JSON.parseObject(message, StringMessage.class));
                break;
            case "exit":
                if (exitFunction != null) exitFunction.apply(JSON.parseObject(message, StringMessage.class));
                break;
        }
    }

    public void onTokenCheckerMessageListener(MessageFunction<StringMessage> function) {
        this.tokenCheckerFunction = function;
    }

    public void onUpdateMessageListener(MessageFunction<TableFullInfoMessage> function) {
        this.updateFunction = function;
    }

    public void onUserActionMessageListener(MessageFunction<UserActionMessage> function) {
        this.userActionFunction = function;
    }

    public void onEntryListener(MessageFunction<StringMessage> function) {
        this.entryFunction = function;
    }

    public void onExitListener(MessageFunction<StringMessage> function) {
        this.exitFunction = function;
    }

    public void sendTokenMessage() {
        sendMessage(new TokenMessage("token", token));
    }

    public void sendEntryMessage(TableInfo tableInfo) {
        sendMessage(new TableSmallInfoMessage("entry", tableInfo));
    }

    public void sendExitMessage(TableInfo tableInfo) {
        sendMessage(new TableSmallInfoMessage("exit", tableInfo));
    }

    private void sendMessage(Message message) {
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

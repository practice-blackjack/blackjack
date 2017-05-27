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

@ServerEndpoint("/game/watch")
public class WatchGameEndpoint {

    private MessageFunction<TokenMessage> tokenCheckerFunction;
    private MessageFunction<TableSmallInfoMessage> entryFunction;
    private MessageFunction<TableSmallInfoMessage> exitFunction;

    private Session session;
    private final ProgramData programData = ProgramData.get();
    private long token = -1;
    private boolean login = false;

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("game_watch open");
        this.session = session;
        programData.watchGameManager.add(this);
    }

    @OnClose
    public void onClose() {
        System.out.println("game_watch close");
        programData.watchGameManager.remove(this);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("game_watch message " + message);
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "token":
                if (tokenCheckerFunction != null) tokenCheckerFunction.apply(JSON.parseObject(message, TokenMessage.class));
                break;
            case "entry":
                if (entryFunction != null) entryFunction.apply(JSON.parseObject(message, TableSmallInfoMessage.class));
                break;
            case "exit":
                if (exitFunction != null) exitFunction.apply(JSON.parseObject(message, TableSmallInfoMessage.class));
                break;
        }
    }

    public void onTokenCheckerMessageListener(MessageFunction<TokenMessage> function) {
        this.tokenCheckerFunction = function;
    }

    public void onEntryListener(MessageFunction<TableSmallInfoMessage> function) {
        this.entryFunction = function;
    }

    public void onExitListener(MessageFunction<TableSmallInfoMessage> function) {
        this.exitFunction = function;
    }

    public void sendTokenMessage(String message) {
        sendMessage(new StringMessage("token", message));
    }

    public void sendUpdateMessage(Table table) {
        sendMessage(new TableFullInfoMessage("update", table));
    }

    public void sendUserActionMessage(TableInfo tableInfo, int place, String action) {
        sendMessage(new UserActionMessage("user_action", tableInfo, place, action));
    }

    public void sendEntryMessage(String message) {
        sendMessage(new StringMessage("entry", message));
    }

    public void sendExitMessage(String message) {
        sendMessage(new StringMessage("exit", message));
    }

    public void sendResultMessage(String message) {
        sendMessage(new ResultMessage("result"));
    }

    private void sendMessage(Message message) {
        String json = JSON.toJSONString(message);
        System.out.println("game_watch send " + json);
        session.getAsyncRemote().sendText(json);
    }

}

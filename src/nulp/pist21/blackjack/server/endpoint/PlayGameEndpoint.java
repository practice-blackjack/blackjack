package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/game/play")
public class PlayGameEndpoint {

    private MessageFunction<UserActionMessage> userActionFunction;
    private MessageFunction<TableSmallInfoMessage> sitFunction;
    private MessageFunction<TableSmallInfoMessage> stayFunction;
    private MessageFunction<TokenMessage> tokenCheckerFunction;

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
        System.out.println("game_play open");
        this.session = session;
        programData.playGameManager.add(this);
    }

    @OnClose
    public void onClose() {
        System.out.println("game_play close");
        programData.playGameManager.remove(this);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("game_play message " + message);
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "token":
                if (tokenCheckerFunction != null) tokenCheckerFunction.apply(JSON.parseObject(message, TokenMessage.class));
                break;
            case "user_action":
                if (userActionFunction != null) userActionFunction.apply(JSON.parseObject(message, UserActionMessage.class));
                break;
            case "sit":
                if (sitFunction != null) sitFunction.apply(JSON.parseObject(message, TableSmallInfoMessage.class));
                break;
            case "stay":
                if (stayFunction != null) stayFunction.apply(JSON.parseObject(message, TableSmallInfoMessage.class));
                break;
        }
    }

    public void onTokenCheckerMessageListener(MessageFunction<TokenMessage> function) {
        this.tokenCheckerFunction = function;
    }

    public void onUserActionMessageListener(MessageFunction<UserActionMessage> function) {
        this.userActionFunction = function;
    }

    public void onSitListener(MessageFunction<TableSmallInfoMessage> function) {
        this.sitFunction = function;
    }

    public void onStayListener(MessageFunction<TableSmallInfoMessage> function) {
        this.stayFunction = function;
    }

    public void sendTokenMessage(String message) {
        sendMessage(new StringMessage("token", message));
    }

    public void sendWaitMessage(TableInfo tableInfo, int place) {
        sendMessage(new WaitMessage("wait", tableInfo, place));
    }

    public void sendSitMessage(String message) {
        sendMessage(new StringMessage("sit", message));
    }

    public void sendStayMessage(String message) {
        sendMessage(new StringMessage("stay", message));
    }

    private void sendMessage(Message message) {
        String json = JSON.toJSONString(message);
        System.out.println("game_play send " + json);
        session.getAsyncRemote().sendText(json);
    }

}

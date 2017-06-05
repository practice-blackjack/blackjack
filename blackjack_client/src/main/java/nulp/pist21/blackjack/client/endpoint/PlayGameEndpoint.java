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
public class PlayGameEndpoint {

    private MessageFunction<WaitMessage> waitActionFunction;
    private MessageFunction<StringMessage> sitFunction;
    private MessageFunction<StringMessage> stayFunction;
    private MessageFunction<StringMessage> tokenCheckerFunction;

    private Session session;
    private final long token;

    public PlayGameEndpoint(long token) {
        this.token = token;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sendTokenMessage();
    }

    @OnMessage
    public void onMessage(String message) {
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "token":
                if (tokenCheckerFunction != null) tokenCheckerFunction.apply(JSON.parseObject(message, StringMessage.class));
                break;
            case "wait":
                if (waitActionFunction != null) waitActionFunction.apply(JSON.parseObject(message, WaitMessage.class));
                break;
            case "sit":
                if (sitFunction != null) sitFunction.apply(JSON.parseObject(message, StringMessage.class));
                break;
            case "stay":
                if (stayFunction != null) stayFunction.apply(JSON.parseObject(message, StringMessage.class));
                break;
        }
    }

    public void onTokenCheckerMessageListener(MessageFunction<StringMessage> function) {
        this.tokenCheckerFunction = function;
    }

    public void onWaitActionMessageListener(MessageFunction<WaitMessage> function) {
        this.waitActionFunction = function;
    }

    public void onSitListener(MessageFunction<StringMessage> function) {
        this.sitFunction = function;
    }

    public void onStayListener(MessageFunction<StringMessage> function) {
        this.stayFunction = function;
    }

    public void sendTokenMessage() {
        sendMessage(new TokenMessage("token", token));
    }

    public void sendActionMessage(TableInfo tableInfo, int place, int bet) {
        sendMessage(new UserActionMessage("user_action", tableInfo, place, UserActionMessage.BET, bet));
    }

    public void sendActionMessage(TableInfo tableInfo, int place, String hitOrStand) {
        sendMessage(new UserActionMessage("user_action", tableInfo, place, hitOrStand));
    }

    public void sendSitMessage(TableInfo tableInfo, int place) {
        sendMessage(new TableSmallInfoMessage("sit", tableInfo, place));
    }

    public void sendStayMessage(TableInfo tableInfo) {
        sendMessage(new TableSmallInfoMessage("stay", tableInfo));
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

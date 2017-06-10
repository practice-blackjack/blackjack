package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

import static nulp.pist21.blackjack.message.MessageConstant.*;

@ClientEndpoint
public class PlayGameEndpoint {

    private MessageFunction<WaitMessage> waitActionFunction;
    private MessageFunction<BooleanMessage> sitFunction;
    private MessageFunction<BooleanMessage> standFunction;
    private MessageFunction<BooleanMessage> tokenCheckerFunction;

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
            case TYPE_TOKEN:
                if (tokenCheckerFunction != null) tokenCheckerFunction.apply(JSON.parseObject(message, BooleanMessage.class));
                break;
            case TYPE_WAIT:
                if (waitActionFunction != null) waitActionFunction.apply(JSON.parseObject(message, WaitMessage.class));
                break;
            case TYPE_SIT:
                if (sitFunction != null) sitFunction.apply(JSON.parseObject(message, BooleanMessage.class));
                break;
            case TYPE_STAND:
                if (standFunction != null) standFunction.apply(JSON.parseObject(message, BooleanMessage.class));
                break;
        }
    }

    public void onTokenCheckerMessageListener(MessageFunction<BooleanMessage> function) {
        this.tokenCheckerFunction = function;
    }

    public void onWaitActionMessageListener(MessageFunction<WaitMessage> function) {
        this.waitActionFunction = function;
    }

    public void onSitListener(MessageFunction<BooleanMessage> function) {
        this.sitFunction = function;
    }

    public void onStandListener(MessageFunction<BooleanMessage> function) {
        this.standFunction = function;
    }

    public void sendTokenMessage() {
        sendMessage(new TokenMessage(TYPE_TOKEN, token));
    }

    public void sendActionMessage(TableInfo tableInfo, int place, int bet) {
        sendMessage(new UserActionMessage(TYPE_USER_ACTION, tableInfo, place, MessageConstant.ACTION_BET, bet));
    }

    public void sendActionMessage(TableInfo tableInfo, int place, String hitOrStand) {
        sendMessage(new UserActionMessage(TYPE_USER_ACTION, tableInfo, place, hitOrStand));
    }

    public void sendSitMessage(TableInfo tableInfo, int place) {
        sendMessage(new TableSmallInfoMessage(TYPE_SIT, tableInfo, place));
    }

    public void sendStandMessage(TableInfo tableInfo, int place) {
        sendMessage(new TableSmallInfoMessage(TYPE_STAND, tableInfo, place));
    }

    private void sendMessage(Message message) {
        try {
            if (session != null && session.isOpen()) {
                String json = JSON.toJSONString(message);
                session.getBasicRemote().sendText(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen() {
        return session.isOpen();
    }

}

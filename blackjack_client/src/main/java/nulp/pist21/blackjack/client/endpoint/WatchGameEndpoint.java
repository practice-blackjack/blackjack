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
public class WatchGameEndpoint {

    private MessageFunction<BooleanMessage> tokenCheckerFunction;
    private MessageFunction<TableFullInfoMessage> updateFunction;
    private MessageFunction<UserActionMessage> userActionFunction;
    private MessageFunction<BooleanMessage> entryFunction;
    private MessageFunction<BooleanMessage> exitFunction;
    private MessageFunction<ResultMessage> resultFunction;

    private Session session;
    private final long token;

    public WatchGameEndpoint(long token) {
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
            case TYPE_UPDATE:
                if (updateFunction != null) updateFunction.apply(JSON.parseObject(message, TableFullInfoMessage.class));
                break;
            case TYPE_USER_ACTION:
                if (userActionFunction != null) userActionFunction.apply(JSON.parseObject(message, UserActionMessage.class));
                break;
            case TYPE_ENTRY:
                if (entryFunction != null) entryFunction.apply(JSON.parseObject(message, BooleanMessage.class));
                break;
            case TYPE_EXIT:
                if (exitFunction != null) exitFunction.apply(JSON.parseObject(message, BooleanMessage.class));
                break;
            case TYPE_RESULT:
                if (resultFunction != null) resultFunction.apply(JSON.parseObject(message, ResultMessage.class));
                break;
        }
    }

    public void onTokenCheckerMessageListener(MessageFunction<BooleanMessage> function) {
        this.tokenCheckerFunction = function;
    }

    public void onUpdateMessageListener(MessageFunction<TableFullInfoMessage> function) {
        this.updateFunction = function;
    }

    public void onUserActionMessageListener(MessageFunction<UserActionMessage> function) {
        this.userActionFunction = function;
    }

    public void onEntryListener(MessageFunction<BooleanMessage> function) {
        this.entryFunction = function;
    }

    public void onExitListener(MessageFunction<BooleanMessage> function) {
        this.exitFunction = function;
    }

    public void onResultListener(MessageFunction<ResultMessage> function) {
        this.resultFunction = function;
    }

    public void sendTokenMessage() {
        sendMessage(new TokenMessage(TYPE_TOKEN, token));
    }

    public void sendEntryMessage(TableInfo tableInfo) {
        sendMessage(new TableSmallInfoMessage(TYPE_ENTRY, tableInfo));
    }

    public void sendExitMessage(TableInfo tableInfo) {
        sendMessage(new TableSmallInfoMessage(TYPE_EXIT, tableInfo));
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

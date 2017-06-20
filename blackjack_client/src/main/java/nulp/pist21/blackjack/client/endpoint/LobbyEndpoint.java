package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.User;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

import static nulp.pist21.blackjack.message.MessageConstant.*;

@ClientEndpoint
public class LobbyEndpoint {

    private MessageFunction<TableListMessage> updateFunction;
    private MessageFunction<UserMessage> myDataFunction;
    private MessageFunction<UserMessage> userDataFunction;
    private MessageFunction<TableListMessage> tableListFunction;
    private MessageFunction<BooleanMessage> tokenCheckerFunction;

    private Session session;
    private final long token;

    public LobbyEndpoint(long token) {
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
                if (updateFunction != null) updateFunction.apply(JSON.parseObject(message, TableListMessage.class));
                break;
            case TYPE_MY_DATA:
                if (myDataFunction != null) myDataFunction.apply(JSON.parseObject(message, UserMessage.class));
                break;
            case TYPE_USER_DATA:
                if (userDataFunction != null) userDataFunction.apply(JSON.parseObject(message, UserMessage.class));
                break;
            case TYPE_TABLE_LIST:
                if (tableListFunction != null) tableListFunction.apply(JSON.parseObject(message, TableListMessage.class));
                break;
        }
    }

    public void onTokenCheckerMessageListener(MessageFunction<BooleanMessage> function) {
        this.tokenCheckerFunction = function;
    }

    public void onUpdateMessageListener(MessageFunction<TableListMessage> function) {
        this.updateFunction = function;
    }

    public void onMyDataListener(MessageFunction<UserMessage> function) {
        this.myDataFunction = function;
    }

    public void onUserDataListener(MessageFunction<UserMessage> function) {
        this.userDataFunction = function;
    }

    public void onTableListListener(MessageFunction<TableListMessage> function) {
        this.tableListFunction = function;
    }

    public void sendTokenMessage() {
        sendMessage(new TokenMessage(TYPE_TOKEN, token));
    }

    public void sendMyDataMessage() {
        sendMessage(new Message(TYPE_MY_DATA));
    }

    public void sendUserDataMessage(User user) {
        sendMessage(new UserMessage(TYPE_USER_DATA, user));
    }

    public void sendTableListMessage() {
        sendMessage(new Message(TYPE_TABLE_LIST));
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

package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.User;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class LobbyEndpoint {

    private MessageFunction<TableListMessage> updateFunction;
    private MessageFunction<UserMessage> myDataFunction;
    private MessageFunction<UserMessage> userDataFunction;
    private MessageFunction<TableListMessage> tableListFunction;
    private MessageFunction<StringMessage> tokenCheckerFunction;

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
            case "token":
                if (tokenCheckerFunction != null) tokenCheckerFunction.apply(JSON.parseObject(message, StringMessage.class));
                break;
            case "update":
                if (updateFunction != null) updateFunction.apply(JSON.parseObject(message, TableListMessage.class));
                break;
            case "my_data":
                if (myDataFunction != null) myDataFunction.apply(JSON.parseObject(message, UserMessage.class));
                break;
            case "user_data":
                if (userDataFunction != null) userDataFunction.apply(JSON.parseObject(message, UserMessage.class));
                break;
            case "table_list":
                if (tableListFunction != null) tableListFunction.apply(JSON.parseObject(message, TableListMessage.class));
                break;
        }
    }

    public void onTokenCheckerMessageListener(MessageFunction<StringMessage> function) {
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
        sendMessage(new TokenMessage("token", token));
    }

    public void sendMyDataMessage() {
        sendMessage(new Message("my_data"));
    }

    public void sendUserDataMessage(User user) {
        sendMessage(new UserMessage("user_data", user));
    }

    public void sendTableListMessage() {
        sendMessage(new Message("table_list"));
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

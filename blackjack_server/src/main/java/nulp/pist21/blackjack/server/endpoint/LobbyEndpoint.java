package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.List;

@ServerEndpoint("/lobby")
public class LobbyEndpoint {

    private MessageFunction<Message> myDataFunction;
    private MessageFunction<UserMessage> userDataFunction;
    private MessageFunction<Message> tableListFunction;
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
        System.out.println("lobby open");
        this.session = session;
        programData.lobbyManager.add(this);
    }

    @OnClose
    public void onClose() {
        System.out.println("lobby close");
        programData.lobbyManager.remove(this);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("lobby message " + message);
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "token":
                if (tokenCheckerFunction != null) tokenCheckerFunction.apply(JSON.parseObject(message, TokenMessage.class));
                break;
            case "my_data":
                if (myDataFunction != null) myDataFunction.apply(JSON.parseObject(message, Message.class));
                break;
            case "user_data":
                if (userDataFunction != null) userDataFunction.apply(JSON.parseObject(message, UserMessage.class));
                break;
            case "table_list":
                if (tableListFunction != null) tableListFunction.apply(JSON.parseObject(message, Message.class));
                break;
        }
    }

    public void onTokenCheckerMessageListener(MessageFunction<TokenMessage> function) {
        this.tokenCheckerFunction = function;
    }

    public void onMyDataListener(MessageFunction<Message> function) {
        this.myDataFunction = function;
    }

    public void onUserDataListener(MessageFunction<UserMessage> function) {
        this.userDataFunction = function;
    }

    public void onTableListListener(MessageFunction<Message> function) {
        this.tableListFunction = function;
    }

    public void sendTokenMessage(String message) {
        sendMessage(new StringMessage("token", message));
    }

    public void sendUpdateMessage(List<TableInfo> tableList) {
        sendMessage(new TableListMessage("table_list", tableList));
    }

    public void sendMyDataMessage(User user) {
        sendMessage(new UserMessage("my_data", user));
    }

    public void sendUserDataMessage(User user) {
        sendMessage(new UserMessage("user_data", user));
    }

    public void sendTableListMessage(List<TableInfo> tableList) {
        sendMessage(new TableListMessage("table_list", tableList));
    }

    private void sendMessage(Message message) {
        String json = JSON.toJSONString(message);
        System.out.println("lobby send " + json);
        session.getAsyncRemote().sendText(json);
    }

}

package nulp.pist21.blackjack.server.endpoint;

import akka.actor.ActorRef;
import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.actor.Actor;
import nulp.pist21.blackjack.server.actor.UserActor;
import nulp.pist21.blackjack.server.actor.message.MyDataRequest;
import nulp.pist21.blackjack.server.actor.message.TableListRequest;
import nulp.pist21.blackjack.server.actor.message.TokenCheck;
import nulp.pist21.blackjack.server.actor.message.UserDataRequest;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.List;

@ServerEndpoint("/lobby")
public class LobbyEndpoint {

    private Session session;
    private ActorRef actor;
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
        actor = Actor.system.actorOf(UserActor.props(this));
    }

    @OnClose
    public void onClose() {
        System.out.println("lobby close");
        Actor.system.stop(actor);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("lobby message " + message);
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "token":
                TokenMessage tokenMessage = JSON.parseObject(message, TokenMessage.class);
                TokenCheck tokenCheck = new TokenCheck(tokenMessage.getToken());
                actor.tell(tokenCheck, ActorRef.noSender());
                break;
            case "my_data":
                Message message1 = JSON.parseObject(message, Message.class);
                MyDataRequest myDataRequest = new MyDataRequest();
                actor.tell(myDataRequest, ActorRef.noSender());
                break;
            case "user_data":
                UserMessage userMessage = JSON.parseObject(message, UserMessage.class);
                UserDataRequest userDataRequest = new UserDataRequest(userMessage.getUser().getName());
                actor.tell(userDataRequest, ActorRef.noSender());
                break;
            case "table_list":
                Message message2 = JSON.parseObject(message, Message.class);
                TableListRequest tableListRequest = new TableListRequest();
                actor.tell(tableListRequest, ActorRef.noSender());
                break;
        }
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

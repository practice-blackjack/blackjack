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
import java.io.IOException;
import java.util.List;

import static nulp.pist21.blackjack.message.MessageConstant.*;

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
            case TYPE_TOKEN:
                TokenMessage tokenMessage = JSON.parseObject(message, TokenMessage.class);
                TokenCheck tokenCheck = new TokenCheck(tokenMessage.getToken());
                actor.tell(tokenCheck, ActorRef.noSender());
                break;
            case TYPE_MY_DATA:
                Message message1 = JSON.parseObject(message, Message.class);
                MyDataRequest myDataRequest = new MyDataRequest();
                actor.tell(myDataRequest, ActorRef.noSender());
                break;
            case TYPE_USER_DATA:
                UserMessage userMessage = JSON.parseObject(message, UserMessage.class);
                UserDataRequest userDataRequest = new UserDataRequest(userMessage.getUser().getName());
                actor.tell(userDataRequest, ActorRef.noSender());
                break;
            case TYPE_TABLE_LIST:
                Message message2 = JSON.parseObject(message, Message.class);
                TableListRequest tableListRequest = new TableListRequest();
                actor.tell(tableListRequest, ActorRef.noSender());
                break;
        }
    }

    public void sendTokenMessage(boolean isOk) {
        sendMessage(new BooleanMessage(TYPE_TOKEN, isOk));
    }

    public void sendUpdateMessage(List<TableInfo> tableList) {
        sendMessage(new TableListMessage(TYPE_UPDATE, tableList));
    }

    public void sendMyDataMessage(User user) {
        sendMessage(new UserMessage(TYPE_MY_DATA, user));
    }

    public void sendUserDataMessage(User user) {
        sendMessage(new UserMessage(TYPE_USER_DATA, user));
    }

    public void sendTableListMessage(List<TableInfo> tableList) {
        sendMessage(new TableListMessage(TYPE_TABLE_LIST, tableList));
    }

    private void sendMessage(Message message) {
        try {
            String json = JSON.toJSONString(message);
            System.out.println("lobby send " + json);
            session.getBasicRemote().sendText(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

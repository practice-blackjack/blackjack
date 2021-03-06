package nulp.pist21.blackjack.server.endpoint;

import akka.actor.ActorRef;
import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.server.actor.Actor;
import nulp.pist21.blackjack.server.actor.PlayerActor;
import nulp.pist21.blackjack.server.actor.message.PlayerAction;
import nulp.pist21.blackjack.server.actor.message.SitTableRequest;
import nulp.pist21.blackjack.server.actor.message.StandTableRequest;
import nulp.pist21.blackjack.server.actor.message.TokenCheck;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

import static nulp.pist21.blackjack.message.MessageConstant.*;

@ServerEndpoint("/game/play")
public class PlayGameEndpoint {

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
        System.out.println("game_play open");
        this.session = session;
        actor = Actor.system.actorOf(PlayerActor.props(this));
    }

    @OnClose
    public void onClose() {
        System.out.println("game_play close");
        Actor.system.stop(actor);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("game_play message " + message);
        switch (JSON.parseObject(message, Message.class).getType()) {
            case TYPE_TOKEN:
                TokenMessage tokenMessage = JSON.parseObject(message, TokenMessage.class);
                TokenCheck tokenCheck = new TokenCheck(tokenMessage.getToken());
                actor.tell(tokenCheck, ActorRef.noSender());
                break;
            case TYPE_USER_ACTION:
                UserActionMessage userActionMessage = JSON.parseObject(message, UserActionMessage.class);
                PlayerAction playerAction = new PlayerAction(userActionMessage.getTableInfo(), userActionMessage.getPlace(), userActionMessage.getAction(), userActionMessage.getBet());
                actor.tell(playerAction, ActorRef.noSender());
                break;
            case TYPE_SIT:
                TableSmallInfoMessage tableSmallInfoMessage = JSON.parseObject(message, TableSmallInfoMessage.class);
                SitTableRequest sitTableRequest = new SitTableRequest(tableSmallInfoMessage.getTableInfo(), tableSmallInfoMessage.getPlace());
                actor.tell(sitTableRequest, ActorRef.noSender());
                break;
            case TYPE_STAND:
                TableSmallInfoMessage tableSmallInfoMessage1 = JSON.parseObject(message, TableSmallInfoMessage.class);
                StandTableRequest standTableRequest = new StandTableRequest(tableSmallInfoMessage1.getTableInfo(), tableSmallInfoMessage1.getPlace());
                actor.tell(standTableRequest, ActorRef.noSender());
                break;
        }
    }

    public void sendTokenMessage(boolean isOk) {
        sendMessage(new BooleanMessage(TYPE_TOKEN, isOk));
    }

    public void sendWaitMessage(TableInfo tableInfo, int place, String type) {
        sendMessage(new WaitMessage(TYPE_WAIT, tableInfo, place, type));
    }

    public void sendSitMessage(boolean isOk) {
        sendMessage(new BooleanMessage(TYPE_SIT, isOk));
    }

    public void sendStandMessage(boolean isOk) {
        sendMessage(new BooleanMessage(TYPE_STAND, isOk));
    }

    private void sendMessage(Message message) {
        try {
            String json = JSON.toJSONString(message);
            System.out.println("game_play send " + json);
            session.getBasicRemote().sendText(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

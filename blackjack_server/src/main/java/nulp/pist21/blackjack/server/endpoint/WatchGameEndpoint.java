package nulp.pist21.blackjack.server.endpoint;

import akka.actor.ActorRef;
import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.table.Table;
import nulp.pist21.blackjack.server.actor.Actor;
import nulp.pist21.blackjack.server.actor.WatcherActor;
import nulp.pist21.blackjack.server.actor.message.EntryTableRequest;
import nulp.pist21.blackjack.server.actor.message.ExitTableRequest;
import nulp.pist21.blackjack.server.actor.message.TokenCheck;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/game/watch")
public class WatchGameEndpoint {

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
        System.out.println("game_watch open");
        this.session = session;
        actor = Actor.system.actorOf(WatcherActor.props(this));
    }

    @OnClose
    public void onClose() {
        System.out.println("game_watch close");
        Actor.system.stop(actor);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("game_watch message " + message);
        switch (JSON.parseObject(message, Message.class).getType()) {
            case "token":
                TokenMessage tokenMessage = JSON.parseObject(message, TokenMessage.class);
                TokenCheck tokenCheck = new TokenCheck(tokenMessage.getToken());
                actor.tell(tokenCheck, ActorRef.noSender());
                break;
            case "entry":
                TableSmallInfoMessage tableSmallInfoMessage = JSON.parseObject(message, TableSmallInfoMessage.class);
                EntryTableRequest entryTableRequest = new EntryTableRequest(tableSmallInfoMessage.getTableInfo());
                actor.tell(entryTableRequest, ActorRef.noSender());
                break;
            case "exit":
                TableSmallInfoMessage tableSmallInfoMessage1 = JSON.parseObject(message, TableSmallInfoMessage.class);
                ExitTableRequest exitTableRequest = new ExitTableRequest(tableSmallInfoMessage1.getTableInfo());
                actor.tell(exitTableRequest, ActorRef.noSender());
                break;
        }
    }

    public void sendTokenMessage(boolean isOk) {
        sendMessage(new StringMessage("token", isOk ? "token ok" : "token error"));
    }

    public void sendUpdateMessage(Table table) {
        sendMessage(new TableFullInfoMessage("update", table));
    }

    public void sendUserActionMessage(TableInfo tableInfo, int place, String action, int bet) {
        sendMessage(new UserActionMessage("user_action", tableInfo, place, action, bet));
    }

    public void sendEntryMessage(boolean isOk) {
        sendMessage(new StringMessage("entry", isOk ? "entry ok" : "entry error"));
    }

    public void sendExitMessage(boolean isOk) {
        sendMessage(new StringMessage("exit", isOk ? "entry ok" : "entry error"));
    }

    public void sendResultMessage(String message) {
        sendMessage(new ResultMessage("result"));
    }

    private void sendMessage(Message message) {
        String json = JSON.toJSONString(message);
        System.out.println("game_watch send " + json);
        session.getAsyncRemote().sendText(json);
    }

}

package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.server.data.ProgramData;
import nulp.pist21.blackjack.model.User;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/login")
public class LoginEndpoint {

    private Session session;
    private final ProgramData programData = ProgramData.get();

    @OnOpen
    public void OnOpen(Session session) {
        this.session = session;
        programData.sessionList.addSession(session);
    }

    @OnClose
    public void OnClose() {
        programData.sessionList.deleteSession(session);
    }

    @OnMessage
    public void OnMessage(String message) {
        User user = JSON.parseObject(message, User.class);
        if(this.session != null && this.session.isOpen()) {
            session.getAsyncRemote().sendText(this.programData.userList.existUser(user) ? "hello user" : "access denied");
        }

    }
}
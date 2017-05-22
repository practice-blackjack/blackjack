package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.server.data.ProgramData;
import nulp.pist21.blackjack.model.User;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/register")
public class RegisterEndpoint {

    private Session session;
    private final ProgramData programData = ProgramData.get();

    @OnOpen
    public void OnOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void OnClose() {
    }

    @OnMessage
    public void OnMessage(String message) {
        User user = JSON.parseObject(message, User.class);
        if (this.session != null && this.session.isOpen()) {
            session.getAsyncRemote().sendText(!programData.userList.existUserName(user.getName()) && programData.userList.addUser(user) ? "user added" : "error");
        }
    }

}

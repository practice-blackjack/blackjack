package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.MessageFunction;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.UserMessage;
import nulp.pist21.blackjack.server.data.ProgramData;
import nulp.pist21.blackjack.model.User;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/register")
public class RegisterEndpoint {

    private Session session;
    private MessageFunction<UserMessage> function;
    private final ProgramData programData = ProgramData.get();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("register open");
        this.session = session;
    }

    @OnClose
    public void onClose() {
        System.out.println("register close");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("register message " + message);
        UserMessage userMessage = JSON.parseObject(message, UserMessage.class);
        if (function != null) {
            function.apply(userMessage);
        }
        if (this.session != null && this.session.isOpen()) {
            StringMessage stringMessage;
            User newUser = userMessage.getUser();
            User user = programData.userList.existUserName(newUser.getName());
            if (user == null && programData.userList.addUser(newUser)) {
                stringMessage = new StringMessage("user added");
            } else {
                stringMessage = new StringMessage("error");
            }
            sendMessage(stringMessage);
        }
    }

    public void onMessageListener(MessageFunction<UserMessage> function) {
        this.function = function;
    }

    public void sendMessage(StringMessage message) {
        String json = JSON.toJSONString(message);
        System.out.println("register send " + json);
        session.getAsyncRemote().sendText(json);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

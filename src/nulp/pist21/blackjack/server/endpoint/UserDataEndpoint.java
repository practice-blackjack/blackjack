package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.MessageFunction;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.message.UserMessage;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/userdata")
public class UserDataEndpoint {

    private Session session;
    private MessageFunction<UserMessage> function;
    private final ProgramData programData = ProgramData.get();
    private TokenChecker tokenChecker;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("userdata open");
        this.session = session;
        tokenChecker = new TokenChecker(session);
    }

    @OnClose
    public void onClose() {
        System.out.println("userdata close");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("userdata message " + message);
        if (tokenChecker.receive(message)) return;
        UserMessage userMessage = JSON.parseObject(message, UserMessage.class);
        if (function != null) {
            function.apply(userMessage);
        }

        if (this.session != null && this.session.isOpen()) {
            User user;
            if (userMessage.getUser().getName().equals("")) {
                user = programData.tokenList.getUser(tokenChecker.getToken());
            } else {
                user = programData.userList.existUserName(userMessage.getUser().getName());
                if (user != null) {
                    user.setPassword("");
                }
            }
            sendMessage(new UserMessage("user data", user));
        }
    }

    public void onMessageListener(MessageFunction<UserMessage> function) {
        this.function = function;
    }

    public void sendMessage(UserMessage message) {
        String json = JSON.toJSONString(message);
        System.out.println("userdata send " + json);
        session.getAsyncRemote().sendText(json);
    }

}

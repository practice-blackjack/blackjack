package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.MessageFunction;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.message.UserMessage;
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
    private MessageFunction<UserMessage> function;
    private final ProgramData programData = ProgramData.get();
    private long token = -1;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("login open");
        this.session = session;
    }

    @OnClose
    public void onClose() {
        System.out.println("login close");
        programData.tokenList.deleteUser(token);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("login message " + message);
        UserMessage userMessage = JSON.parseObject(message, UserMessage.class);
        if (function != null) {
            function.apply(userMessage);
        }
        if(this.session != null && this.session.isOpen()) {
            TokenMessage tokenMessage;
            User user = this.programData.userList.existUser(userMessage.getUser());
            if (user != null) {
                programData.tokenList.deleteUser(user);
                token = programData.tokenList.addUser(user);
                tokenMessage = new TokenMessage("login+, send token", token);
            } else {
                tokenMessage = new TokenMessage("login-", -1);
            }
            sendMessage(tokenMessage);
        }
    }

    public void onMessageListener(MessageFunction<UserMessage> function) {
        this.function = function;
    }

    public void sendMessage(TokenMessage message) {
        String json = JSON.toJSONString(message);
        System.out.println("login send " + json);
        session.getAsyncRemote().sendText(json);
    }

}
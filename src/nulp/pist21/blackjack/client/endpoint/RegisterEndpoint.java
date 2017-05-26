package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.MessageFunction;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.message.UserMessage;

import javax.websocket.*;
import java.io.IOException;

@ClientEndpoint
public class RegisterEndpoint {

    private Session session;
    private MessageFunction<StringMessage> function;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        StringMessage stringMessage = JSON.parseObject(message, StringMessage.class);
        if (function != null) {
            function.apply(stringMessage);
        }
    }

    public void onMessageListener(MessageFunction<StringMessage> function) {
        this.function = function;
    }

    public void sendMessage(UserMessage message) {
        String json = JSON.toJSONString(message);
        session.getAsyncRemote().sendText(json);
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

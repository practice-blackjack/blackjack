package nulp.pist21.blackjack.client.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.TokenMessage;

import javax.websocket.Session;

public class TokenChecker {

    private boolean init = true;
    private TokenMessage initMessage;

    public TokenChecker(TokenMessage message) {
        initMessage = message;
    }

    public void send(Session session) {
        String json = JSON.toJSONString(initMessage);
        session.getAsyncRemote().sendText(json);
    }

    public boolean receive(String message) {
        if (init) {
            StringMessage stringMessage = JSON.parseObject(message, StringMessage.class);
            if (stringMessage.getMessage().equals("token ok")) {
                init = false;
            }
            return true;
        }
        return false;
    }

}

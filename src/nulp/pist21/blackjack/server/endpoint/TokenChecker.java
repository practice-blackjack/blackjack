package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.MessageFunction;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.Session;

public class TokenChecker {

    private final ProgramData programData = ProgramData.get();
    private Session session;
    private MessageFunction<TokenMessage> function;
    private boolean init = true;
    private long token;

    public TokenChecker(Session session) {
        this.session = session;
    }

    public boolean receive(String message) {
        if (init) {
            System.out.println("tokenchecker message " + message);
            TokenMessage tokenMessage = JSON.parseObject(message, TokenMessage.class);
            if (this.session != null && this.session.isOpen()) {
                token = tokenMessage.getToken();
                User user = programData.tokenList.getUser(token);
                StringMessage stringMessage;
                if (user != null) {
                    stringMessage = new StringMessage("token ok");
                    init = false;
                } else {
                    stringMessage = new StringMessage("token error");
                }
                String json = JSON.toJSONString(stringMessage);
                System.out.println("tokenchecker send " + json);
                session.getAsyncRemote().sendText(json);
            }
            if (function != null) {
                function.apply(tokenMessage);
            }
            return true;
        }
        return false;
    }

    public void onMessageListener(MessageFunction<TokenMessage> function) {
        this.function = function;
    }

    public long getToken() {
        return token;
    }

}

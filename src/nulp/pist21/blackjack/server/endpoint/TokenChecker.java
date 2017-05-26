package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.StringMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.Session;

public class TokenChecker {

    private final ProgramData programData = ProgramData.get();
    private Session session;
    private boolean init = true;
    private long token;

    public TokenChecker(Session session) {
        this.session = session;
    }

    public boolean receive(String message) {
        if (init) {
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
                session.getAsyncRemote().sendText(json);
            }
        }
        return init;
    }

    public long getToken() {
        return token;
    }

}

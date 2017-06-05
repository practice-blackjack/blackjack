package nulp.pist21.blackjack.server.endpoint;

import com.alibaba.fastjson.JSON;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.server.data.ProgramData;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/card")
public class CardEndpoint {

    private Session session;
    private final ProgramData programData = ProgramData.get();
    private TokenChecker tokenChecker;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("card open");
        this.session = session;
        tokenChecker = new TokenChecker(session);
        tokenChecker.onMessageListener((TokenMessage tokenMessage) -> programData.cardManager.add(tokenMessage.getToken(), this));
    }

    @OnClose
    public void onClose() {
        System.out.println("card close");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("card message " + message);
        if (tokenChecker.receive(message)) return;
    }

    public void onTokenCheckerMessageListener(MessageFunction<TokenMessage> function) {
        tokenChecker.onMessageListener(function);
    }

    public void sendMessage(CardMessage message) {
        String json = JSON.toJSONString(message);
        System.out.println("card send " + json);
        session.getAsyncRemote().sendText(json);
    }

}
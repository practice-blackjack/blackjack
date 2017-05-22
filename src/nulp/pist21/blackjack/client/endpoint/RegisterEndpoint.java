package nulp.pist21.blackjack.client.endpoint;

import javax.websocket.*;
import java.io.IOException;

@ClientEndpoint
public class RegisterEndpoint {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("server > " + message);
    }

    public void sendMessage(String message) {
        session.getAsyncRemote().sendText(message);
    }

    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package nulp.pist21.blackjack.client.endpoint;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class LoginEndpoint {

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
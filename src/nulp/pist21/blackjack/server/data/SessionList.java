package nulp.pist21.blackjack.server.data;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class SessionList {

    private final List<Session> sessionList = new ArrayList<>();

    public void addSession(Session session){
        sessionList.add(session);
    }

    public void deleteSession(Session session) {
        sessionList.remove(session);
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

}

package nulp.pist21.blackjack.server.data;

import nulp.pist21.blackjack.message.TableSmallInfoMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.endpoint.WatchGameEndpoint;

import java.util.ArrayList;
import java.util.List;

public class WatchGameManager {

    private final ProgramData programData;
    private List<WatchGameEndpoint> endpointList;

    public WatchGameManager(ProgramData programData) {
        this.programData = programData;
        endpointList = new ArrayList<>();
    }

    @SuppressWarnings("Duplicates")
    public void add(WatchGameEndpoint endpoint) {
        endpointList.add(endpoint);
        endpoint.onTokenCheckerMessageListener((TokenMessage tokenMessage) -> {
            long token = tokenMessage.getToken();
            endpoint.setToken(token);
            User user = programData.tokenList.getUser(token);
            String message;
            if (user != null) {
                endpoint.setLogin(true);
                message = "token ok";
            } else {
                endpoint.setLogin(false);
                message = "token error";
            }
            endpoint.sendTokenMessage(message);
        });
        endpoint.onEntryListener((TableSmallInfoMessage tableSmallInfoMessage) -> {
            if (!endpoint.isLogin()) {
                endpoint.sendTokenMessage("token error");
                return;
            }
            TableInfo tableInfo = tableSmallInfoMessage.getTableInfo();
            int place = tableSmallInfoMessage.getPlace();
            //todo: entry
            String message;
            message = "ok or not ok";
            endpoint.sendEntryMessage(message);
        });
        endpoint.onExitListener((TableSmallInfoMessage tableSmallInfoMessage) -> {
            if (!endpoint.isLogin()) {
                endpoint.sendTokenMessage("token error");
                return;
            }
            TableInfo tableInfo = tableSmallInfoMessage.getTableInfo();
            int place = tableSmallInfoMessage.getPlace();
            //todo: exit
            String message;
            message = "ok or not ok";
            endpoint.sendEntryMessage(message);
        });
    }

    public void remove(WatchGameEndpoint endpoint) {
        endpointList.remove(endpoint);
    }

}

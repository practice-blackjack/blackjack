package nulp.pist21.blackjack.server.data;

import nulp.pist21.blackjack.message.TableSmallInfoMessage;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.message.UserActionMessage;
import nulp.pist21.blackjack.message.WaitMessage;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.endpoint.PlayGameEndpoint;

import java.util.ArrayList;
import java.util.List;

public class PlayGameManager {

    private final ProgramData programData;
    private List<PlayGameEndpoint> endpointList;

    public PlayGameManager(ProgramData programData) {
        this.programData = programData;
        endpointList = new ArrayList<>();
    }

    @SuppressWarnings("Duplicates")
    public void add(PlayGameEndpoint endpoint) {
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
        endpoint.onUserActionMessageListener((UserActionMessage userActionMessage) -> {
            if (!endpoint.isLogin()) {
                endpoint.sendTokenMessage("token error");
                return;
            }
            TableInfo tableInfo = userActionMessage.getTableInfo();
            int place = userActionMessage.getPlace();
            String action = userActionMessage.getAction();
            int bet = userActionMessage.getBet();
            //todo: user_action
            endpoint.sendWaitMessage(tableInfo, place, WaitMessage.WAIT_BET);
        });
        endpoint.onSitListener((TableSmallInfoMessage tableSmallInfoMessage) -> {
            if (!endpoint.isLogin()) {
                endpoint.sendTokenMessage("token error");
                return;
            }
            TableInfo tableInfo = tableSmallInfoMessage.getTableInfo();
            int place = tableSmallInfoMessage.getPlace();
            //todo: sit
            String message;
            message = "ok or not ok";
            endpoint.sendSitMessage(message);
        });
        endpoint.onStayListener((TableSmallInfoMessage tableSmallInfoMessage) -> {
            if (!endpoint.isLogin()) {
                endpoint.sendTokenMessage("token error");
                return;
            }
            TableInfo tableInfo = tableSmallInfoMessage.getTableInfo();
            int place = tableSmallInfoMessage.getPlace();
            //todo: stay
            String message;
            message = "ok or not ok";
            endpoint.sendSitMessage(message);
        });
    }

    public void remove(PlayGameEndpoint endpoint) {
        endpointList.remove(endpoint);
    }

}

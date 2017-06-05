package nulp.pist21.blackjack.server.data;

import nulp.pist21.blackjack.message.Message;
import nulp.pist21.blackjack.message.TokenMessage;
import nulp.pist21.blackjack.message.UserMessage;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.endpoint.LobbyEndpoint;

import java.util.ArrayList;
import java.util.List;

public class LobbyManager {

    private final ProgramData programData;
    private List<LobbyEndpoint> endpointList;

    public LobbyManager(ProgramData programData) {
        this.programData = programData;
        endpointList = new ArrayList<>();
    }

    @SuppressWarnings("Duplicates")
    public void add(LobbyEndpoint endpoint) {
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
        endpoint.onMyDataListener((Message message) -> {
            if (!endpoint.isLogin()) {
                endpoint.sendTokenMessage("token error");
                return;
            }
            User user = programData.tokenList.getUser(endpoint.getToken());
            endpoint.sendMyDataMessage(user);
        });
        endpoint.onUserDataListener((UserMessage userMessage) -> {
            if (!endpoint.isLogin()) {
                endpoint.sendTokenMessage("token error");
                return;
            }
            User user = programData.userList.existUserName(userMessage.getUser().getName());
            if (user != null) {
                user.setPassword("");
            }
            endpoint.sendUserDataMessage(user);
        });
        endpoint.onTableListListener((Message message) -> {
            if (!endpoint.isLogin()) {
                endpoint.sendTokenMessage("token error");
                return;
            }
            List<TableInfo> tableInfoList = programData.tableManager.getTablesInfo();
            endpoint.sendTableListMessage(tableInfoList);
        });
    }

    public void remove(LobbyEndpoint endpoint) {
        endpointList.remove(endpoint);
    }

}

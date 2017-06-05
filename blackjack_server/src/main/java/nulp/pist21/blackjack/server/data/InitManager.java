package nulp.pist21.blackjack.server.data;

import nulp.pist21.blackjack.message.Message;
import nulp.pist21.blackjack.message.UserMessage;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.server.endpoint.InitEndpoint;

import java.util.ArrayList;
import java.util.List;

public class InitManager {

    private final ProgramData programData;
    private List<InitEndpoint> endpointList;

    public InitManager(ProgramData programData) {
        this.programData = programData;
        endpointList = new ArrayList<>();
    }

    public void add(InitEndpoint endpoint) {
        endpointList.add(endpoint);
        endpoint.onRegisterListener((UserMessage userMessage) -> {
            User newUser = userMessage.getUser();
            User user = programData.userList.existUserName(newUser.getName());
            String message;
            if (user == null && programData.userList.addUser(newUser)) {
                message = "user added";
            } else {
                message = "error";
            }
            endpoint.sendRegisterMessage(message);
        });
        endpoint.onLoginListener((UserMessage userMessage) -> {
            User user = programData.userList.existUser(userMessage.getUser());
            long token = -1;
            if (user != null) {
                programData.tokenList.deleteUser(user);
                token = programData.tokenList.addUser(user);
            }
            endpoint.setToken(token);
            endpoint.sendLoginMessage(token);
        });
        endpoint.onUnloginListener((Message message) -> {
            programData.tokenList.deleteUser(endpoint.getToken());
            endpoint.sendUnloginMessage();
        });
    }

    public void remove(InitEndpoint endpoint) {
        endpointList.remove(endpoint);
    }

}

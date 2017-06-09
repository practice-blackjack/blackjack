package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import nulp.pist21.blackjack.server.actor.message.*;
import nulp.pist21.blackjack.server.endpoint.LobbyEndpoint;

public class UserActor extends AbstractActor {

    static public Props props(LobbyEndpoint endpoint) {
        return Props.create(UserActor.class, () -> new UserActor(endpoint));
    }

    private final LobbyEndpoint endpoint;

    public UserActor(LobbyEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TokenCheck.class, message -> {
                    endpoint.setToken(message.token);
                    Actor.tokenManager.tell(message, getSelf());
                })
                .match(MyDataRequest.class, message -> {
                    if (!endpoint.isLogin()) {
                        endpoint.sendTokenMessage(false);
                        return;
                    }
                    Actor.tokenManager.tell(new MyDataTokenRequest(endpoint.getToken()), getSelf());
                })
                .match(UserDataRequest.class, message -> {
                    if (!endpoint.isLogin()) {
                        endpoint.sendTokenMessage(false);
                        return;
                    }
                    Actor.userManager.tell(message, getSelf());
                })
                .match(TableListRequest.class, message -> {
                    if (!endpoint.isLogin()) {
                        endpoint.sendTokenMessage(false);
                        return;
                    }
                    Actor.tableManager.tell(message, getSelf());
                })
                .match(TokenChecked.class, message -> {
                    endpoint.setLogin(message.isOk);
                    endpoint.sendTokenMessage(message.isOk);
                })
                .match(MyDataResponse.class, message -> {
                    endpoint.sendMyDataMessage(message.user);
                })
                .match(UserDataResponse.class, message -> {
                    endpoint.sendUserDataMessage(message.user);
                })
                .match(TableListResponse.class, message -> {
                    endpoint.sendTableListMessage(message.tableInfoList);
                })
                //todo:
                .match(TableListUpdateResponse.class, message -> {
                    endpoint.sendUpdateMessage(message.tableInfoList);
                })
                .build();
    }

}

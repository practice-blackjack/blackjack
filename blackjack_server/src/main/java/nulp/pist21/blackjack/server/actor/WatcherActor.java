package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import nulp.pist21.blackjack.server.actor.message.*;
import nulp.pist21.blackjack.server.endpoint.WatchGameEndpoint;

public class WatcherActor extends AbstractActor {

    static public Props props(WatchGameEndpoint endpoint) {
        return Props.create(WatcherActor.class, () -> new WatcherActor(endpoint));
    }

    private final WatchGameEndpoint endpoint;

    public WatcherActor(WatchGameEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TokenCheck.class, message -> {
                    endpoint.setToken(message.token);
                    Actor.tokenManager.tell(message, getSelf());
                })
                .match(EntryTableRequest.class, message -> {
                    if (!endpoint.isLogin()) {
                        endpoint.sendTokenMessage("token error");
                        return;
                    }
                    Actor.tableManager.tell(message, getSelf());
                })
                .match(ExitTableRequest.class, message -> {
                    if (!endpoint.isLogin()) {
                        endpoint.sendTokenMessage("token error");
                        return;
                    }
                    Actor.tableManager.tell(message, getSelf());
                })
                .match(TokenChecked.class, message -> {
                    endpoint.setLogin(message.isOk);
                    endpoint.sendTokenMessage(message.isOk ? "token ok" : "token error");
                })
                .match(EntryTableResponse.class, message -> {
                    endpoint.sendEntryMessage(message.isOk ? "entry ok" : "entry error");
                })
                .match(ExitTableResponse.class, message -> {
                    endpoint.sendExitMessage(message.isOk ? "exit ok" : "exit error");
                })
                //todo:
                .match(TableUpdate.class, message -> {
                    endpoint.sendUpdateMessage(message.table);
                })
                //todo:
                .match(WatchPlayerAction.class, message -> {
                    endpoint.sendUserActionMessage(message.tableInfo, message.place, message.action, message.bet);
                })
                .match(ResultAction.class, message -> {
                    //todo:
                    endpoint.sendResultMessage("result");
                })
                .build();
    }

}

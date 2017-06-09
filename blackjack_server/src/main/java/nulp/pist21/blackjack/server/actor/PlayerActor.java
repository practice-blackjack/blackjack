package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import nulp.pist21.blackjack.server.actor.message.*;
import nulp.pist21.blackjack.server.endpoint.PlayGameEndpoint;

public class PlayerActor extends AbstractActor {

    static public Props props(PlayGameEndpoint endpoint) {
        return Props.create(PlayerActor.class, () -> new PlayerActor(endpoint));
    }

    private final PlayGameEndpoint endpoint;

    public PlayerActor(PlayGameEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TokenCheck.class, message -> {
                    endpoint.setToken(message.token);
                    Actor.tokenManager.tell(message, getSelf());
                })
                .match(SitTableRequest.class, message -> {
                    if (!endpoint.isLogin()) {
                        endpoint.sendTokenMessage(false);
                        return;
                    }
                    Actor.tableManager.tell(message, getSelf());
                })
                .match(StandTableRequest.class, message -> {
                    if (!endpoint.isLogin()) {
                        endpoint.sendTokenMessage(false);
                        return;
                    }
                    Actor.tableManager.tell(message, getSelf());
                })
                .match(PlayerAction.class, message -> {
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
                .match(SitTableResponse.class, message -> {
                    endpoint.sendSitMessage(message.isOk ? "sit ok" : "sit error");
                })
                .match(StandTableResponse.class, message -> {
                    endpoint.sendStandMessage(message.isOk ? "stand ok" : "stand error");
                })
                .match(WaitAction.class, message -> {
                    endpoint.sendWaitMessage(message.tableInfo, message.place, message.waitType);
                })
                .build();
    }

}

package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import nulp.pist21.blackjack.server.actor.message.*;
import nulp.pist21.blackjack.server.endpoint.InitEndpoint;

public class InitActor extends AbstractActor {

    static public Props props(InitEndpoint endpoint) {
        return Props.create(InitActor.class, () -> new InitActor(endpoint));
    }

    private final InitEndpoint endpoint;

    public InitActor(InitEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RegisterRequest.class, message -> {
                    Actor.userManager.tell(message, getSelf());
                })
                .match(LoginRequest.class, message -> {
                    Actor.userManager.tell(message, getSelf());
                })
                .match(LogoutRequest.class, message -> {
                    long token = endpoint.getToken();
                    Actor.tokenManager.tell(new LogoutTokenRequest(token), getSelf());
                })
                .match(RegisterResponse.class, message -> {
                    endpoint.sendRegisterMessage(message.isOk);
                })
                .match(LoginResponse.class, message -> {
                    long token = message.token;
                    endpoint.setToken(token);
                    endpoint.sendLoginMessage(token);
                })
                .match(LogoutResponse.class, message -> {
                    endpoint.sendLogoutMessage(true);
                })
                .build();
    }

}

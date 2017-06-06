package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class KernelActor extends AbstractActor {

    static public Props props() {
        return Props.create(KernelActor.class, () -> new KernelActor());
    }

    public KernelActor() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TableActor.class, greeting -> {

                })
                .build();
    }

}

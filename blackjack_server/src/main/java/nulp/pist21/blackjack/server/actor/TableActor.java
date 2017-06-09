package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import nulp.pist21.blackjack.model.Table;
import nulp.pist21.blackjack.server.actor.message.*;

import java.util.ArrayList;
import java.util.List;

public class TableActor extends AbstractActor {

    static public Props props(Table table) {
        return Props.create(TableActor.class, () -> new TableActor(table));
    }

    private List<ActorRef> players;
    private List<ActorRef> watchers;
    private Table table;

    public TableActor(Table table) {
        players = new ArrayList<>();
        this.table = table;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(EntryTableRequest.class, message -> {
                    ActorRef sender = getSender();
                    watchers.add(sender);
                    sender.tell(new EntryTableResponse(true), getSelf());
                })
                .match(ExitTableRequest.class, message -> {
                    ActorRef sender = getSender();
                    watchers.add(sender);
                    sender.tell(new ExitTableResponse(true), getSelf());
                })
                .match(SitTableRequest.class, message -> {
                    ActorRef sender = getSender();
                    if (players.get(message.place) == null) {
                        players.add(message.place, sender);
                    }
                    sender.tell(new SitTableResponse(true), getSelf());
                })
                .match(StandTableRequest.class, message -> {
                    ActorRef sender = getSender();
                    if (players.get(message.place).equals(sender)) {
                        players.add(message.place, null);
                    }
                    sender.tell(new StandTableResponse(true), getSelf());
                })
                .match(PlayerAction.class, message -> {
                    //todo:
                })
                .build();
    }

}

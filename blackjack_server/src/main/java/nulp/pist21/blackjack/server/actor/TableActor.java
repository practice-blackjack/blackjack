package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.Deck;
import nulp.pist21.blackjack.model.game.IHand;
import nulp.pist21.blackjack.model.game.IRound;
import nulp.pist21.blackjack.model.table.Table;
import nulp.pist21.blackjack.server.actor.message.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableActor extends AbstractActor {

    static public Props props(TableInfo tableInfo) {
        return Props.create(TableActor.class, () -> new TableActor(tableInfo));
    }

    private ActorRef[] players;
    private List<ActorRef> watchers;
    private Table table;

    public TableActor(TableInfo tableInfo) {
        players = new ActorRef[tableInfo.getMaxPlayerCount()];
        watchers = new ArrayList<>();
        this.table = new Table(tableInfo.getMaxPlayerCount(), new Deck(2));
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
                    watchers.remove(sender);
                    sender.tell(new ExitTableResponse(true), getSelf());
                })
                .match(SitTableRequest.class, message -> {
                    ActorRef sender = getSender();
                    if (players[message.place] == null) {
                        table.getBoxes()[message.place].isActivated(true);
                        players[message.place] = sender;
                    }
                    sender.tell(new SitTableResponse(true), getSelf());
                })
                .match(StandTableRequest.class, message -> {
                    ActorRef sender = getSender();
                    if (players[message.place].equals(sender)) {
                        table.getBoxes()[message.place].isActivated(false);
                        players[message.place] = null;
                    }
                    sender.tell(new StandTableResponse(true), getSelf());
                })
                .match(PlayerAction.class, message -> {
                    if (players[message.place] != getSender()) {
                        return;
                    }
                    IRound round = table.getRound();
                    IHand userHand = round.getCurrentHand();
                    int place = Arrays.asList(table.getBoxes()).indexOf(userHand);
                    if (message.place != place) {
                        return;
                    }
                    //todo:
                    GameAction action = new GameAction(GameAction.Actions.HIT);
                    round.next(action);

                    watchers.forEach(w -> {
                        w.tell(new TableUpdate(table), getSelf());
                    });
                })
                .build();
    }

}

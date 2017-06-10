package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import nulp.pist21.blackjack.message.MessageConstant;
import nulp.pist21.blackjack.model.Player;
import nulp.pist21.blackjack.model.TableFullInfo;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.Deck;
import nulp.pist21.blackjack.model.game.IHand;
import nulp.pist21.blackjack.model.game.IRound;
import nulp.pist21.blackjack.model.game.Round;
import nulp.pist21.blackjack.model.table.Table;
import nulp.pist21.blackjack.server.actor.message.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nulp.pist21.blackjack.message.MessageConstant.*;

public class TableActor extends AbstractActor {

    static public Props props(TableInfo tableInfo) {
        return Props.create(TableActor.class, () -> new TableActor(tableInfo));
    }

    private ActorRef[] players;
    private User[] users;
    private List<ActorRef> watchers;
    private TableInfo tableInfo;
    private Table table;

    private boolean wait;

    public TableActor(TableInfo tableInfo) {
        players = new ActorRef[tableInfo.getMaxPlayerCount()];
        users = new User[tableInfo.getMaxPlayerCount()];
        watchers = new ArrayList<>();
        this.tableInfo = tableInfo;
        this.table = new Table(tableInfo.getMaxPlayerCount(), new Deck(2));
        wait = false;
        new Thread(() -> gameCycle()).start();
    }

    public void gameCycle() {
        ActorRef[] players;
        ActorRef player;
        long time;
        while (true) {
            players = Arrays.copyOf(this.players, this.players.length);
            for (int i = 0; i < players.length; i++) {
                player = players[i];
                if (player == null) {
                    continue;
                }
                wait = true;
                player.tell(new WaitAction(tableInfo, i, ACTION_WAIT_BET), getSelf());
                time = System.currentTimeMillis();
                while (wait){
                    if (System.currentTimeMillis() - time > 30000) {
                        break;
                    }
                }
            }
            for (int i = 0; i < players.length; i++) {
                player = players[i];
                if (player == null) {
                    continue;
                }
                wait = true;
                player.tell(new WaitAction(tableInfo, i, ACTION_WAIT_HIT_OR_STAND), getSelf());
                time = System.currentTimeMillis();
                while (wait){
                    if (System.currentTimeMillis() - time > 30000) {
                        break;
                    }
                }
            }
        }
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
                .match(SitTableUserRequest.class, message -> {
                    ActorRef sender = getSender();
                    boolean result = players[message.place] == null;
                    if (result) {
                        table.getBoxes()[message.place].isActivated(true);
                        players[message.place] = sender;
                        users[message.place] = message.user;
                    }
                    sender.tell(new SitTableResponse(result), getSelf());
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
                    if (message.place != place || !wait) {
                        return;
                    }
                    GameAction.Actions action = null;
                    switch (message.action) {
                        case ACTION_BET:
                            //todo:
                            break;
                        case ACTION_HIT:
                            action = GameAction.Actions.HIT;
                            break;
                        case ACTION_STAND:
                            action = GameAction.Actions.STAND;
                            break;
                    }
                    GameAction gameAction = new GameAction(action);
                    round.next(gameAction);
                    wait = false;
                    watchers.forEach(w -> {
                        w.tell(new TableUpdate(getTableFullInfo(table)), getSelf());
                    });
                })
                .build();
    }

    public TableFullInfo getTableFullInfo(Table table) {
        IRound round = table.getRound();
        IHand userHand = round.getCurrentHand();
        int currentUser = Arrays.asList(table.getBoxes()).indexOf(userHand);
        Card[] dealerHand = round.getHand(Round.DEALER_INDEX).getHand();
        Player[] players = new Player[tableInfo.getMaxPlayerCount()];
        for (int i = 0; i < round.getHandCount(); i++) {
            IHand hand = round.getHand(i);
            int index = Arrays.asList(table.getBoxes()).indexOf(hand);
            User user = users[index];
            players[index] = new Player(user.getName(), user.getCash(), hand.getHand());
        }
        return new TableFullInfo(dealerHand, players, currentUser);
    }

}

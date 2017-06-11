package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import nulp.pist21.blackjack.message.MessageConstant;
import nulp.pist21.blackjack.model.Player;
import nulp.pist21.blackjack.model.TableFullInfo;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.model.User;
import nulp.pist21.blackjack.model.actions.Action;
import nulp.pist21.blackjack.model.actions.BetAction;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.Deck;
import nulp.pist21.blackjack.model.game.GameWithDealer;
import nulp.pist21.blackjack.model.game.round.BetRound;
import nulp.pist21.blackjack.model.game.round.GameRound;
import nulp.pist21.blackjack.model.game.round.IRound;
import nulp.pist21.blackjack.model.table.Table;
import nulp.pist21.blackjack.server.actor.message.*;

import java.util.*;

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
    private GameWithDealer game;
    private Timer timer;
    private long delay;

    public TableActor(TableInfo tableInfo) {
        players = new ActorRef[tableInfo.getMaxPlayerCount()];
        users = new User[tableInfo.getMaxPlayerCount()];
        watchers = new ArrayList<>();
        this.tableInfo = tableInfo;
        this.table = new Table(tableInfo.getMaxPlayerCount(), tableInfo.getMin(), tableInfo.getMax(), new Deck(2));
        this.game = new GameWithDealer(table);
        this.delay = 30000;
        timer = new Timer();
    }

    public void gameCycle() {

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
                        boolean wasEmpty = table.isEmpty();
                        table.getBoxes()[message.place].isActivated(true);
                        players[message.place] = sender;
                        users[message.place] = message.user;
                        if (wasEmpty){
                            timer.schedule(new TimedOutAction(), delay);
                            notifyCurrentPlayer();
                        }
                    }
                    sender.tell(new SitTableResponse(result), getSelf());
                })
                .match(StandTableRequest.class, message -> {
                    ActorRef sender = getSender();
                    if (sender.equals(players[message.place])) {
                        table.getBoxes()[message.place].isActivated(false);
                        players[message.place] = null;
                    }
                    sender.tell(new StandTableResponse(true), getSelf());
                })
                .match(PlayerAction.class, message -> {
                    if (players[message.place] != getSender()) {
                        return;
                    }
                    int place = game.getCurrentIndex();
                    if (message.place != place) {
                        return;
                    }
                    Action action = null;
                    switch (message.action) {
                        case ACTION_BET:
                            action = new BetAction(message.bet);
                            break;
                        case ACTION_HIT:
                            action = new GameAction(GameAction.Actions.HIT);
                            break;
                        case ACTION_STAND:
                            action = new GameAction(GameAction.Actions.STAND);
                            break;
                    }
                    if (!game.isOver() &&
                            game.getCurrentRound().next(action)){
                        timer.cancel();
                        watchers.forEach(w -> {
                            w.tell(new TableUpdate(getTableFullInfo()), getSelf());
                        });

                        if (game.isOver()){
                            if (table.isEmpty()){
                                timer.cancel();
                            }
                        }
                        else {
                            notifyCurrentPlayer();
                            timer.schedule(new TimedOutAction(), delay);
                        }
                    }
                })
                .build();
    }

    private void notifyCurrentPlayer(){
        int playerPlace = game.getCurrentIndex();
        ActorRef currentPlayer = players[playerPlace];
        if (game.getCurrentRound() instanceof BetRound){
            currentPlayer.tell(new WaitAction(tableInfo, playerPlace, MessageConstant.ACTION_WAIT_BET), getSelf());
        }
        else if (game.getCurrentRound() instanceof GameRound){
            currentPlayer.tell(new WaitAction(tableInfo, playerPlace, MessageConstant.ACTION_WAIT_HIT_OR_STAND), getSelf());
        }
    }

    public TableFullInfo getTableFullInfo() {
        IRound round = game.getCurrentRound();
        int currentUser = game.getCurrentIndex();
        Card[] dealerHand = game.getDealer().getHand();
        Player[] players = new Player[tableInfo.getMaxPlayerCount()];
        for (int i = 0; i < table.getBoxes().length; i++) {
            User user = users[i];
            players[i] = new Player(user.getName(), user.getCash(), table.getBoxes()[i].getHand());
        }
        return new TableFullInfo(dealerHand, players, currentUser);
    }

    private class TimedOutAction extends TimerTask{
        @Override
        public void run() {
            if (game.isOver()){
                return;
            }

            if (game.getCurrentRound() instanceof BetRound){
                game.getCurrentRound().next(new BetAction(tableInfo.getMin()));
            }
            else if (game.getCurrentRound() instanceof GameRound){
                game.getCurrentRound().next(new GameAction(GameAction.Actions.STAND));
            }
        }
    }

}

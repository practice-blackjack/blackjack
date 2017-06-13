package nulp.pist21.blackjack.server.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import nulp.pist21.blackjack.message.MessageConstant;
import nulp.pist21.blackjack.model.*;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.Deck;
import nulp.pist21.blackjack.model.managers.BetManager;
import nulp.pist21.blackjack.model.managers.PlayManager;
import nulp.pist21.blackjack.model.managers.SitManager;
import nulp.pist21.blackjack.model.managers.WinManager;
import nulp.pist21.blackjack.server.actor.message.*;
import nulp.pist21.blackjack.server.actor.utility.Transaction;

import java.util.*;

import static nulp.pist21.blackjack.message.MessageConstant.*;
import static nulp.pist21.blackjack.model.managers.PlayManager.Actions.HIT;
import static nulp.pist21.blackjack.model.managers.PlayManager.Actions.STAND;

public class TableActor extends AbstractActor {

    static public Props props(TableInfo tableInfo) {
        return Props.create(TableActor.class, () -> new TableActor(tableInfo));
    }

    private ActorRef[] players;
    private List<ActorRef> watchers;

    private TableInfo tableInfo;

    private SitManager sitManager;
    private BetManager betManager;
    private PlayManager playManager;
    private WinManager winManager;

    private Sit[] currentPlaySits;

    private Timer timer;
    private long delay;

    public TableActor(TableInfo tableInfo) {
        players = new ActorRef[tableInfo.getMaxPlayerCount()];
        watchers = new ArrayList<>();
        this.tableInfo = tableInfo;
        this.sitManager = new SitManager(tableInfo.getMaxPlayerCount());
        this.betManager = new BetManager(tableInfo.getMin(), tableInfo.getMax());
        this.playManager = new PlayManager(new Deck(2), new Dealer());
        this.winManager = new WinManager();
        this.delay = 30000;
        timer = new Timer();
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
                    boolean wasEmpty = sitManager.isEmpty();
                    if(!sitManager.getSits()[message.place].sit(message.user)){
                        sender.tell(new SitTableResponse(false), getSelf());
                        return;
                    }
                    players[message.place] = sender;

                    if (wasEmpty){
                        startRound();
                    }

                    sender.tell(new SitTableResponse(true), getSelf());
                })
                .match(StandTableRequest.class, message -> {
                    ActorRef sender = getSender();
                    sitManager.getSits()[message.place].makeFree();

                    players[message.place] = null;

                    sender.tell(new StandTableResponse(true), getSelf());
                })
                .match(PlayerAction.class, message -> {
                    if (players[message.place] != getSender()) {
                        return;
                    }
                    int place = -1;


                    switch (message.action) {
                        case ACTION_BET:
                            if (message.place != place) {
                                return;
                            }
                            handleBetAction(message.bet);
                            break;
                        case ACTION_HIT:
                            if (message.place != place) {
                                return;
                            }
                            handlePlayAction(HIT);
                            break;
                        case ACTION_STAND:
                            if (message.place != place) {
                                return;
                            }
                            handlePlayAction(STAND);
                    }
                })
                .build();
    }

    private int getCurrentIndex(){
        if (!betManager.isOver()){
            return betManager.getIndex();
        }
        if (!playManager.isOver()) {
            return playManager.getIndex();
        }
        return -1;
    }

    private void handleBetAction(int bet){
        if (betManager.isOver()){
            return;
        }
        if (!betManager.next(bet)) {
            return;
        }
        User currentUser = currentPlaySits[getCurrentIndex()].getUser();
        Transaction.take(currentUser, bet);
        timer.cancel();
        notifyWatchers();
        notifyCurrentPlayer();
        if (betManager.isOver()){
            playManager.start(currentPlaySits.length);
            if (playManager.isOver()){
                return;
            }
        }
        timer.schedule(new TimedOutAction(), delay);
    }

    private void handlePlayAction(PlayManager.Actions action) {
        if (playManager.isOver()){
            return;
        }
        if (!playManager.next(action)){
            return;
        }
        timer.cancel();
        notifyWatchers();
        notifyCurrentPlayer();

        if (!playManager.isOver()){
            timer.schedule(new TimedOutAction(), delay);
            return;
        }

        double koefs[] = winManager.start(playManager.getHands(), playManager.getHands().length - 1);

        for (int i = 0; i < currentPlaySits.length; i++){
            Sit sit = currentPlaySits[i];
            User user = sit.getUser();
            int bet = betManager.getBanks()[i];
            double koef = koefs[i];
            Transaction.giveMoney(user, (int)Math.round(bet * koef));
        }

        //todo: tell that round is over

        if (!sitManager.isEmpty()){
            startRound();
        }
    }

    private void startRound(){
        currentPlaySits = sitManager.getPlayingSits();
        betManager.start(currentPlaySits.length);

        notifyWatchers();
        notifyCurrentPlayer();
        timer.schedule(new TimedOutAction(), delay);
    }

    private void notifyWatchers(){
        watchers.forEach(w -> {
            w.tell(new TableUpdate(getTableFullInfo()), getSelf());
        });
    }

    private void notifyCurrentPlayer(){
        Sit currentSit = currentPlaySits[getCurrentIndex()];
        int sitIndex = Arrays.asList(sitManager.getPlayingSits()).indexOf(currentSit);
        ActorRef currentPlayer = players[getCurrentIndex()];
        if (!betManager.isOver()){
            currentPlayer.tell(new WaitAction(tableInfo, sitIndex, MessageConstant.ACTION_WAIT_BET), getSelf());
        }
        else if (!playManager.isOver()){
            currentPlayer.tell(new WaitAction(tableInfo, sitIndex, MessageConstant.ACTION_WAIT_HIT_OR_STAND), getSelf());
        };
    }

    public TableFullInfo getTableFullInfo() {
        Card[] dealerHand = playManager.getHands()[playManager.getHands().length - 1].getHand();

        Player[] players = new Player[tableInfo.getMaxPlayerCount()];
        for (int i = 0; i < currentPlaySits.length; i++){
            int index = Arrays.asList(sitManager.getSits()).indexOf(currentPlaySits[i]);
            User user = currentPlaySits[i].getUser();
            players[index] = new Player();
            players[index].setName(user.getName());
            players[index].setCash(user.getCash());
            players[index].setHand(playManager.getHands()[i].getHand());
        }


        return new TableFullInfo(dealerHand, players, getCurrentIndex());
    }

    private class TimedOutAction extends TimerTask{
        @Override
        public void run() {
            if (!betManager.isOver()){
               handleBetAction(betManager.getMinBet());
               timer.schedule(new TimedOutAction(), delay);
            }
            else if (!playManager.isOver()){
                handlePlayAction(PlayManager.Actions.STAND);
                if (!playManager.isOver()){
                    timer.schedule(new TimedOutAction(), delay);
                }
            }
        }
    }

}

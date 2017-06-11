package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.actions.Action;
import nulp.pist21.blackjack.model.deck.IDeck;
import nulp.pist21.blackjack.model.game.round.BetRound;
import nulp.pist21.blackjack.model.game.Dealer;
import nulp.pist21.blackjack.model.game.round.IRound;
import nulp.pist21.blackjack.model.game.round.GameRound;

import java.util.Arrays;

public class Table {

    private TableBox[] boxes;
    private TableBox[] playingBoxes;
    private Dealer dealer;

    private IDeck deck;

    private BetRound betRound;
    private GameRound gameRound;
    private IRound currentRound;

    private int minBet;
    private int maxBet;

    public Table(int boxes, IDeck deck, int minBet, int maxBet) {
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.deck = deck;
        this.dealer = new Dealer();

        this.minBet = minBet;
        this.maxBet = maxBet;
    }

    public boolean doAction(Action action){
        if (currentRound == null){
            return false;
        }

        if (currentRound.isEnd()){
            if (currentRound instanceof BetRound){
                gameRound = new GameRound(playingBoxes, deck, dealer);
                currentRound = gameRound;
            }
            else if (currentRound instanceof GameRound){
                return false;
            }
        }
        currentRound.next(action);
        return true;
    }

    public IRound getCurrentRound() {
        return currentRound;
    }

    public boolean isRoundOver(){
        return currentRound == gameRound && currentRound.isEnd();
    }

    public void startRound(){
        playingBoxes = Arrays.stream(boxes).filter(box -> box.isActivated()).toArray(TableBox[]::new);
        betRound = new BetRound(playingBoxes, minBet, maxBet);
        currentRound = betRound;
    }

    public TableBox getCurrentBox(){
        return playingBoxes[currentRound.getIndex()];
    }

    public TableBox[] getPlayingBoxes() {
        return playingBoxes;
    }

    public int getCurrentIndex(){
        return Arrays.asList(boxes).indexOf(getCurrentBox());
    }

    public TableBox[] getBoxes() {
        return boxes;
    }

    public IDeck getDeck() {
        return deck;
    }

    public Dealer getDealer() {
        return dealer;
    }
}

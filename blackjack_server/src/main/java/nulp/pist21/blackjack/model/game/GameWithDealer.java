package nulp.pist21.blackjack.model.game;

import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.game.round.BetRound;
import nulp.pist21.blackjack.model.game.round.GameRound;
import nulp.pist21.blackjack.model.game.round.IRound;
import nulp.pist21.blackjack.model.table.Table;
import nulp.pist21.blackjack.model.table.TableBox;

import java.util.Arrays;

public class GameWithDealer implements IGame {
    private BetRound betRound;
    private GameRound gameRound;
    private IRound currentRound;

    private Table table;
    private TableBox[] playingBoxes;
    private Dealer dealer;



    public GameWithDealer(Table table) {
        this.table = table;
        this.dealer = new Dealer();
        this.betRound = new BetRound(new IBetable[0], 0, 1);
        this.gameRound = new GameRound(new IHand[0], new EndlessDeck(), dealer);
        this.currentRound = betRound;
        start();
    }

    @Override
    public IRound getCurrentRound() {
        if (currentRound != null && currentRound.isEnd()){
            if (currentRound == betRound){
                gameRound = new GameRound(playingBoxes, table.getDeck(), dealer);
                currentRound = gameRound;
            }
            else if (currentRound == gameRound){
                currentRound = null;
            }
        }
        return currentRound;
    }

    @Override
    public boolean isOver(){
        return getCurrentRound() == null;
    }

    @Override
    public void start(){
        gameRound.end();
        betRound.end();
        playingBoxes = Arrays.stream(table.getBoxes()).filter(box -> box.isActivated()).toArray(TableBox[]::new);
        betRound = new BetRound(playingBoxes, table.getMinBet(), table.getMaxBet());
        currentRound = betRound;
    }

    @Override
    public int getCurrentIndex() {
        if (isOver()){
            return -1;
        }
        return Arrays.asList(table.getBoxes()).indexOf(getCurrentBox());
    }

    @Override
    public TableBox getCurrentBox() {
        return playingBoxes[currentRound.getIndex()];
    }

    public Dealer getDealer() {
        return dealer;
    }
}

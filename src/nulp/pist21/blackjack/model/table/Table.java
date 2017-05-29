package nulp.pist21.blackjack.model.table;

import javafx.util.Pair;
import nulp.pist21.blackjack.model.*;
import nulp.pist21.blackjack.model.deck.IDeck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private int rate;
    private TableBox[] boxes;

    private GameWithDealer game;
    private IDeck deck;

    private List<ISpectator> spectators;

    public Table(int rate, int boxes, IDeck deck) {
        this.rate = rate;
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.deck = deck;
        game = new GameWithDealer();
        spectators = new ArrayList<>();
    }

    public int getRate() {
        return rate;
    }

    public TableBox[] getBoxes() { return boxes; }

    public boolean sitDown(IPlayer player, int i) {
        if (i >= 0 && i < boxes.length)
            return boxes[i].sitDown(player);
        return false;
    }

    public boolean standUp(IPlayer player, int i) {
        if (i >= 0 && i < boxes.length)
            return boxes[i].standUp(player);
        return false;
    }

    public List<ISpectator> getSpectators() {
        return spectators;
    }

    public void addSpectator(ISpectator spectator) {
        spectators.add(spectator);
    }

    public void removePlayer(IPlayer player) { // on exit
        spectators.remove(player);
        for (TableBox box : boxes) {
            if (box.getPlayer() == player)
            box.makeFree();
        }
    }

    public IDeck getDeck() {
        return deck;
    }

    public GameWithDealer getGame() {
        return game;
    }

    public void takeBets(){
        TableBox[] playingBoxes = Arrays.stream(boxes).filter(b -> !b.isFree()).toArray(TableBox[]::new);
        for (TableBox box: playingBoxes) {
            box.setBet(rate);
        }

        game.start(playingBoxes);
    }

    public void startRound(){
        game.giveFirstCards(deck);
    }

    public void endRound(){
        for (Pair<TableBox, Float> winner: game.getWinners()){
            TableBox winnerBox = winner.getKey();
            Float koef = winner.getValue();
            winnerBox.getPlayer().giveMoney(Math.round(winnerBox.getBet() * koef));
        }
        for (TableBox box: boxes){
            box.setBet(0);
            box.takeCards();
        }
    }

}

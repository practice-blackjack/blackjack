package nulp.pist21.blackjack.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int id;
    private String name;
    private int rate;
    private TableBox[] boxes;
    private GameWithDealer game;
    private IDeck deck;

    private List<ISpectator> spectators;

    public Table(int id, String name, int rate, int boxes, IDeck deck) {
        this.name = name;
        this.rate = rate;
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.deck = deck;
        game = new GameWithDealer();
        spectators = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }

    public TableBox[] getBoxes() { return boxes; }

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

    public void takeBets(){
        ArrayList<TableBox> playingBoxes = new ArrayList<>();
        for (TableBox box: boxes){
            if (box.isFree()) continue;

            box.setBet(rate);
            playingBoxes.add(box);
        }
        //temporary
        TableBox[] playingBoxesArr = new TableBox[playingBoxes.size()];
        for (int i = 0; i < playingBoxes.size(); i++){
            playingBoxesArr[i] = playingBoxes.get(i);
        }
        game.start(playingBoxesArr);
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

    public int getId() {
        return id;
    }

}

package nulp.pist21.blackjack.model.table;

import nulp.pist21.blackjack.model.table.deck.IDeck;
import nulp.pist21.blackjack.model.table.game.IGame;

public class Table {
    private TableBox[] boxes;
    private IGame game;
    private IDeck deck;

    public Table(int boxes, IGame game, IDeck deck) {
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.game = game;
        this.deck = deck;
    }

    public TableBox getBox(int index) {
        if (index >= 0 && index < boxes.length){
            return boxes[index];
        }
        return null;
    }
    public int getBoxCount(){
        return boxes.length;
    }

    public IGame getGame() {
        return game;
    }
}

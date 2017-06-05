package model.table;

import model.table.game.GameWithDealer;

public class Table {
    private TableBox[] boxes;
    private GameWithDealer game;

    public Table(int boxes, GameWithDealer game) {
        this.boxes = new TableBox[boxes];
        for (int i = 0; i < boxes; i++) {
            this.boxes[i] = new TableBox();
        }
        this.game = game;
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

    public GameWithDealer getGame() {
        return game;
    }
}

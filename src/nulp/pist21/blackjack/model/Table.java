package nulp.pist21.blackjack.model;

public class Table {

    private TableInfo tableInfo;
    private TableBox[] sits;
    private AbstractPlayer dealer;

    public Table(){
        tableInfo = new TableInfo("name", 9, 6, 10, 20);
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void startRound() {

    }

    public boolean sitDown(Player player, int place) {
        return false;
    }

    public boolean standUp (Player player) {
        return false;
    }

}

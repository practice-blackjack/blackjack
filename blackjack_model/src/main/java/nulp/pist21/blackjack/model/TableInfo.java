package nulp.pist21.blackjack.model;

public class TableInfo {

    private String name;
    private int maxPlayerCount;
    private int playerCount;

    private int max;
    private int min;

    public TableInfo() {
        this("", 0, 0, 0, 0);
    }

    public TableInfo(String name, int maxPlayerCount, int playerCount, int max, int min) {
        this.name = name;
        this.maxPlayerCount = maxPlayerCount;
        this.playerCount = playerCount;
        this.max = max;
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

}
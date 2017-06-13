package nulp.pist21.blackjack.model.managers;

public class BetManager {
    private int banks[];
    private int index;
    private int minBet;
    private int maxBet;

    public BetManager(int minBet, int maxBet) {
        this.banks = new int[0];
        this.minBet = minBet;
        this.maxBet = maxBet;
    }

    public void start(int bankCount){
        this.banks = new int[bankCount];
        index = 0;
    }

    public boolean next(int money) {
        if (index >= banks.length) {
            return false;
        }
        if (money < minBet || money > maxBet){
            return false;
        }
        banks[index] = money;
        index++;
        return true;
    }

    public int getIndex() {
        return index;
    }

    public int[] getBanks() {
        return banks;
    }

    public boolean isOver() {
        return index >= banks.length;
    }

    public int getMinBet() {
        return minBet;
    }

    public int getMaxBet() {
        return maxBet;
    }
}

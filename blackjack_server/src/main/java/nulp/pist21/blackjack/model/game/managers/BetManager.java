package nulp.pist21.blackjack.model.game.managers;

import nulp.pist21.blackjack.model.game.IBank;

public class BetManager {
    private IBank banks[];
    private int index;
    private int minBet;
    private int maxBet;

    public BetManager(int minBet, int maxBet) {
        this.banks = new IBank[0];
        this.minBet = minBet;
        this.maxBet = maxBet;
    }

    public void start(IBank banks[]){
        end();
        this.banks = banks;
        index = 0;
    }

    public boolean next(int money) {
        if (index >= banks.length) {
            return false;
        }
        if (money < minBet || money > maxBet){
            return false;
        }
        banks[index].setBet(money);
        index++;
        return true;
    }

    public void end() {
        for (IBank player: banks) {
            player.setBet(0);
        }
    }

    public IBank getCurrentBank() {
        return banks[index];
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

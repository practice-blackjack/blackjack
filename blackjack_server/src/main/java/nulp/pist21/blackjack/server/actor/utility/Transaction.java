package nulp.pist21.blackjack.server.actor.utility;

import javax.management.OperationsException;

public class Transaction {
    public static boolean canTake(ITransactable from, int cash){
        if (from.getCash() - cash >= 0){
            return true;
        }
        return false;
    }

    public static void take(ITransactable from, int cash){
        if (!canTake(from, cash)){

        }
        from.setCash(from.getCash() - cash);
    }

    public static void giveMoney(ITransactable to, int cash){
        to.setCash(to.getCash() + cash);
    }

    public static void transact(ITransactable from, ITransactable to, int cash){
        take(from, cash);
        giveMoney(to, cash);
    }
}

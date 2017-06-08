package nulp.pist21.blackjack.model;

import mock.UserMock;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.game.Combination;
import nulp.pist21.blackjack.model.game.IRound;
import nulp.pist21.blackjack.model.game.Round;
import nulp.pist21.blackjack.model.table.Table;
import org.junit.Test;

public class ModelTest {

    @Test
    public void should_correctly_play_round(){

        UserMock users[] = new UserMock[]{
                new UserMock(14),
                new UserMock(16),
                new UserMock(18),
        };

        Table table = new Table(3, new EndlessDeck());

        table.getBox(0).isActivated(true);
        table.getBox(1).isActivated(true);
        table.getBox(2).isActivated(true);

        IRound round = table.startRound();

        System.out.println("Round started.");
        round.start();
        System.out.println();

        System.out.println("Dealers cards: ");
        for (Card card: round.getPlayer(Round.DEALER_INDEX).getHand()) {
            System.out.println("   " + card.toString());
        }
        System.out.println("   Points: " + new Combination(round.getPlayer(Round.DEALER_INDEX)));
        System.out.println();

        for (int i = 0; i < 3; i++){
            System.out.println("Player " + (i + 1) + " cards: ");
            for (Card card: round.getPlayer(i).getHand()) {
                System.out.println("   " + card.toString());
            }
            System.out.println("Points: " + new Combination(round.getPlayer(i)));
            System.out.println();
        }

        GameAction action;
        boolean canNext;
        do{
            int userId = round.getCurrentIndex();
            action = users[userId].doStep(round, userId);
            canNext = round.next(action, new EndlessDeck());

            System.out.println("Players " + (userId + 1) + " turn. ");

            System.out.println("   Action: " + action.getAction());

            System.out.println("   Cards: ");
            for (Card card: round.getPlayer(userId).getHand()) {
                System.out.println("      " + card.toString());
            }

            System.out.println("   Points: " + new Combination(round.getPlayer(userId)));

            System.out.println();
        } while (canNext);

        System.out.println("Dealers cards: ");
        for (Card card: round.getPlayer(Round.DEALER_INDEX).getHand()) {
            System.out.println("   " + card.toString());
        }
        System.out.println("Points: " + new Combination(round.getPlayer(Round.DEALER_INDEX)));
        System.out.println();

        for (int i = 0; i < 3; i++){
            double kof = new Combination(round.getPlayer(i)).getWin(new Combination(round.getPlayer(Round.DEALER_INDEX)));
            System.out.println("Players " + (i + 1) + " win kof: " + kof);
        }

        System.out.println("Round over.");
    }
}

package nulp.pist21.blackjack.model;

import mock.UserMock;
import nulp.pist21.blackjack.model.actions.Action;
import nulp.pist21.blackjack.model.actions.BetAction;
import nulp.pist21.blackjack.model.actions.GameAction;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.game.calculating.Combination;
import nulp.pist21.blackjack.model.table.Table;
import org.junit.Assert;
import org.junit.Test;

public class ModelTest {

    @Test
    public void should_correctly_play_round(){

        UserMock users[] = new UserMock[]{
                new UserMock(14, 100),
                new UserMock(16, 200),
                new UserMock(18, 300),
        };

        Table table = new Table(3, new EndlessDeck(), 100, 300);

        table.getBoxes()[0].isActivated(true);
        table.getBoxes()[1].isActivated(true);
        table.getBoxes()[2].isActivated(true);

        table.startRound();
        System.out.println("GameRound started.");
        System.out.println();

        System.out.println("Getting bets.");
        for (int i = 0; i < 3; i++){
            BetAction action = (BetAction) users[i].doStep(table);
            System.out.println("Player " + (i + 1) + " bet: " + action.getBet());
            Assert.assertTrue(table.doAction(action));
        }
        System.out.println("Bets taken.");
        System.out.println();


        System.out.println("Dealers cards: ");
        for (Card card: table.getDealer().getHand()) {
            System.out.println("   " + card.toString());
        }
        System.out.println("Points: " + new Combination(table.getDealer()));
        System.out.println();

        for (int i = 0; i < 3; i++){
            System.out.println("Player " + (i + 1) + " cards: ");
            for (Card card: table.getBoxes()[i].getHand()) {
                System.out.println("   " + card.toString());
            }
            System.out.println("Points: " + new Combination(table.getBoxes()[i]));
            System.out.println();
        }

        System.out.println("Game started.");
        while (!table.isRoundOver()){
            int userId = table.getCurrentIndex();
            GameAction action = (GameAction) users[userId].doStep(table);
            Assert.assertTrue(table.doAction(action));

            System.out.println("Players " + (userId + 1) + " turn. ");

            System.out.println("   Action: " + action.getAction());

            System.out.println("   Cards: ");
            for (Card card: table.getBoxes()[userId].getHand()) {
                System.out.println("      " + card.toString());
            }

            System.out.println("   Points: " + new Combination(table.getBoxes()[userId]));

            System.out.println();
        }

        System.out.println("Dealers cards: ");
        for (Card card: table.getDealer().getHand()) {
            System.out.println("   " + card.toString());
        }
        System.out.println("Points: " + new Combination(table.getDealer()));
        System.out.println();

        for (int i = 0; i < 3; i++){
            double kof = new Combination(table.getBoxes()[i]).getWin(new Combination(table.getDealer()));
            System.out.println("Players " + (i + 1) + " win kof: " + kof);
        }

        System.out.println("GameRound over.");
    }
}

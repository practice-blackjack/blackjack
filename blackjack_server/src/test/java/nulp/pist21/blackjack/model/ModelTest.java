package nulp.pist21.blackjack.model;

import mock.UserMock;
import nulp.pist21.blackjack.model.deck.Card;
import nulp.pist21.blackjack.model.deck.EndlessDeck;
import nulp.pist21.blackjack.model.game.Dealer;
import nulp.pist21.blackjack.model.game.calculating.Combination;
import nulp.pist21.blackjack.model.game.managers.BetManager;
import nulp.pist21.blackjack.model.game.managers.PlayManager;
import nulp.pist21.blackjack.model.table.Table;
import nulp.pist21.blackjack.model.table.TableBox;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ModelTest {

    @Test
    public void should_correctly_play_round(){

        UserMock users[] = new UserMock[]{
                new UserMock(14, 100),
                new UserMock(16, 200),
                new UserMock(18, 300),
        };

        Table table = new Table(3);

        table.getBoxes()[0].activate(users[0]);
        table.getBoxes()[1].activate(users[1]);
        table.getBoxes()[2].activate(users[2]);

        BetManager bets = new BetManager(100, 300);
        PlayManager play = new PlayManager(new EndlessDeck(), new Dealer());
        TableBox playingBoxes[] = table.getPlayingBoxes();

        System.out.println("GameRound started.");
        System.out.println();
        bets.start(playingBoxes);

        System.out.println("Getting bets.");
        for (int i = 0; i < 3; i++){
            int bet = users[i].doBet(bets);
            System.out.println("Player " + (i + 1) + " bet: " + bet);
            Assert.assertTrue(bets.next(bet));
        }
        System.out.println("Bets taken.");
        System.out.println();

        play.start(playingBoxes);

        System.out.println("Dealers cards: ");

        for (Card card: play.getDealer().getHand()) {
            System.out.println("   " + card.toString());
        }
        System.out.println("Points: " + new Combination(play.getDealer()));
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
        while (!play.isOver()){
            int userId = Arrays.asList(table.getBoxes()).indexOf(play.getCurrentHand());
            PlayManager.Actions action = users[userId].doStep(play);
            Assert.assertTrue(play.next(action));

            System.out.println("Players " + (userId + 1) + " turn. ");

            System.out.println("   Action: " + action);

            System.out.println("   Cards: ");
            for (Card card: table.getBoxes()[userId].getHand()) {
                System.out.println("      " + card.toString());
            }

            System.out.println("   Points: " + new Combination(table.getBoxes()[userId]));

            System.out.println();
        }

        System.out.println("Dealers cards: ");
        for (Card card: play.getDealer().getHand()) {
            System.out.println("   " + card.toString());
        }
        System.out.println("Points: " + new Combination(play.getDealer()));
        System.out.println();

        for (int i = 0; i < 3; i++){
            double kof = new Combination(table.getBoxes()[i]).getWin(new Combination(play.getDealer()));
            System.out.println("Players " + (i + 1) + " win kof: " + kof);
        }

        System.out.println("GameRound over.");
    }
}

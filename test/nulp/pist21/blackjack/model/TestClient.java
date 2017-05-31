package nulp.pist21.blackjack.model;

import javafx.util.Pair;
import mock.UserMock;
import nulp.pist21.blackjack.model.table.DealerBox;
import nulp.pist21.blackjack.model.table.deck.EndlessDeck;
import nulp.pist21.blackjack.model.table.game.GameWithDealer;
import nulp.pist21.blackjack.model.table.Table;

import java.util.*;

class TestClient {
    static Scanner in = new Scanner(System.in);
    static Table table;
    static List<Pair<String, UserMock>> users;

    static boolean workWithUser(UserMock user){
        String command = in.next();

        if (command.equals("stand_up")){
            int box = Integer.parseInt(in.next());
            //table.standUp(user, box);
            return true;
        }
        else if (command.equals("sit_down")){
            int box = Integer.parseInt(in.next());
            //table.sitDown(user, box);
            return true;
        }
        return false;
    }

    static void showTable(){
        //System.out.println("Rate: " + table.getRate());
    }

    public static void main(String[] args) {
        table = new Table(3, new GameWithDealer(new EndlessDeck(), new DealerBox()));
        users = new ArrayList<>();
        users.add(new Pair<>("p1", new UserMock(500)));
        users.add(new Pair<>("p2", new UserMock(700)));
        users.add(new Pair<>("p3", new UserMock(1000)));


        while (true){
            String message = in.next();
            boolean handled = false;

            if (message.equals("exit")){
                break;
            }
            else if (message.equals("show")){
                showTable();
                handled = true;
            }
            else {
                for (Pair<String, UserMock> user: users){
                    if (message.equals(user.getKey()) && workWithUser(user.getValue())){
                        handled = true;
                    }
                }

            }

            if (!handled){
                System.out.println("Wrong input");
            }
        }
    }
}

package nulp.pist21.blackjack.model;

public class Player implements IPlayer {
    private User user;

    public Player(User user) {
        this.user = user;
    }

    @Override
    public Action getAction() {
        return null;
    }

    public User getUser() {
        return user;
    }
}

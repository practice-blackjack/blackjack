package nulp.pist21.blackjack.model.actions;

public class GameAction implements Action {
    public enum Actions{
        STAND,
        HIT,
    }

    private Actions action;

    public GameAction(Actions action){
        this.action = action;
    }

    public Actions getAction() {
        return action;
    }
}

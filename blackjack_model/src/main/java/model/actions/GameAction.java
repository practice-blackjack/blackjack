package model.actions;

public class GameAction {
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

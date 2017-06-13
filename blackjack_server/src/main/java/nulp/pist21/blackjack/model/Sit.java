package nulp.pist21.blackjack.model;

public class Sit {

    private User user;
    public Sit() {
        user = null;
    }

    public boolean isActivated(){
        return user != null;
    }

    public boolean sit(User player){
        if (this.user != null){
            return false;
        }
        this.user = player;
        return true;
    }

    public boolean stand(User user){
        if (this.user != user){
            return false;
        }
        this.user = null;
        return true;
    }

    public User getUser(){
        return user;
    }

    public void makeFree(){
        user = null;
    }
}

package nulp.pist21.blackjack.message;

public class BooleanMessage extends Message {

    private boolean isOk;

    public BooleanMessage() {
        this("", false);
    }

    public BooleanMessage(String type, boolean isOk) {
        super(type);
        this.isOk = isOk;
    }

    public void setOk(boolean isOk) {
        this.isOk = isOk;
    }

    public boolean isOk() {
        return isOk;
    }

}

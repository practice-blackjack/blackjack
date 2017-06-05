package nulp.pist21.blackjack.model;

public class User {

    private String name;
    private String password;
    private int cash;

    public User() {

    }

    public User(String name, String password) {
        update(name, password, 0);
    }

    public User(String name, String password, int cash) {
        update(name, password, cash);
    }

    public User(String name) {
        update(name, "", 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void update(String name, String password, int cash) {
        this.name = name;
        this.password = password;
        this.cash = cash;
    }

}
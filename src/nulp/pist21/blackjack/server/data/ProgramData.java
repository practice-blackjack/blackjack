package nulp.pist21.blackjack.server.data;

public class ProgramData {

    private static final ProgramData programData = new ProgramData();

    private ProgramData() {}

    public static ProgramData get() {
        return programData;
    }

    public UserList userList = new UserList();
    public TokenList tokenList = new TokenList();

}

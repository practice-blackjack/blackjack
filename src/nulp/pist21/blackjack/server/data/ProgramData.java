package nulp.pist21.blackjack.server.data;

import nulp.pist21.blackjack.model.Table;

public class ProgramData {

    private static final ProgramData programData = new ProgramData();

    private ProgramData() {
        for (int i = 0; i < 10; i++) tableManager.addTable(new Table());
    }

    public static ProgramData get() {
        return programData;
    }

    public UserList userList = new UserList();
    public TokenList tokenList = new TokenList();
    public TableManager tableManager = new TableManager();
    public GameActionManager gameActionManager = new GameActionManager(tokenList);
    public CardManager cardManager = new CardManager(tokenList);

}

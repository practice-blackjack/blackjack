package nulp.pist21.blackjack.server.data;

public class ProgramData {

    private static final ProgramData programData = new ProgramData();

    private ProgramData() {
        //for (int i = 0; i < 10; i++) tableManager.addTable(new Table());
    }

    public static ProgramData get() {
        return programData;
    }

    public UserList userList = new UserList();
    public TokenList tokenList = new TokenList();
    public TableManager tableManager = new TableManager();



    public InitManager initManager = new InitManager(this);
    public LobbyManager lobbyManager = new LobbyManager(this);
    public WatchGameManager watchGameManager = new WatchGameManager(this);
    public PlayGameManager playGameManager = new PlayGameManager(this);

}

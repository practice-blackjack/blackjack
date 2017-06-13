package sample;

import com.alibaba.fastjson.JSON;
import javafx.stage.Stage;
import nulp.pist21.blackjack.client.endpoint.InitEndpoint;
import nulp.pist21.blackjack.client.endpoint.LobbyEndpoint;
import nulp.pist21.blackjack.client.endpoint.PlayGameEndpoint;
import nulp.pist21.blackjack.client.endpoint.WatchGameEndpoint;
import nulp.pist21.blackjack.message.*;
import nulp.pist21.blackjack.model.TableInfo;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import static nulp.pist21.blackjack.message.MessageConstant.*;

@SuppressWarnings("Duplicates")
public class ProgramData {

    private final static ProgramData programData = new ProgramData();

    public static ProgramData get() {
        return programData;
    }

    private ProgramData() {
        container = ContainerProvider.getWebSocketContainer();
    }

    private WebSocketContainer container;

    public InitEndpoint getInitEndpoint() {
        if (initEndpoint == null || !initEndpoint.isOpen()) {
            initInit();
        }
        return initEndpoint;
    }

    public LobbyEndpoint getLobbyEndpoint() {
        if (lobbyEndpoint == null || !lobbyEndpoint.isOpen()) {
            initLobby();
        }
        return lobbyEndpoint;
    }

    public WatchGameEndpoint getWatchGameEndpoint() {
        if (watchGameEndpoint == null || !watchGameEndpoint.isOpen()) {
            initWatchGame();
        }
        return watchGameEndpoint;
    }

    public PlayGameEndpoint getPlayGameEndpoint() {
        if (playGameEndpoint == null || !playGameEndpoint.isOpen()) {
            initPlayGame();
        }
        return playGameEndpoint;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stageRouter = new StageRouter(stage);
        this.stage = stage;
    }

    private InitEndpoint initEndpoint;
    private LobbyEndpoint lobbyEndpoint;
    private WatchGameEndpoint watchGameEndpoint;
    private PlayGameEndpoint playGameEndpoint;

    private long token;

    private Stage stage;

    private TableInfo currentTable;

    private StageRouter stageRouter;

    public void initInit() {
        try {
            initEndpoint = new InitEndpoint();
            container.connectToServer(initEndpoint, new URI("ws://localhost:8080/blackjack/init"));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void initLobby() {
        try {
            lobbyEndpoint = new LobbyEndpoint(token);
            lobbyEndpoint.onTokenCheckerMessageListener((BooleanMessage booleanMessage) -> {
                System.out.println("server > " + JSON.toJSONString(booleanMessage));
                if (!booleanMessage.isOk()) {
                    lobbyEndpoint.sendTokenMessage();
                }
            });
            container.connectToServer(lobbyEndpoint, new URI("ws://localhost:8080/blackjack/lobby"));
            lobbyEndpoint.onUpdateMessageListener((TableListMessage tableListMessage) -> {
                System.out.println("server > " + JSON.toJSONString(tableListMessage));
            });
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void initWatchGame() {
        try {
            watchGameEndpoint = new WatchGameEndpoint(token);
            watchGameEndpoint.onTokenCheckerMessageListener((BooleanMessage booleanMessage) -> {
                System.out.println("server > " + JSON.toJSONString(booleanMessage));
                if (!booleanMessage.isOk()) {
                    watchGameEndpoint.sendTokenMessage();
                }
            });
            container.connectToServer(watchGameEndpoint, new URI("ws://localhost:8080/blackjack/game/watch"));
            watchGameEndpoint.onUpdateMessageListener((TableFullInfoMessage tableFullInfoMessage) -> {
                System.out.println("server > " + JSON.toJSONString(tableFullInfoMessage));
            });
            watchGameEndpoint.onUserActionMessageListener((UserActionMessage userActionMessage) -> {
                System.out.println("server > " + JSON.toJSONString(userActionMessage));
            });
            watchGameEndpoint.onResultListener((ResultMessage resultMessage) -> {
                System.out.println("server > " + JSON.toJSONString(resultMessage));
            });
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void initPlayGame() {
        try {
            playGameEndpoint = new PlayGameEndpoint(token);
            playGameEndpoint.onTokenCheckerMessageListener((BooleanMessage booleanMessage) -> {
                System.out.println("server > " + JSON.toJSONString(booleanMessage));
                if (!booleanMessage.isOk()) {
                    playGameEndpoint.sendTokenMessage();
                }
            });
            container.connectToServer(playGameEndpoint, new URI("ws://localhost:8080/blackjack/game/play"));
            playGameEndpoint.onWaitActionMessageListener((WaitMessage waitMessage) -> {
                System.out.println("server > " + JSON.toJSONString(waitMessage));
                TableInfo tableInfo = waitMessage.getTableInfo();
                int place = waitMessage.getPlace();
                Scanner scn = new Scanner(System.in);
                switch (waitMessage.getWaitType()) {
                    case ACTION_WAIT_BET:
                        int bet = scn.nextInt();
                        playGameEndpoint.sendActionMessage(tableInfo, place, bet);
                        break;
                    case ACTION_WAIT_HIT_OR_STAND:
                        String hitOrStand = scn.nextLine();
                        playGameEndpoint.sendActionMessage(tableInfo, place, hitOrStand);
                        break;
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public TableInfo getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(TableInfo currentTable) {
        this.currentTable = currentTable;
    }

    public StageRouter getStageRouter() {
        return stageRouter;
    }
}

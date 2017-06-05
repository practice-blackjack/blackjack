package nulp.pist21.blackjack.message;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

@FunctionalInterface
public interface MessageFunction <T extends Message> {

    void apply(T message);

}

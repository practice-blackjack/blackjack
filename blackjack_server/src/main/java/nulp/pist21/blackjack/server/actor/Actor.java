package nulp.pist21.blackjack.server.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import nulp.pist21.blackjack.server.data.TableManager;
import nulp.pist21.blackjack.server.data.TokenManager;
import nulp.pist21.blackjack.server.data.UserManager;

public class Actor {

    public final static ActorSystem system = ActorSystem.create("blackjack");
    public final static ActorRef userManager = system.actorOf(UserManager.props());
    public final static ActorRef tokenManager = system.actorOf(TokenManager.props());
    public final static ActorRef tableManager = system.actorOf(TableManager.props());

}


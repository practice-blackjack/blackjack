package nulp.pist21.blackjack.message;

@FunctionalInterface
public interface MessageFunction <T extends StringMessage> {

    void apply(T message);

}

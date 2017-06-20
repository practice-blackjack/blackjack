package nulp.pist21.blackjack.message;

@FunctionalInterface
public interface MessageFunction <T extends Message> {

    void apply(T message);

}

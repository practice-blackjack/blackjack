import nulp.pist21.blackjack.model.*;
import org.junit.Before;
import org.junit.Test;

public class TableTest {

    @Test
    public void instance_should_create(){
        ITableBox[] boxes = {
                new TableBoxSingle(),
                new TableBoxSingle(),
                new TableBoxSingle(),
                new TableBoxSingle(),
                new TableBoxSingle()};
        IDeck deck = new EndlessDeck();
        Table table = new Table("Kyiv", 100, boxes, deck);
    }

    @Test
    public void ololo(){

    }
}

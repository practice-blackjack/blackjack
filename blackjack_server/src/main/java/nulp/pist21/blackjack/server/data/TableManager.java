package nulp.pist21.blackjack.server.data;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import nulp.pist21.blackjack.model.Table;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.server.actor.Actor;
import nulp.pist21.blackjack.server.actor.KernelActor;
import nulp.pist21.blackjack.server.actor.TableActor;
import nulp.pist21.blackjack.server.actor.message.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableManager extends AbstractActor {

    static public Props props() {
        return Props.create(TableManager.class, () -> new TableManager());
    }

    public TableManager() {
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TableListRequest.class, greeting -> {
                    List<TableInfo> tablesInfo = getTablesInfo();
                    getSender().tell(new TableListResponse(tablesInfo), getSelf());
                })
                .match(EntryTableRequest.class, message -> {
                    ActorRef tableActor = getTableActor(message.tableInfo);
                    if (tableActor != null) {
                        tableActor.tell(message, getSender());
                    }
                })
                .match(ExitTableRequest.class, message -> {
                    ActorRef tableActor = getTableActor(message.tableInfo);
                    if (tableActor != null) {
                        tableActor.tell(message, getSender());
                    }
                })
                .match(SitTableRequest.class, message -> {
                    ActorRef tableActor = getTableActor(message.tableInfo);
                    if (tableActor != null) {
                        tableActor.tell(message, getSender());
                    }
                })
                .match(StandTableRequest.class, message -> {
                    ActorRef tableActor = getTableActor(message.tableInfo);
                    if (tableActor != null) {
                        tableActor.tell(message, getSender());
                    }
                })
                .match(PlayerAction.class, message -> {
                    ActorRef tableActor = getTableActor(message.tableInfo);
                    if (tableActor != null) {
                        tableActor.tell(message, getSender());
                    }
                })
                .build();
    }

    private List<Table> tableList = new ArrayList<>();
    private List<ActorRef> tableActorList = new ArrayList<>();

    public void addTable(Table table) {
        tableList.add(table);
        ActorRef actorRef = Actor.system.actorOf(TableActor.props(table));
        tableActorList.add(actorRef);
    }

    public List<TableInfo> getTablesInfo() {
        return tableList.stream().map(Table::getTableInfo).collect(Collectors.toList());
    }

    public ActorRef getTableActor(TableInfo tableInfo) {
        for (int i = 0; i < tableList.size(); i++) {
            Table t = tableList.get(i);
            //todo: equals
            if (t.getTableInfo().equals(tableInfo)) {
                //todo: get ref
                ActorRef actorRef = tableActorList.get(i);
                return actorRef;
            }
        }
        return null;
    }

}

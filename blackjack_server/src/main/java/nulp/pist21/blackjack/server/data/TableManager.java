package nulp.pist21.blackjack.server.data;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import nulp.pist21.blackjack.model.TableInfo;
import nulp.pist21.blackjack.server.actor.Actor;
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
        addTable(new TableInfo("table 1", 6, 0, 50, 10));
        addTable(new TableInfo("table 2", 6, 0, 50, 10));
        addTable(new TableInfo("table 3", 6, 0, 50, 10));
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TableListRequest.class, message -> {
                    List<TableInfo> tablesInfo = getTableInfoList();
                    getSender().tell(new TableListResponse(tablesInfo), getSelf());
                })
                .match(EntryTableRequest.class, message -> {
                    ActorRef tableActor = getTableActor(message.tableInfo);
                    if (tableActor != null) {
                        tableActor.tell(message, getSender());
                    } else {
                        getSender().tell(new EntryTableResponse(false), getSelf());
                    }
                })
                .match(ExitTableRequest.class, message -> {
                    ActorRef tableActor = getTableActor(message.tableInfo);
                    if (tableActor != null) {
                        tableActor.tell(message, getSender());
                    } else {
                        getSender().tell(new ExitTableResponse(false), getSelf());
                    }
                })
                .match(SitTableRequest.class, message -> {
                    ActorRef tableActor = getTableActor(message.tableInfo);
                    if (tableActor != null) {
                        tableActor.tell(message, getSender());
                    } else {
                        getSender().tell(new SitTableResponse(false), getSelf());
                    }
                })
                .match(StandTableRequest.class, message -> {
                    ActorRef tableActor = getTableActor(message.tableInfo);
                    if (tableActor != null) {
                        tableActor.tell(message, getSender());
                    } else {
                        getSender().tell(new StandTableResponse(false), getSelf());
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

    private List<TableRecord> tableRecords = new ArrayList<>();

    public void addTable(TableInfo tableInfo) {
        ActorRef actorRef = Actor.system.actorOf(TableActor.props(tableInfo));
        tableRecords.add(new TableRecord(tableInfo, actorRef));
    }

    public ActorRef getTableActor(TableInfo tableInfo) {
        for (TableRecord tr : tableRecords) {
            TableInfo ti = tr.tableInfo;
            if (ti.equals(tableInfo)) {
                return tr.tableRef;
            }
        }
        return null;
    }

    public List<TableInfo> getTableInfoList() {
        return tableRecords.stream().map(tr -> tr.tableInfo).collect(Collectors.toList());
    }

    private class TableRecord {

        private final TableInfo tableInfo;
        private final ActorRef tableRef;

        private TableRecord(TableInfo tableInfo, ActorRef tableRef) {
            this.tableInfo = tableInfo;
            this.tableRef = tableRef;
        }

    }

}

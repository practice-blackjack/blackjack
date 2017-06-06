package nulp.pist21.blackjack.server.actor.message;

import nulp.pist21.blackjack.model.TableInfo;

import java.util.List;

public class TableListUpdateResponse {

    public final List<TableInfo> tableInfoList;

    public TableListUpdateResponse(List<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
    }

}

package nulp.pist21.blackjack.server.data;

import nulp.pist21.blackjack.model.Table;
import nulp.pist21.blackjack.model.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableManager {

    private List<Table> tableList = new ArrayList<>();

    public void addTable(Table table) {
        tableList.add(table);
    }

    public List<TableInfo> getTablesInfo() {
        return tableList.stream().map(Table::getTableInfo).collect(Collectors.toList());
    }

}

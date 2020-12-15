package com.holub.database;

import com.holub.database.visitors.SortVisitor;

import java.util.Collection;
import java.util.Map;

public class OrderBySelect extends SelectDecorator {
    private SelectInterface selectStatement;
    private Table notOrderedTable;
    private Map<String, Boolean> orderParams;

    public OrderBySelect(SelectInterface selectStatement, Map<String, Boolean> orderParams)
    {   this.selectStatement = selectStatement;
        this.orderParams = orderParams;
    }

    @Override
    public Table select(Selector where, String[] requestedColumns, Table[] other) {
        notOrderedTable = selectStatement.select(where, requestedColumns, other);
        return makeOrderedTable();
    }

    @Override
    public Table select(Selector where, String[] requestedColumns) {
        notOrderedTable = selectStatement.select(where, requestedColumns);
        return makeOrderedTable();
    }

    @Override
    public Table select(Selector where) {
        notOrderedTable = selectStatement.select(where);
        return makeOrderedTable();
    }

    @Override
    public Table select(Selector where, Collection requestedColumns, Collection other) {
        notOrderedTable = selectStatement.select(where, requestedColumns, other);
        return makeOrderedTable();
    }

    @Override
    public Table select(Selector where, Collection requestedColumns) {
        notOrderedTable = selectStatement.select(where, requestedColumns);
        return makeOrderedTable();
    }

    private Table makeOrderedTable() {
        return notOrderedTable.accept(new SortVisitor(orderParams));
    }
}

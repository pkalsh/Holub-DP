package com.holub.database;

import java.util.Collection;
import java.util.Iterator;

public class DistinctSelect extends SelectDecorator {
    private SelectInterface selectStatement;
    private Table notDistinctTable;

    public DistinctSelect(SelectInterface selectStatement) { this.selectStatement = selectStatement; }

    @Override
    public Table select(Selector where, String[] requestedColumns, Table[] other) {
        notDistinctTable = selectStatement.select(where, requestedColumns, other);
        return makeDistinctTable();
    }

    @Override
    public Table select(Selector where, String[] requestedColumns) {
        notDistinctTable = selectStatement.select(where, requestedColumns);
        return makeDistinctTable();
    }

    @Override
    public Table select(Selector where) {
        notDistinctTable = selectStatement.select(where);
        return makeDistinctTable();
    }

    @Override
    public Table select(Selector where, Collection requestedColumns, Collection other) {
        notDistinctTable = selectStatement.select(where, requestedColumns, other);
        return makeDistinctTable();
    }

    @Override
    public Table select(Selector where, Collection requestedColumns) {
        notDistinctTable = selectStatement.select(where, requestedColumns);
        return makeDistinctTable();
    }

    public Table makeDistinctTable() {
        if (notDistinctTable == null)
            notDistinctTable = (Table) selectStatement;
        Cursor rows = notDistinctTable.rows();

        int columnCount = rows.columnCount();
        String[] columns = new String[columnCount];
        for (int idx=0; idx<columnCount; idx++) {
            columns[idx] = rows.columnName(idx);
        }

        Table distinctTable = TableFactory.create(null, columns);
        while (rows.advance() == true) {
            Object[] insertedRow = new Object[columnCount];
            Iterator row = rows.columns();

            int idx = 0;
            while (row.hasNext()) {
                insertedRow[idx++] = row.next();
            }

            if (!distinctTable.contains(insertedRow))
                distinctTable.insert(insertedRow);
        }

        return distinctTable;
    }
}

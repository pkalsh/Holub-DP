package com.holub.database.visitors;

import com.holub.database.*;

import java.util.*;

public class SortVisitor implements TableVisitor {
    private final Map<String, Boolean> sortParam;
    public SortVisitor(Map<String, Boolean> sortParam) {
        this.sortParam = sortParam;
    }

    public Table visit(ConcreteTable concreteTable)  {

        Cursor rows = concreteTable.rows();
        int columnCount = rows.columnCount();
        String[] columns = new String[columnCount];
        for (int idx=0; idx<columnCount; idx++) {
            columns[idx] = rows.columnName(idx);
        }

        Table orderedTable = TableFactory.create(null, columns);
        LinkedList rowSet = new LinkedList();

        while (rows.advance()) {
            String row[] = new String[columnCount];

            for (int idx=0; idx < columnCount; idx++) {
                row[idx] = rows.column(columns[idx]).toString();
            }
            rowSet.add(row);
        }

        rowSet.sort(new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                Iterator<String> keys = sortParam.keySet().iterator();
                while (keys.hasNext()) {
                    String columnName = keys.next();
                    int index = concreteTable.indexOf(columnName);
                    boolean order = sortParam.get(columnName);
                    String o1String = o1[index].toString();
                    String o2String = o2[index].toString();

                    if (o1String.compareTo(o2String) != 0) {
                        if (order) {
                            return o1String.compareTo(o2String);
                        } else {
                            return o1String.compareTo(o2String) * -1;
                        }
                    }
                }
                return 0;
            }
        });

        for (int idx=0; idx < rowSet.size(); idx++) {
            orderedTable.insert((Object[]) rowSet.get(idx));
        }

        return orderedTable;
    }
    public Table visit(UnmodifiableTable unmodifiableTable) {
        throw new UnsupportedOperationException();
    }
}
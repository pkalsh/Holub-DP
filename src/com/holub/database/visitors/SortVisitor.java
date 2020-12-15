package com.holub.database.visitors;

import com.holub.database.ConcreteTable;
import com.holub.database.Table;
import com.holub.database.UnmodifiableTable;

import java.util.*;

public class SortVisitor implements TableVisitor {
    private Map<String, Boolean> sortParam;
    public SortVisitor(Map<String, Boolean> sortParam) {
        this.sortParam = sortParam;
    }

    public Table visit(ConcreteTable concreteTable)  {

        LinkedList rowSet = concreteTable.getRowSet();

        Collections.sort(rowSet, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                Iterator<String> keys = sortParam.keySet().iterator();
                while( keys.hasNext() )
                {   String columnName = keys.next();
                    int index = concreteTable.indexOf(columnName);
                    boolean order = sortParam.get(columnName);
                    String o1String = o1[index].toString();
                    String o2String = o2[index].toString();

                    if (o1String.compareTo(o2String) != 0)
                    {  if (order) { return o1String.compareTo(o2String); }
                       else { return o1String.compareTo(o2String) * -1; }
                    }
                }
                return 0;
            }
        });

        return concreteTable;
    }

    public Table visit(UnmodifiableTable unmodifiableTable) {
        throw new UnsupportedOperationException();
    }



}
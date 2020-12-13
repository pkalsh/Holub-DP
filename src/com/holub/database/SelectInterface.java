package com.holub.database;

import java.util.Collection;

public interface SelectInterface {

    Table select( Selector where, String[] requestedColumns, Table[] other);

    Table select( Selector where, String[] requestedColumns );

    Table select( Selector where);

    Table select( Selector where, Collection requestedColumns,
                 Collection other);

    Table select( Selector where, Collection requestedColumns );
}

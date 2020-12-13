package com.holub.database;

import java.util.Collection;

public abstract class SelectDecorator implements SelectInterface {
    public abstract Table select(Selector where, String[] requestedColumns, Table[] other);
    public abstract Table select(Selector where, String[] requestedColumns);
    public abstract Table select(Selector where);
    public abstract Table select(Selector where, Collection requestedColumns, Collection other);
    public abstract Table select(Selector where, Collection requestedColumns);
}

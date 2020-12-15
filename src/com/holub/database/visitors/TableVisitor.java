package com.holub.database.visitors;

import com.holub.database.ConcreteTable;
import com.holub.database.Table;
import com.holub.database.UnmodifiableTable;


public interface TableVisitor {
    Table visit(ConcreteTable concreteTable);
    Table visit(UnmodifiableTable unmodifiableTable);
}

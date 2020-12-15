package com.holub.database;

import com.holub.database.visitors.SortVisitor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Map<String, Boolean> orderParameter = new LinkedHashMap<>();
    Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });

    public TableTest() {
        orderParameter.put("addrId", Boolean.TRUE);
        orderParameter.put("last", Boolean.FALSE);

        people.insert(new Object[] { "Holub", "Allen", "1" });
        people.insert(new Object[] { "Flintstone", "Wilma", "2" });
        people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });
        people.insert(new Object[] { "Zeta", "Allen", "2" });
        people.insert(new Object[] { "Alpha", "Wilma", "2" });
    }

    @Test
    void acceptTest() {
        Table orderedTable = people.accept(new SortVisitor(orderParameter));
        Table actual = TableFactory.create("people", new String[] { "last", "first", "addrId" });
        actual.insert(new Object[] { "Holub", "Allen", "1" });
        actual.insert(new Object[] { "Zeta", "Allen", "2" });
        actual.insert(new Object[] { "Flintstone", "Wilma", "2" });
        actual.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });
        actual.insert(new Object[] { "Alpha", "Wilma", "2" });

        assertEquals(orderedTable.toString(), actual.toString());
    }
}
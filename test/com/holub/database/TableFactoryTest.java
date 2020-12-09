package com.holub.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableFactoryTest {

    @org.junit.Test
    @DisplayName("create_table_by_String-tableName_String-array-columns")
    void create() {
        String expected = "people\nlast\tfirst\taddrId\t\n----------------------------------------\n";
        TableFactory tableFactory = new TableFactory();
        Table table = tableFactory.create("people", new String[] { "last", "first", "addrId" });
        String result = table.toString();

        assertEquals(expected, result);
    }

    @Test
    void testCreate() {

        TableFactory tableFactory = new TableFactory();
    }

    @Test
    void load() {
    }

    @Test
    void testLoad() {
    }

    @Test
    void testLoad1() {
    }
}
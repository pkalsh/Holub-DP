package com.holub.database;

import com.holub.text.ParseFailure;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

class DistinctSelectTest {
    Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });

    public DistinctSelectTest() {
        people.insert(new Object[] { "Holub", "Allen", "1" });
        people.insert(new Object[] { "Flintstone", "Wilma", "2" });
        people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });
        people.insert(new Object[] { "Holub", "Allen", "1" });
        people.insert(new Object[] { "Flintstone", "Wilma", "2" });
    }

    @Test
    void makeDistinctTableTest() {
        Table expectedTable = TableFactory.create(null, new String[] { "last", "first", "addrId" });
        expectedTable.insert(new Object[] { "Holub", "Allen", "1" });
        expectedTable.insert(new Object[] { "Flintstone", "Wilma", "2" });
        expectedTable.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });

        DistinctSelect distinctSelect =  new DistinctSelect(people);
        Table actualTable = distinctSelect.makeDistinctTable();
        assertEquals(expectedTable.toString(), actualTable.toString());
    }
}
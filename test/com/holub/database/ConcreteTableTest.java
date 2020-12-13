package com.holub.database;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteTableTest {
    Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });

    Table address = TableFactory.create("address", new String[] { "addrId", "street", "city", "state", "zip" });

    public ConcreteTableTest() {
        people.insert(new Object[] { "Holub", "Allen", "1" });
        people.insert(new Object[] { "Flintstone", "Wilma", "2" });
        people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });

        address.insert(new Object[] { "1", "123 MyStreet", "Berkeley", "CA", "99999" });
    }


    @Test
    void insert() {
        List l = new ArrayList();
        l.add("2");
        l.add("123 Quarry Ln.");
        l.add("Bedrock ");
        l.add("XX");
        l.add("12345");
        assertTrue (address.insert(l) == 1);
    }

    @Test
    void testInsert() {
        List l = new ArrayList();
        l.add("3");
        l.add("Bogus");
        l.add("Bad");
        l.add("XX");
        l.add("12345");

        List c = new ArrayList();
        c.add("addrId");
        c.add("street");
        c.add("city");
        c.add("state");
        c.add("zip");
        assertTrue (address.insert(c, l) == 1);
    }

    @Test
    void select() {
    }

    @Test
    void testSelect() {
    }

    @Test
    void testSelect1() {
    }

    @Test
    void testSelect2() {
    }

    @Test
    void testSelect3() {
    }

    @Test
    void contains() {
        assertTrue(people.contains(new Object[] { "Holub", "Allen", "1" }) == true);
        assertTrue(people.contains(new Object[] { "Dongmin", "Lee", "1" }) == false);
        assertTrue(people.contains(new Object[] { "Holub", "Allen", 1 }) == false);
    }

}
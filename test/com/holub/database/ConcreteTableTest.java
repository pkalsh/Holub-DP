package com.holub.database;

import com.holub.text.ParseFailure;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteTableTest {
    Database database;
    Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
    Table address = TableFactory.create("address", new String[] { "addrId", "street", "city", "state", "zip" });

    public ConcreteTableTest() throws IOException, ParseFailure {
        database = new Database();

        database.execute("create table address " +
                        "(addrId int, street varchar, city varchar, " +
                        "state char(2), zip int, primary key(addrId))");

        database.execute("create table name(first varchar(10), last varchar(10), addrId integer)");

        database.execute("insert into address values( 0,'12 MyStreet','Berkeley','CA','99999')");
        database.execute("insert into address values( 1, '34 Quarry Ln.', 'Bedrock' , 'XX', '00000')");

        database.execute("insert into name VALUES ('Fred',  'Flintstone', '1')");
        database.execute("insert into name VALUES ('Wilma', 'Flintstone', '1')");
        database.execute("insert into name (last,first,addrId) VALUES('Holub','Allen',(10-10*1))");


        people.insert(new Object[] { "Holub", "Allen", "0" });
        people.insert(new Object[] { "Flintstone", "Wilma", "1" });
        people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "1", "Fred", "Flintstone" });

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
    void testSelect() throws IOException, ParseFailure {
        Table actual = TableFactory.create(null, new String[] { "first", "last", "addrId" });
        Table expected = database.execute("SELECT * FROM name WHERE first = 'Allen'");
        actual.insert(new Object[] { "Allen", "Holub", "0" });

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testSelect1() throws IOException, ParseFailure {
        Table actual = TableFactory.create(null, new String[] { "first", "last" });
        Table expected = database.execute("SELECT first, last FROM name WHERE addrId = '1'");
        actual.insert(new Object[] { "Fred",  "Flintstone" });
        actual.insert(new Object[] { "Wilma", "Flintstone" });

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testSelect2() throws IOException, ParseFailure {
        Table actual = TableFactory.create(null,
                                            new String[] {"first", "last", "street" });
        Table expected = database.execute("SELECT first, last, street FROM name, address " +
                                            "WHERE name.addrId = address.addrId");

        actual.insert(new Object[] { "Fred",  "Flintstone", "34 Quarry Ln." });
        actual.insert(new Object[] { "Wilma", "Flintstone", "34 Quarry Ln." });
        actual.insert(new Object[] { "Allen", "Holub", "12 MyStreet"});

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testSelect3() throws IOException, ParseFailure {
        Table actual = TableFactory.create(null,
                new String[] {"first", "last", "addrId", "street", "city", "state", "zip" });
        Table expected = database.execute("SELECT * FROM name, address WHERE name.addrId = address.addrId");

        actual.insert(new Object[] { "Fred",  "Flintstone", "1", "34 Quarry Ln.", "Bedrock", "XX", "00000" });
        actual.insert(new Object[] { "Wilma", "Flintstone", "1", "34 Quarry Ln.", "Bedrock", "XX", "00000" });
        actual.insert(new Object[] { "Allen", "Holub", "0", "12 MyStreet", "Berkeley", "CA", "99999"});

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void orderBySelect() throws IOException, ParseFailure {
        Table actual = TableFactory.create(null,
                new String[] {"first", "last", "addrId", "street", "city", "state", "zip" });
        Table expected = database.execute("SELECT * FROM name, address " +
                "WHERE name.addrId = address.addrId ORDER BY first");

        actual.insert(new Object[] { "Allen", "Holub", "0", "12 MyStreet", "Berkeley", "CA", "99999"});
        actual.insert(new Object[] { "Fred",  "Flintstone", "1", "34 Quarry Ln.", "Bedrock", "XX", "00000" });
        actual.insert(new Object[] { "Wilma", "Flintstone", "1", "34 Quarry Ln.", "Bedrock", "XX", "00000" });

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void contains() {
        assertTrue(people.contains(new Object[] { "Holub", "Allen", "0" }) == true);
        assertTrue(people.contains(new Object[] { "Dongmin", "Lee", "1" }) == false);
        assertTrue(people.contains(new Object[] { "Holub", "Allen", 1 }) == false);
    }

}
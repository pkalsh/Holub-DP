package com.holub.database;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class XMLExporterTest {
    private final  String OUT_FILE_NAME = "people_out.xml";
    private final  String EXPECTED_FILE_NAME = "people.xml";
    private Writer writer;
    private Table  table;

    public XMLExporterTest() throws IOException, NotWellFormedException {
        writer = new FileWriter(OUT_FILE_NAME);
        Reader in = new FileReader(EXPECTED_FILE_NAME);
        table = new ConcreteTable(new XMLImporter(in));
        in.close();
    }


    @Test
    void testConcreteBuilder() throws IOException, NotWellFormedException {
        Reader in = new FileReader(OUT_FILE_NAME);
        Reader expected_in = new FileReader(EXPECTED_FILE_NAME);
        table.export(new XMLExporter(writer));
        writer.close();

        Table actual = new ConcreteTable(new XMLImporter(in));
        Table expected = new ConcreteTable(new XMLImporter(expected_in));

        expected_in.close();
        in.close();

        assertEquals(expected.toString(), actual.toString());
    }
}
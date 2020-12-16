package com.holub.database;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class HTMLExporterTest {
    private final  String OUT_FILE_NAME = "people.html";
    private Table  table;

    public HTMLExporterTest() throws IOException, NotWellFormedException {
        table = TableFactory.create("people", new String[] { "last", "first", "addrId" });
        table.insert(new Object[] { "Holub", "Allen", "1" });
        table.insert(new Object[] { "Flintstone", "Wilma", "2" });
        table.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });

    }


    @Test
    void exportTest() throws IOException, NotWellFormedException {
        Writer writer = new FileWriter(OUT_FILE_NAME);
        table.export(new HTMLExporter(writer));
        writer.close();

        String expected =
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>HTML Exporter Page</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<table>\n" +
                "\t\t<caption>people</caption>\n" +
                "\t\t<thead>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<th>last</th>\n" +
                "\t\t\t\t<th>first</th>\n" +
                "\t\t\t\t<th>addrId</th>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</thead>\n" +
                "\t\t<tbody>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Holub</td>\n" +
                "\t\t\t\t<td>Allen</td>\n" +
                "\t\t\t\t<td>1</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Flintstone</td>\n" +
                "\t\t\t\t<td>Wilma</td>\n" +
                "\t\t\t\t<td>2</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>Flintstone</td>\n" +
                "\t\t\t\t<td>Fred</td>\n" +
                "\t\t\t\t<td>2</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</tbody>\n" +
                "\t</table>\n" +
                "</body>\n";

        FileReader fileReader = new FileReader(OUT_FILE_NAME);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder actual = new StringBuilder();
        String line = "";

        while((line = bufferedReader.readLine()) != null){
            actual.append(line);
            actual.append("\n");
        }

        bufferedReader.close();
        fileReader.close();

        assertEquals(expected, actual.toString());
    }
}
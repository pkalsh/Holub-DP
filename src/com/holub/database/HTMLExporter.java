package com.holub.database;

import com.holub.database.Table;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class HTMLExporter implements Table.Exporter {

    private final Writer         out;
    private       int            width;

    public HTMLExporter( Writer out ) {
        this.out = out;
    }

    public void writeTag(String tag) throws IOException {
        out.write(tag);
        out.write("\n");
    }

    @Override
    public void startTable() throws IOException {
        writeTag("<table>");
    }

    @Override
    public void storeMetadata(String tableName,
                              int width,
                              int height,
                              Iterator columnNames) throws IOException {
        this.width = width;
        writeTag("<caption>");
        out.write("\t");
        out.write(tableName == null ? "<anonymous>" : tableName ); out.write("\n");
        writeTag("</caption>");
        storeRow( columnNames );
    }

    @Override
    public void storeRow(Iterator data) throws IOException {
        int i = width;
        writeTag("<tbody>");

        while( data.hasNext() ) {
            Object datum = data.next();

            // Null columns are represented by an empty field
            // (two commas in a row). There's nothing to write
            // if the column data is null.
            writeTag("<th>");
            if( datum != null ) {
                out.write("\t");
                out.write(datum.toString());
            }
            writeTag("</th>");

            i -= 1;
        }
        out.write("\n");
    }

    @Override
    public void endTable() throws IOException {
        writeTag("</table>");
    }
}

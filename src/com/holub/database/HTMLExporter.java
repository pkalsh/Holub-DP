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


    @Override
    public void startTable() throws IOException {
        out.write("<!DOCTYPE html>\n");
        out.write("<html>\n");
        out.write("<head>\n");
        out.write("\t<title>HTML Exporter Page</title>\n");
        out.write("</head>\n");
        out.write("<body>\n");
        out.write("\t<table>\n");
    }

    @Override
    public void storeMetadata(String tableName,
                              int width,
                              int height,
                              Iterator columnNames) throws IOException {
        this.width = width;
        out.write("\t\t<caption>");
        out.write(tableName == null ? "<anonymous>" : tableName );
        out.write("</caption>\n");
        out.write("\t\t<thead>\n");

        out.write("\t\t\t<tr>\n");
        while( columnNames.hasNext() ) {
            Object datum = columnNames.next();

            // Null columns are represented by an empty field
            // (two commas in a row). There's nothing to write
            // if the column data is null.
            out.write("\t\t\t\t<th>");
            if( datum != null ) {
                out.write(datum.toString());
            }
            out.write("</th>\n");

            width -= 1;
        }
        out.write("\t\t\t</tr>\n");
        out.write("\t\t</thead>\n");
        out.write("\t\t<tbody>\n");
    }


    @Override
    public void storeRow(Iterator data) throws IOException {
        int i = width;

        out.write("\t\t\t<tr>\n");
        while( data.hasNext() ) {
            Object datum = data.next();

            // Null columns are represented by an empty field
            // (two commas in a row). There's nothing to write
            // if the column data is null.
            out.write("\t\t\t\t<td>");
            if( datum != null ) {
                out.write(datum.toString());
            }
            out.write("</td>\n");

            i -= 1;
        }
        out.write("\t\t\t</tr>\n");
    }

    @Override
    public void endTable() throws IOException {
        out.write("\t\t</tbody>\n");
        out.write("\t</table>\n");
        out.write("</body>");
    }
}

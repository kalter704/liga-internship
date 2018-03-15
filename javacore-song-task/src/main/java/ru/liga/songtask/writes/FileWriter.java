package ru.liga.songtask.writes;

import java.io.File;
import java.io.IOException;

public class FileWriter extends BaseWrite {

    private final java.io.FileWriter fileWriter;

    public FileWriter(File outFile) throws IOException {
        super(null);
        fileWriter = new java.io.FileWriter(outFile);
    }

    public FileWriter(File outFile, BaseWrite writer) throws IOException {
        super(writer);
        fileWriter = new java.io.FileWriter(outFile);
    }

    @Override
    public void write(String s) throws IOException {
        super.write(s);
        fileWriter.write(s);
        fileWriter.flush();
    }

    @Override
    public void writeln(String s) throws IOException {
        super.writeln(s);
        fileWriter.write(s + "\n");
        fileWriter.flush();
    }

    @Override
    public void writeln() throws IOException {
        super.writeln();
        fileWriter.write("\n");
        fileWriter.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
        fileWriter.close();
    }
}

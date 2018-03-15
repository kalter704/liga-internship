package ru.liga.songtask.writes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FileWriter extends BaseWriter {

    private final Logger log = LoggerFactory.getLogger(FileWriter.class);

    private final java.io.FileWriter fileWriter;

    public FileWriter(File outFile) throws IOException {
        super(null);
        fileWriter = new java.io.FileWriter(outFile);
    }

    public FileWriter(File outFile, BaseWriter writer) throws IOException {
        super(writer);
        fileWriter = new java.io.FileWriter(outFile);
    }

    @Override
    public void write(String s) {
        super.write(s);
        try {
            fileWriter.write(s);
            fileWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void writeln(String s) {
        super.writeln(s);
        try {
            fileWriter.write(s + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void writeln() {
        super.writeln();
        try {
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void close() {
        super.close();
        try {
            fileWriter.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

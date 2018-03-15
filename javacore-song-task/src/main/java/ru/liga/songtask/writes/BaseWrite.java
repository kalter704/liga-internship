package ru.liga.songtask.writes;

import java.io.IOException;

public abstract class BaseWrite {

    private final BaseWrite writer;

    public BaseWrite(BaseWrite writer) {
        this.writer = writer;
    }

    public void write(String s) throws IOException {
        if (writer != null) {
            writer.write(s);
        }
    }

    public void writeln(String s) throws IOException {
        if (writer != null) {
            writer.writeln(s);
        }
    }

    public void writeln() throws IOException {
        if (writer != null) {
            writer.writeln();
        }
    }

    public void close() throws IOException {}
}

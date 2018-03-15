package ru.liga.songtask.writes;

public abstract class BaseWriter {

    private final BaseWriter writer;

    public BaseWriter(BaseWriter writer) {
        this.writer = writer;
    }

    public void write(String s) {
        if (writer != null) {
            writer.write(s);
        }
    }

    public void writeln(String s) {
        if (writer != null) {
            writer.writeln(s);
        }
    }

    public void writeln() {
        if (writer != null) {
            writer.writeln();
        }
    }

    public void close() {}
}

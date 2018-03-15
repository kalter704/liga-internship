package ru.liga.songtask.writes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.App;

import java.io.IOException;
import java.io.Writer;

public class LogWriter extends BaseWrite {

    private static Logger logger = null;

    public LogWriter(Class clazz) {
        super(null);
        logger = LoggerFactory.getLogger(clazz);
    }

    public LogWriter(Class clazz, BaseWrite writer) {
        super(writer);
        logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void write(String s) throws IOException {
        super.write(s);
        logger.info(s);
    }

    @Override
    public void writeln(String s) throws IOException {
        super.writeln(s);
        logger.info(s);
    }

    @Override
    public void writeln() throws IOException {
        super.writeln();
        logger.info("");
    }
}

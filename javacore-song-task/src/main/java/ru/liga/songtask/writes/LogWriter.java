package ru.liga.songtask.writes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LogWriter extends BaseWrite {

    private final Logger logger;

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

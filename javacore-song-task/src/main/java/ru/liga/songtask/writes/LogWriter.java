package ru.liga.songtask.writes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LogWriter extends BaseWriter {

    private final Logger logger;

    public LogWriter(Class clazz) {
        super(null);
        logger = LoggerFactory.getLogger(clazz);
    }

    public LogWriter(Class clazz, BaseWriter writer) {
        super(writer);
        logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void write(String s) {
        super.write(s);
        logger.info(s);
    }

    @Override
    public void writeln(String s) {
        super.writeln(s);
        logger.info(s);
    }

    @Override
    public void writeln() {
        super.writeln();
        logger.info("");
    }
}

package ru.liga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.Task;

import java.io.IOException;

/**
 * Всего нот: 15
 * <p>
 * Анализ диапазона:
 * верхняя: E4
 * нижняя: F3
 * диапазон: 11
 * <p>
 * Анализ длительности нот (мс):
 * 4285: 10
 * 2144: 5
 * <p>
 * Анализ нот по высоте:
 * E4: 3
 * D4: 3
 * A3: 3
 * G3: 3
 * F3: 3
 * <p>
 * Анализ интервалов:
 * 2: 9
 * 5: 3
 * 11: 2
 */
public class App {

    private static Logger logger = LoggerFactory.getLogger( App.class );

    public static void main(String[] args) throws IOException {

        Task task = ArgumentsManager.getTaskByArg(args);

        if (task != null) {
            task.execute();
        }

//        catchOutOfMemoryError();

//        catchStackOverflowException();

    }

    private static void catchOutOfMemoryError() {
        long timeStart = System.currentTimeMillis();
        long timeEnd = timeStart;
        try {
            long[] arr = new long[1000000000];
        } catch (OutOfMemoryError e) {
            timeEnd = System.currentTimeMillis();
            e.printStackTrace();
        }
        logger.info("Time: " + String.valueOf(timeEnd - timeStart));
    }

    private static void catchStackOverflowException() {
        catchStackOverflowException();
    }

}

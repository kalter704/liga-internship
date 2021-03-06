package ru.liga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.SongAnalysisTask;
import ru.liga.songtask.SongChangeTask;
import ru.liga.songtask.Task;
import ru.liga.songtask.writes.BaseWriter;
import ru.liga.songtask.writes.FileWriter;
import ru.liga.songtask.writes.LogWriter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArgumentsManager {

    private static Logger log = LoggerFactory.getLogger(ArgumentsManager.class);

    public static Task getTaskByArg(String[] args) throws IOException {
        Task task = null;
        File inputFile = null;
        try {
            inputFile = new Resources(args[0]).getFile();
        } catch (NullPointerException e) {
            log.error(e.toString());
        }
        if (inputFile == null) {
            return null;
        }
        if ("analyze".equalsIgnoreCase(args[1])) {
            BaseWriter write = new LogWriter(App.class);
            if (args.length >= 3 && "-f".equalsIgnoreCase(args[2])) {
                write = new FileWriter(new File(inputFile.getParent() + "/analyze"), write);
            }
            task = new SongAnalysisTask(inputFile, write);
        } else if ("change".equalsIgnoreCase(args[1])) {
            Map<String, Integer> commands = new HashMap<>();
            for (int i = 2; i <= (args.length - 2) / 2 + 2; i += 2) {
                commands.put(args[i], Integer.valueOf(args[i + 1]));
            }
            task = new SongChangeTask(inputFile, commands);
        }
        return task;
    }

}

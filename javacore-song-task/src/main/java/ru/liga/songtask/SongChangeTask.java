package ru.liga.songtask;

import com.leff.midi.MidiFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class SongChangeTask implements Task {

    private final static Logger log = LoggerFactory.getLogger(SongChangeTask.class);

    private SongChange songChange = null;

    private final Map<String, Integer> commands;

    private final File outFile;

    public SongChangeTask(File inputFile, Map<String, Integer> commands) {
        String fileName = inputFile.getName().split("\\.")[0];
        if (commands.containsKey("-trans")) {
            fileName += "-trans" + String.valueOf(commands.get("-trans"));
        }
        if (commands.containsKey("-tempo")) {
            fileName += "-tempo" + String.valueOf(commands.get("-tempo"));
        }
        fileName += "." + inputFile.getName().split("\\.")[1];
        outFile = new File(inputFile.getParent() + "/" + fileName);
        try {
            this.songChange = new SongChange(new MidiFile(inputFile));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        this.commands = commands;
    }

    @Override
    public void execute() {
        if (commands.containsKey("-trans")) {
            songChange.changeTrans(commands.get("-trans"));
        }
        if (commands.containsKey("-tempo")) {
            songChange.changeTempo(commands.get("-tempo"));
        }
        try {
            songChange.getMidiFile().writeToFile(outFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

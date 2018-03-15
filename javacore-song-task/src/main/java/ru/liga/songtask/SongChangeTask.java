package ru.liga.songtask;

import com.leff.midi.MidiFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class SongChangeTask implements Task {

    private SongChange songChange = null;

    private final Map<String, Integer> commands;

    public SongChangeTask(File inputFile, Map<String, Integer> commands) {
        String fileName = inputFile.getName().split("\\.")[0];
        if (commands.containsKey("-trans")) {
            fileName += "-trans" + String.valueOf(commands.get("-trans"));
        }
        if (commands.containsKey("-tempo")) {
            fileName += "-tempo" + String.valueOf(commands.get("-tempo"));
        }
        fileName += "." + inputFile.getName().split("\\.")[1];
        try {
            this.songChange = new SongChange(new MidiFile(inputFile),  new File(inputFile.getParent() + "/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
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
        songChange.save();
    }
}

package ru.liga.songtask;

import com.leff.midi.MidiFile;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.Resources;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SongChangeTaskTest {

    @Test
    public void execute() throws Exception {
        int trans = 5;
        int tempo = 11;

        Map<String, Integer> commands = new HashMap<String, Integer>() {{
            put("-trans", trans);
            put("-tempo", tempo);
        }};

        File inputFile = new Resources("CRANBERRIES_THE_-_Zombie1.mid").getFile();

        SongChangeTask task = new SongChangeTask(inputFile, commands);
        task.execute();

        String newFileName = inputFile.getParent() + "/CRANBERRIES_THE_-_Zombie1"
                + "-trans" + String.valueOf(trans)
                + "-tempo" + String.valueOf(tempo)
                + ".mid";

        File newFile = new File(newFileName);

        Assertions.assertThat(newFile.exists()).isTrue();

        MidiFile originalFile = new MidiFile(inputFile);
        MidiFile resultFile = new MidiFile(newFile);

        Assertions.assertThat(originalFile.getResolution()).isEqualTo(resultFile.getResolution());
        Assertions.assertThat(originalFile.getLengthInTicks()).isEqualTo(resultFile.getLengthInTicks());
        Assertions.assertThat(originalFile.getType()).isEqualTo(resultFile.getType());
    }

}
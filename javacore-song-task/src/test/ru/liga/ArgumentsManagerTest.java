package ru.liga;

import org.junit.Test;
import org.assertj.core.api.Assertions;
import ru.liga.songtask.SongAnalysisTask;
import ru.liga.songtask.SongChangeTask;

import java.io.IOException;

public class ArgumentsManagerTest {

    @Test
    public void whenAnalysisCommandReturnAnalysisTask() throws IOException {
        Assertions.assertThat(ArgumentsManager.getTaskByArg(new String[]{ "zombie.mid", "analyze" }))
                .isExactlyInstanceOf(SongAnalysisTask.class);
    }

    @Test
    public void whenChangeCommandReturnAnalysisTask() throws IOException {
        String[] com = new String[] { "CRANBERRIES_THE_-_Zombie1.mid",  "change",  "-trans",  "-5", "-tempo", "50" };
        Assertions.assertThat(ArgumentsManager.getTaskByArg(com))
                .isExactlyInstanceOf(SongChangeTask.class);
    }

    @Test
    public void whenWrongCommandReturnNull() throws IOException {
        Assertions.assertThat(ArgumentsManager.getTaskByArg(new String[] { "CRANBERRIES_THE_-_Zombie1.mid", "testcommand" }))
                .isNull();
    }

    @Test
    public void whenNoExistingFileReturnNull() throws IOException {
        Assertions.assertThat(ArgumentsManager.getTaskByArg(new String[] { "filename", "analysisForTest"})).isNull();
    }

}
package ru.liga.songtask;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import ru.liga.Resources;
import ru.liga.songtask.domain.NoteSign;
import ru.liga.songtask.domain.SimpleMidiFile;

import java.util.Iterator;

public class SongAnalysisTest {

    private SongAnalysis songAnalysis;

    private String fileName = "zombie.mid";

    @Before
    public void setUp() {
        songAnalysis = new SongAnalysis(new SimpleMidiFile(new Resources(fileName).getFile()));
    }

    @Test
    public void getLength() {
        Assertions.assertThat(songAnalysis.numberOfNotes()).isEqualTo(289);
    }

    @Test
    public void getHigherNote() throws Exception {
        Assertions.assertThat(songAnalysis.getHigherNote().sign().fullName()).isEqualTo("A5");
    }

    @Test
    public void getLowerNote() throws Exception {
        Assertions.assertThat(songAnalysis.getLowerNote().sign().fullName()).isEqualTo("E4");
    }

    @Test
    public void noteLengthCount() throws Exception {
        Assertions.assertThat(songAnalysis.noteLengthCount().get(2195L)).isEqualTo(4L);
    }

    @Test
    public void noteHeightCount() throws Exception {
        Iterator<NoteSign> noteI = songAnalysis.noteHeightCount().keySet().iterator();
        Assertions.assertThat(noteI.next().fullName()).isEqualTo("A5");
    }

    @Test
    public void noteIntervalCount() throws Exception {
        Assertions.assertThat(songAnalysis.noteIntervalCount().get(4)).isEqualTo(17);
    }

}
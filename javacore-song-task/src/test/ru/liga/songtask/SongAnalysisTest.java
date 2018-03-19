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

    private final String fileName = "zombie.mid";

    @Before
    public void setUp() {
        songAnalysis = new SongAnalysis(new SimpleMidiFile(new Resources(fileName).getFile()));
    }

    @Test
    public void whenCallNumberOfNotesReturn289() {
        Assertions.assertThat(songAnalysis.numberOfNotes()).isEqualTo(289);
    }

    @Test
    public void whenCallGetHigherNoteReturnA5() throws Exception {
        Assertions.assertThat(songAnalysis.getHigherNote().sign().fullName()).isEqualTo("A5");
    }

    @Test
    public void whenCallGetLowerNoteReturnE4() throws Exception {
        Assertions.assertThat(songAnalysis.getLowerNote().sign().fullName()).isEqualTo("E4");
    }

    @Test
    public void whenGetNoteLengthWithKey2195Return4() throws Exception {
        Assertions.assertThat(songAnalysis.noteLengthCount().get(2195L)).isEqualTo(4L);
    }

    @Test
    public void whenGetFirstNoteHeightReturnA5() throws Exception {
        Iterator<NoteSign> noteI = songAnalysis.noteHeightCount().keySet().iterator();
        Assertions.assertThat(noteI.next().fullName()).isEqualTo("A5");
    }

    @Test
    public void whenGetNoteIntervalWithKey4Return17() throws Exception {
        Assertions.assertThat(songAnalysis.noteIntervalCount().get(4)).isEqualTo(17);
    }

}
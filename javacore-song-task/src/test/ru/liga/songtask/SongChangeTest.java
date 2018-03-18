package ru.liga.songtask;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import ru.liga.Resources;

import java.io.IOException;
import java.util.Iterator;

public class SongChangeTest {

    private SongChange songChange;

    private MidiFile zombie;

    private final String fileName = "zombie.mid";

    @Before
    public void setUp() throws IOException {
        zombie = new MidiFile(new Resources(fileName).getFile());
        songChange = new SongChange(new MidiFile(new Resources(fileName).getFile()));
    }

    @Test
    public void getMidiFile() throws Exception {
        MidiFile zombieTest = songChange.getMidiFile();

        Assertions.assertThat(zombie.getTracks().get(0).getEvents().size())
                .isEqualTo(zombieTest.getTracks().get(0).getEvents().size());

        Iterator<MidiEvent> events = zombie.getTracks().get(0).getEvents().iterator();
        Iterator<MidiEvent> eventsTest = zombieTest.getTracks().get(0).getEvents().iterator();
        while (events.hasNext() && eventsTest.hasNext()) {
            Assertions.assertThat(events.next().getSize()).isEqualTo(eventsTest.next().getSize());
            Assertions.assertThat(events.next().getTick()).isEqualTo(eventsTest.next().getTick());
            Assertions.assertThat(events.next().getDelta()).isEqualTo(eventsTest.next().getDelta());
        }
    }

    @Test
    public void changeTempo() throws Exception {
        songChange.changeTempo(15);

        Iterator<MidiEvent> events = zombie.getTracks().get(0).getEvents().iterator();
        Iterator<MidiEvent> eventsTest = songChange.getMidiFile().getTracks().get(0).getEvents().iterator();

        while (events.hasNext() && eventsTest.hasNext()) {
            MidiEvent me = events.next();
            MidiEvent meTest = eventsTest.next();
            if (me instanceof Tempo) {
                Tempo tempo = (Tempo) me;
                Tempo tempTest = (Tempo) meTest;
                Assertions.assertThat(tempo.getBpm() * ((float)(15 + 100) / 100)).isEqualTo(tempTest.getBpm());
            }
        }
    }

    @Test
    public void changeTrans() throws Exception {
        int delta = 10;
        songChange.changeTrans(delta);

        Iterator<MidiEvent> events = zombie.getTracks().get(0).getEvents().iterator();
        Iterator<MidiEvent> eventsTest = songChange.getMidiFile().getTracks().get(0).getEvents().iterator();

        while (events.hasNext() && eventsTest.hasNext()) {
            MidiEvent me = events.next();
            MidiEvent meTest = eventsTest.next();
            if (me instanceof NoteOn) {
                NoteOn n = (NoteOn) me;
                NoteOn nTest = (NoteOn) meTest;
                Assertions.assertThat(n.getNoteValue() + delta).isEqualTo(nTest.getNoteValue());
            } else if (me instanceof NoteOff) {
                NoteOff n = (NoteOff) me;
                NoteOff nTest = (NoteOff) meTest;
                Assertions.assertThat(n.getNoteValue() + delta).isEqualTo(nTest.getNoteValue());
            }
        }
    }

}
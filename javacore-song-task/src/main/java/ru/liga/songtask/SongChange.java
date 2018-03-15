package ru.liga.songtask;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;

import java.io.File;
import java.io.IOException;

public class SongChange {

    private final MidiFile midiFile;

    private final File outFile;

    public SongChange(MidiFile midiFile, File outFile) {
        this.midiFile = midiFile;
        this.outFile = outFile;
    }

    public void changeTempo(int delta) {
        change((midiEvent) -> {
            if (midiEvent instanceof Tempo) {
                Tempo tempo = (Tempo) midiEvent;
                tempo.setBpm(tempo.getBpm() * ((float)(delta + 100) / 100));
            }
        });
    }

    public void changeTrans(int delta) {
        change((midiEvent) -> {
            if (midiEvent instanceof NoteOn) {
                NoteOn note = (NoteOn) midiEvent;
                note.setNoteValue(note.getNoteValue() + delta);
            } else if (midiEvent instanceof NoteOff) {
                NoteOff note = (NoteOff) midiEvent;
                note.setNoteValue(note.getNoteValue() + delta);
            }
        });
    }

    public void change(Change func) {
        midiFile.getTracks().forEach(midiTrack -> midiTrack.getEvents().forEach(func::change));
    }

    public void save() {
        try {
            midiFile.writeToFile(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private interface Change {

        void change(MidiEvent midiEvent);

    }
}

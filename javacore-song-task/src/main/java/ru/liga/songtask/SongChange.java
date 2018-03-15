package ru.liga.songtask;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;

public class SongChange {

    private final MidiFile midiFile;

    public SongChange(MidiFile midiFile) {
        this.midiFile = midiFile;
    }

    public MidiFile getMidiFile() {
        return midiFile;
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

    private void change(Change func) {
        midiFile.getTracks().forEach(midiTrack -> midiTrack.getEvents().forEach(func::change));
    }

    private interface Change {

        void change(MidiEvent midiEvent);

    }
}

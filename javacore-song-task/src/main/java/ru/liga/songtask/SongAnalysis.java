package ru.liga.songtask;

import javafx.util.Pair;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.NoteSign;
import ru.liga.songtask.domain.SimpleMidiFile;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SongAnalysis {

    private final SimpleMidiFile simpleMidiFile;

    public SongAnalysis(SimpleMidiFile simpleMidiFile) {
        this.simpleMidiFile = simpleMidiFile;
    }

    public int numberOfNotes() {
        return simpleMidiFile.vocalNoteList().size();
    }

    public Note getHigherNote() {
        return simpleMidiFile.vocalNoteList().stream().min(Comparator.comparing(Note::sign)).get();
    }

    public Note getLowerNote() {
        return simpleMidiFile.vocalNoteList().stream().max(Comparator.comparing(Note::sign)).get();
    }


    public Map<Long, Long> noteLengthCount() {
        Map<Long, Long> noteLengthCountMap = new TreeMap<>(Comparator.reverseOrder());
        Float tickInMs = simpleMidiFile.tickInMs();
        simpleMidiFile.vocalNoteList().stream()
                .map(Note::durationTicks)
                .map(durationTicks -> durationTicks * tickInMs)
                .map(Float::longValue)
                .map(noteLength -> {
                    long count = 0;
                    if (noteLengthCountMap.containsKey(noteLength)) {
                        count = noteLengthCountMap.get(noteLength);
                    }
                    return new Pair<>(noteLength, count + 1);
                }).forEach(keyValue -> noteLengthCountMap.put(keyValue.getKey(), keyValue.getValue()));
        return noteLengthCountMap;
    }

    public Map<NoteSign, Long> noteHeightCount() {
        Map<NoteSign, Long> noteHeightCountMap = new TreeMap<>(new NoteHeightComparator());
        simpleMidiFile.vocalNoteList().stream()
                .map(note -> {
                    long count = 0;
                    if (noteHeightCountMap.containsKey(note.sign())) {
                        count = noteHeightCountMap.get(note.sign());
                    }
                    return new Pair<>(note.sign(), count + 1);
                }).forEach(keyValue -> noteHeightCountMap.put(keyValue.getKey(), keyValue.getValue()));
        return noteHeightCountMap;
    }

    public Map<Integer, Integer> noteIntervalCount() {
        Map<Integer, Integer> noteIntervalCountMap = new TreeMap<>();
        final Note[] previousNote = new Note[1]/*{simpleMidiFile.vocalNoteList().get(0)}*/;
        simpleMidiFile.vocalNoteList().stream()
                .filter(note -> {
                    if (previousNote[0] == null) {
                        previousNote[0] = note;
                        return false;
                    }
                    return true;
                })
                .map(note -> {
                    Integer count = 0;
                    Integer diff = note.sign().diffInSemitones(previousNote[0].sign());
                    if (noteIntervalCountMap.containsKey(diff)) {
                        count = noteIntervalCountMap.get(diff);
                    }
                    previousNote[0] = note;
                    return new Pair<>(diff, count + 1);
                }).forEach(keyValue -> noteIntervalCountMap.put(keyValue.getKey(), keyValue.getValue()));
        return noteIntervalCountMap;
    }

    private class NoteHeightComparator implements Comparator<NoteSign> {

        @Override
        public int compare(NoteSign o1, NoteSign o2) {
            return o2.moreThanInSemitones(o1);
        }

    }
}

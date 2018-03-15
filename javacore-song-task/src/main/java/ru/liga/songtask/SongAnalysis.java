package ru.liga.songtask;

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
        return getNoteByCondition(((n1, n2) -> n2.sign().higher(n1.sign())));
    }

    public Note getLowerNote() {
        return getNoteByCondition(((n1, n2) -> n2.sign().lower(n1.sign())));
    }

    private Note getNoteByCondition(Condition condition) {
        Note result = null;
        for (Note note : simpleMidiFile.vocalNoteList()) {
            if (result == null) {
                result = note;
                continue;
            }
            if (condition.compare(result, note)) {
                result = note;
            }
        }
        return result;
    }

    public Map<Long, Long> noteLengthCount() {
        Map<Long, Long> noteLengthCountMap = new TreeMap<>(Comparator.reverseOrder());
        Float tickInMs = simpleMidiFile.tickInMs();
        for (Note note : simpleMidiFile.vocalNoteList()) {
            long noteLength = (long) (note.durationTicks() * tickInMs);
            if (noteLengthCountMap.containsKey(noteLength)) {
                noteLengthCountMap.put(noteLength, noteLengthCountMap.get(noteLength) + 1);
            } else {
                noteLengthCountMap.put(noteLength, 1L);
            }
        }
        return noteLengthCountMap;
    }

    public Map<NoteSign, Long> noteHeightCount() {
        Map<NoteSign, Long> noteHeightCountMap = new TreeMap<>(new NoteHeightComparator());
        for (Note note : simpleMidiFile.vocalNoteList()) {
            if (noteHeightCountMap.containsKey(note.sign())) {
                noteHeightCountMap.put(note.sign(), noteHeightCountMap.get(note.sign()) + 1);
            } else {
                noteHeightCountMap.put(note.sign(), 1L);
            }
        }
        return noteHeightCountMap;
    }

    public Map<Integer, Integer> noteIntervalCount() {
        Map<Integer, Integer> noteIntervalCountMap = new TreeMap<>();
        Note previousNote = null;
        for (Note note : simpleMidiFile.vocalNoteList()) {
            if (previousNote == null) {
                previousNote = note;
                continue;
            }
            Integer diff = note.sign().diffInSemitones(previousNote.sign());
            if (noteIntervalCountMap.containsKey(diff)) {
                noteIntervalCountMap.put(diff, noteIntervalCountMap.get(diff) + 1);
            } else {
                noteIntervalCountMap.put(diff, 1);
            }
            previousNote = note;
        }
        return noteIntervalCountMap;
    }


    private interface Condition {
        boolean compare(Note n1, Note n2);
    }

    private class NoteHeightComparator implements Comparator<NoteSign> {

        @Override
        public int compare(NoteSign o1, NoteSign o2) {
            return o2.moreThanInSemitones(o1);
        }

    }
}

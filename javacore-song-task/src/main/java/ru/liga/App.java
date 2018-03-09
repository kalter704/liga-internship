package ru.liga;

import ru.liga.songtask.content.Content;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.NoteSign;
import ru.liga.songtask.domain.SimpleMidiFile;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Всего нот: 15
 * <p>
 * Анализ диапазона:
 * верхняя: E4
 * нижняя: F3
 * диапазон: 11
 * <p>
 * Анализ длительности нот (мс):
 * 4285: 10
 * 2144: 5
 * <p>
 * Анализ нот по высоте:
 * E4: 3
 * D4: 3
 * A3: 3
 * G3: 3
 * F3: 3
 * <p>
 * Анализ интервалов:
 * 2: 9
 * 5: 3
 * 11: 2
 */
public class App {
    public static void main(String[] args) {
        SimpleMidiFile simpleMidiFile = new SimpleMidiFile(Content.ZOMBIE);
        System.out.println("Всего нот: " + simpleMidiFile.vocalNoteList().size());

        Note highNote = null;
        Note lowNote = null;

        Map<Long, Long> noteLengthCountMap = new TreeMap<>(Comparator.comparingLong(o -> -o));
        Map<NoteSign, Long> noteHeightCountMap = new TreeMap<>(new NoteHeightComparator());
        Map<Integer, Integer> noteIntervalCountMap = new TreeMap<>();

        Note previousNote = null;

        Float tickInMs = simpleMidiFile.tickInMs();

        long count;
        for (Note note : simpleMidiFile.vocalNoteList()) {
            count = 0;
            long noteLength = (long) (note.durationTicks() * tickInMs);
            if (noteLengthCountMap.containsKey(noteLength)) {
                count = noteLengthCountMap.get(noteLength);
            }
            noteLengthCountMap.put(noteLength, (count + 1));

            count = 0;
            if (noteHeightCountMap.containsKey(note.sign())) {
                count = noteHeightCountMap.get(note.sign());
            }
            noteHeightCountMap.put(note.sign(), (count + 1));

            if (highNote != null && lowNote != null) {
                if (lowNote.sign().higher(note.sign())) {
                    lowNote = note;
                }
                if (highNote.sign().lower(note.sign())) {
                    highNote = note;
                }
            } else {
                highNote = note;
                lowNote = note;
                continue;
            }

            if (previousNote != null) {
                Integer diff = Math.abs(note.sign().getMidi() - previousNote.sign().getMidi());
                count = 0;
                if (noteIntervalCountMap.containsKey(diff)) {
                    count = noteIntervalCountMap.get(diff);
                }
                noteIntervalCountMap.put(diff, (int) (count + 1));
            }
            previousNote = note;
        }

        System.out.println();
        System.out.println("Анализ диапазона:");
        System.out.print("верхняя: ");
        System.out.println(highNote.sign().fullName());
        System.out.print("нижняя: ");
        System.out.println(lowNote.sign().fullName());
        System.out.print("диапазон: ");
        System.out.println(highNote.sign().diffInSemitones(lowNote.sign()));

        System.out.println();
        System.out.println("Анализ длительности нот (мс):");
        noteLengthCountMap.keySet().forEach(key ->
                System.out.println(String.valueOf(key) + ": " + noteLengthCountMap.get(key)));

        System.out.println();
        System.out.println("Анализ нот по высоте:");
        noteHeightCountMap.keySet().forEach(key ->
                System.out.println(key.fullName() + ": " + String.valueOf(noteHeightCountMap.get(key))));

        System.out.println();
        System.out.println("Анализ интервалов:");
        noteIntervalCountMap.keySet().forEach(key ->
                System.out.println(String.valueOf(key) + ": " + String.valueOf(noteIntervalCountMap.get(key))));
    }

    private static class NoteHeightComparator implements Comparator<NoteSign> {

        @Override
        public int compare(NoteSign o1, NoteSign o2) {
            return o2.moreThanInSemitones(o1);
        }

    }
}

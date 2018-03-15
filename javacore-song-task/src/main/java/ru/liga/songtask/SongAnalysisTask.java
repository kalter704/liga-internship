package ru.liga.songtask;

import ru.liga.songtask.domain.SimpleMidiFile;
import ru.liga.songtask.writes.BaseWriter;

import java.io.File;
import java.io.IOException;

public class SongAnalysisTask implements Task {

    private final SongAnalysis songAnalysis;

    private final BaseWriter writer;

    public SongAnalysisTask(File midiFile, BaseWriter writer) {
        this.songAnalysis = new SongAnalysis(new SimpleMidiFile(midiFile));
        this.writer = writer;
    }

    @Override
    public void execute() {
        writer.writeln("Всего нот: " + String.valueOf(songAnalysis.numberOfNotes()));
        writer.writeln();
        writer.writeln("Анализ диапазона:");
        writer.writeln("верхняя: " + songAnalysis.getHigherNote().sign().fullName());
        writer.writeln("нижняя: " + songAnalysis.getLowerNote().sign().fullName());
        writer.writeln("диапазон: " + String.valueOf(songAnalysis.getHigherNote().sign().diffInSemitones(songAnalysis.getLowerNote().sign())));

        writer.writeln();
        writer.writeln("Анализ длительности нот (мс):");
        songAnalysis.noteLengthCount().forEach((count, length) -> {
            writer.writeln(count.toString() + ": " + length.toString());
        });

        writer.writeln();
        writer.writeln("Анализ нот по высоте:");
        songAnalysis.noteHeightCount().forEach((noteSign, count) -> {
            writer.writeln(noteSign.fullName() + ": " + count.toString());
        });

        writer.writeln();
        writer.writeln("Анализ интервалов:");
        songAnalysis.noteIntervalCount().forEach((interval, count) -> {
            writer.writeln(interval.toString() + ": " + count.toString());
        });

        writer.close();

    }
}

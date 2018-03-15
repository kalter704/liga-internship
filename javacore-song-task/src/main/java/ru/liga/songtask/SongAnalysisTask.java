package ru.liga.songtask;

import ru.liga.songtask.domain.SimpleMidiFile;
import ru.liga.songtask.writes.BaseWrite;

import java.io.File;
import java.io.IOException;

public class SongAnalysisTask implements Task {

    private SongAnalysis songAnalysis;

    private BaseWrite writer;

    public SongAnalysisTask(File midiFile, BaseWrite writer) {
        this.songAnalysis = new SongAnalysis(new SimpleMidiFile(midiFile));
        this.writer = writer;
    }

    @Override
    public void execute() {
        try {

            writer.writeln("Всего нот: " + String.valueOf(songAnalysis.numberOfNotes()));
            writer.writeln();
            writer.writeln("Анализ диапазона:");
            writer.writeln("верхняя: " + songAnalysis.getHigherNote().sign().fullName());
            writer.writeln("нижняя: " + songAnalysis.getLowerNote().sign().fullName());
            writer.writeln("диапазон: " + String.valueOf(songAnalysis.getHigherNote().sign().diffInSemitones(songAnalysis.getLowerNote().sign())));

            writer.writeln();
            writer.writeln("Анализ длительности нот (мс):");
            songAnalysis.noteLengthCount().forEach((count, length) -> {
                try {
                    writer.writeln(count.toString() + ": " + length.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.writeln();
            writer.writeln("Анализ нот по высоте:");
            songAnalysis.noteHeightCount().forEach((noteSign, count) -> {
                try {
                    writer.writeln(noteSign.fullName() + ": " + count.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.writeln();
            writer.writeln("Анализ интервалов:");
            songAnalysis.noteIntervalCount().forEach((interval, count) -> {
                try {
                    writer.writeln(interval.toString() + ": " + count.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

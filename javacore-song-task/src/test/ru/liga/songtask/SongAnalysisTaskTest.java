package ru.liga.songtask;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.Resources;
import ru.liga.songtask.writes.FileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SongAnalysisTaskTest {

    private final String fileName = "zombie.mid";

    @Test
    public void execute() throws Exception {

        File originalFile = new Resources(fileName).getFile();

        String path = originalFile.getParent();

        String fileResultName = path + "/analysisTest";
        String filePreparedData = path + "/analysisForTest";

        SongAnalysisTask task = new SongAnalysisTask(new Resources(fileName).getFile(),
                new FileWriter(new File(fileResultName)));
        task.execute();

        String result = readFile(fileResultName);
        String preparedData = readFile(filePreparedData);

        Assertions.assertThat(result).isEqualTo(preparedData);
    }

    private String readFile(String fileName) throws FileNotFoundException {
        FileReader resultReader = new FileReader(fileName);
        StringBuilder builder = new StringBuilder();
        Scanner sc = new Scanner(resultReader);
        while (sc.hasNext()) {
            builder.append(sc.nextLine());
        }
        return builder.toString();
    }

}
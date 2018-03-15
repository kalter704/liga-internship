package ru.liga;

import java.io.File;

public class Resources {

    private final String fileName;

    public Resources(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file;
    }

}

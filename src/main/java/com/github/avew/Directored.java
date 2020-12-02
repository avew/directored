package com.github.avew;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;

public class Directored {

    public String createFile(String tmpPath, String newDir, String fileName) {

        String dir = tmpPath + File.separator + newDir;

        File file = new File(dir + File.separator + fileName);
        if (file.exists()) {
            try {

                boolean deleteIfExists = Files.deleteIfExists(file.toPath());
                if (deleteIfExists) {
                    return createFile(tmpPath, newDir, fileName);
                }
            } catch (FileSystemException e) {
                if (!file.delete()) {
                    // wait a bit then retry on Windows
                    if (file.exists()) {
                        for (int i = 0; i < 6; i++) {
                            try {
                                Thread.sleep(500);
                                System.gc();
                                if (file.delete()) {

                                    return createFile(tmpPath, newDir, fileName);
                                }
                                break;
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        File directory = new File(dir);
        if (!directory.exists()) {

            boolean mkdirs = directory.mkdirs();
        }
        return dir + File.separator + fileName;
    }
}

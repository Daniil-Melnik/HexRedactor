package main.java.hexeditor.Utils;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Utility class for file operations.
 * Provides methods to copy, move, and clear files.
 * 
 * @autor DMelnik
 * @version 1.0
 */

public class FileManager {
    /**
     * Moves or copies a file from the old path to the new path.
     *
     * @param fileNameOld the path of the source file
     * @param fileNameNew the path of the destination file
     * @param isCopy      if true, the file is copied; if false, the file is moved
     * @throws IOException if an I/O error occurs
     */
    public void setFile(String fileNameOld, String fileNameNew, boolean isCopy) throws IOException {
        if (!isCopy)
            clearFile(fileNameNew);
        copyFileUsingStream(fileNameOld, fileNameNew);
        Path pathOld = Paths.get(fileNameOld);
        Files.delete(pathOld);
    }

    /**
     * Copies a file using streams.
     *
     * @param fileNameOld the path of the source file
     * @param fileNameNew the path of the destination file
     * @throws IOException if an I/O error occurs
     */
    private static void copyFileUsingStream(String fileNameOld, String fileNameNew) throws IOException {
        File source = new File(fileNameOld);
        File dest = new File(fileNameNew);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            int copyBufLen = 1024;
            byte[] buffer = new byte[copyBufLen];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    /**
     * Clears the content of a file.
     *
     * @param fName the path of the file to clear
     * @throws IOException if an I/O error occurs
     */

    public void clearFile(String fName) throws IOException {
        Path path = Paths.get(fName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            // Пустой блок, так как нам нужно только очистить файл
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            FileManager fM = new FileManager();
            fM.clearFile("example.txt");
        } catch (NoSuchFileException e) {
            System.out.println("Нет такого файла");
        }

    }
}

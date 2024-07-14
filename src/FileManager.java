import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
* Класс с утилитами
* Назначение: работа с файлами
* */

public class FileManager {
    ///////////////////////////////////////////////////////////////
    ////////////////////// Переставить файлы //////////////////////
    ///////////////////////////////////////////////////////////////
    public void setFile(String fNameOld, String fNameNew, boolean isCopy) throws IOException {
        if (!isCopy)
            clearFile(fNameNew);
        copyFileUsingStream(fNameOld, fNameNew);
        Path pathOld = Paths.get(fNameOld);
        Files.delete(pathOld);
    }

    ///////////////////////////////////////////////////////////////
    /////////////////////// Копировать файл ///////////////////////
    ///////////////////////////////////////////////////////////////
    private static void copyFileUsingStream(String fNameOld, String fNameNew) throws IOException {
        File source = new File(fNameOld);
        File dest = new File(fNameNew);
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

    ///////////////////////////////////////////////////////////////
    //////////////////////// Очистить файл ////////////////////////
    ///////////////////////////////////////////////////////////////
    // public void clearFile(String fName) throws IOException {
    // Path path = Paths.get(fName);
    // Files.writeString(path, new byte[0]);
    // }

    public void clearFile(String fName) throws IOException {
        Path path = Paths.get(fName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            // Пустой блок, так как нам нужно только очистить файл
        }
    }

    public static void main(String[] args) throws IOException {
        FileManager fM = new FileManager();
        fM.clearFile("example.txt");
    }
}

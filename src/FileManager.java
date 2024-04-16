import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// fNameNew -> 1.txt
// fNameOld -> example.txt

public class FileManager {
    ///////////////////////////////////////////////////////////////
    ////////////////////// Переставить файлы //////////////////////
    ///////////////////////////////////////////////////////////////
    public void setFile(String fNameOld, String fNameNew) throws IOException {
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
            byte[] buffer = new byte[1024];
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
    public void clearFile(String fName) throws IOException {
        Path path = Paths.get(fName);
        Files.writeString(path, "");
    }

    public static void main(String [] args) throws IOException {
        FileManager fM = new FileManager();
        fM.clearFile("example.txt");
    }
}

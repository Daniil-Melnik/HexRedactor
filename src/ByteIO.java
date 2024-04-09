import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class ByteIO {
    private final String fName;

    public ByteIO(String fName){
        this.fName = fName;
    }

    ////////////////////////////////////////////////////////////////
    ///////////////////// Получить все байты ///////////////////////
    ////////////////////////////////////////////////////////////////

    public String [] getHexBytesAll() throws IOException {
        Path path = Paths.get(this.fName);
        byte [] bytes = Files.readAllBytes(path);
        String [] hexBytes = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++){
            hexBytes[i] = Integer.toHexString(bytes[i]);
        }
        return hexBytes;
    }

    ////////////////////////////////////////////////////////////////
    ///////////////// Получить байты по смещению ///////////////////
    ////////////////////////////////////////////////////////////////

    public String [] getHexBytesOfft(int offt, int len){
        String [] hexBytesOfft = null;
        try (RandomAccessFile file = new RandomAccessFile(this.fName, "r")) {
            byte[] bytes = new byte[len];
            file.seek(offt);
            file.read(bytes);
            hexBytesOfft = new String[len];
            for (int i = 0; i < len; i++){
                hexBytesOfft[i] = Integer.toHexString(bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hexBytesOfft;
    }
}

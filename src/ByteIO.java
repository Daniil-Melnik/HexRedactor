import javax.swing.plaf.synth.SynthLookAndFeel;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.nio.file.Paths;
import java.nio.file.Files;

public class ByteIO {
    private final String fName;
    private byte [] bytes;
    public ByteIO(String fName){
        this.fName = fName;
    }
    public byte [] getByteOfFile() throws IOException {
        Path path = Paths.get(this.fName);

        return Files.readAllBytes(path);
    }

    public static void main(String[] args) throws IOException {
        ByteIO test = new ByteIO("src/1.txt");
        byte [] tData = test.getByteOfFile();
        for (byte datum : tData) {
            System.out.println(datum);
        }
    }
}

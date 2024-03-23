import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class ByteIO {
    private final String fName;
    private byte [] bytes;
    private String [] hexBytes;

    public  void setBytes(byte [] bytes){
        this.bytes = bytes;
    }

    public byte [] getBytes(){
        return this.bytes;
    }

    public String [] getHexByte(){
        return this.hexBytes;
    }

    public ByteIO(String fName){
        this.fName = fName;
    }

    public void getByteOfFile() throws IOException {
        Path path = Paths.get(this.fName);
        this.bytes = Files.readAllBytes(path);
        String [] hexBytes = new String[this.bytes.length];
        for (int i = 0; i < this.bytes.length; i++){
            hexBytes[i] = Integer.toHexString(this.bytes[i]);
        }
        this.hexBytes = hexBytes;
    }

    public static void main(String[] args) throws IOException {
        ByteIO test = new ByteIO("src/1.txt");
        test.getByteOfFile();
        byte [] tData = test.getBytes();
        for (byte datum : tData) {
            System.out.println(datum);
        }
    }
}

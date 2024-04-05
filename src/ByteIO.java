import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class ByteIO {
    private final String fName;
    private byte [] bytes;
    private String [] hexBytes;
    private byte [] bytesOfft;
    private String [] hexBytesOfft;

    public  void setBytes(byte [] bytes){
        this.bytes = bytes;
    }

    public byte [] getBytes(){
        return this.bytes;
    }

    public String [] getHexBytesOfft(){
        return this.hexBytesOfft;
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

    public String [] setHexBytesOfft(int offt, int len){
        try (RandomAccessFile file = new RandomAccessFile(this.fName, "r")) {
            byte[] bytes = new byte[len];
            file.seek(offt);
            file.read(bytes);
            this.bytesOfft = bytes;
            String [] hexQBytes = new String[len];
            for (int i = 0; i < len; i++){
                hexQBytes[i] = Integer.toHexString(this.bytesOfft[i]);
            }
            this.hexBytesOfft = hexQBytes;
           
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hexBytesOfft;
    }

    public void deletebytes(){
        
    }

    public static void main(String[] args) throws IOException {
        ByteIO test = new ByteIO("src/1.txt");
        test.getByteOfFile();
        test.setHexBytesOfft(117, 10);
        String [] tData = test.getHexBytesOfft();
        for (String datum : tData) {
            System.out.print(datum + " ");
        }
    }
}

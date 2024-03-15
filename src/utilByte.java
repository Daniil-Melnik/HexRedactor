import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class utilByte {
    private byte [] bytes;

    public utilByte(byte [] bytes){
        this.bytes = bytes;
    }

    public String [][] toArr(int len){
        String [][] result = new String[this.bytes.length/len + 1][len];
        for (int i = 0; i < this.bytes.length; i++){
            result[i / len][i % len] = "" + this.bytes[i];
            //System.out.println( i / 4 + " " + i % 4);
        }
        return result;
    }

    public static void main(String [] args) throws IOException {
        ByteIO bIO = new ByteIO("src/1.txt");
        bIO.getByteOfFile();
        utilByte ub = new utilByte(bIO.getBytes());
        //System.out.println(bIO.getBytes()[0]);
        String [][] b = ub.toArr(4);
        for (String [] datum : b) {
            for (String bdat : datum){
                System.out.print(bdat + " ");
            }
            System.out.println();
        }
    }
}

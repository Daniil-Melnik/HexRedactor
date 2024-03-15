import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class utilByte {
    private byte [] bytes;

    public utilByte(byte [] bytes){
        this.bytes = bytes;
    }

    public String [][] toFour(){
        String [][] result = new String[this.bytes.length/4 + 1][4];
        for (int i = 0; i < this.bytes.length; i++){
            result[i / 4][i % 4] = "" + this.bytes[i];
            //System.out.println( i / 4 + " " + i % 4);
        }
        return result;
    }

    public static void main(String [] args) throws IOException {
        ByteIO bIO = new ByteIO("src/1.txt");
        bIO.getByteOfFile();
        utilByte ub = new utilByte(bIO.getBytes());
        //System.out.println(bIO.getBytes()[0]);
        String [][] b = ub.toFour();
        for (String [] datum : b) {
            for (String bdat : datum){
                System.out.print(bdat + " ");
            }
            System.out.println();
        }
    }
}

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class utilByte {
    private byte [] bytes;
    private int rowLen;

    public utilByte(byte [] bytes){
        this.bytes = bytes;
    }

    public String [][] toArr(int len){
        int k = this.bytes.length / len + 1;
        if (this.bytes.length % len != 0) {
            k = k + 1;
        }
        String [][] result = new String[k][len + 1];
        for (int i = 0; i < this.bytes.length; i++){
            result[i / len][i % len] = "" + this.bytes[i];
            //System.out.println( i / 4 + " " + i % 4);
        }
        for (int i = 0; i < k; i++){
            result[i][0] = "" + (i) * (len - 1);
        }
        return result;
    }

    public String [] getIndexes (int len){
        int k = this.bytes.length / len;
        if (this.bytes.length % len != 0) {
            k = k + 1;
        }
        String [] res = new String[k];
        for (int i = 0; i < k; i++){
            res[i] = "" + len * i;
        }

        return res;
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

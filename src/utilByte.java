import javax.swing.*;
import java.io.IOException;

public class utilByte {
    private byte [] bytes;
    private String [] hexBytes;

    public utilByte(byte [] bytes, String [] hexBytes){
        this.bytes = bytes;
        this.hexBytes = hexBytes;
    }

    public Object [][] toArr(int len, int offset, int vertLen){
        int k = this.hexBytes.length / len + 1;
        int endLen;
        if (this.hexBytes.length % len != 0) {
            k = k + 1;
        }
        Object [][] chngWidth = new Object[k][len + 1];
        Object [][] result = new Object[vertLen][len + 1];
        for (int i = 0; i < this.hexBytes.length; i++){
            chngWidth[i / len][i % len] = "" + this.hexBytes[i];
        }
        for (int i = 0; i < k; i++){
            chngWidth[i][0] = new JLabel("" + (i) * (len - 1));
        }
        if ((offset + len * vertLen) >= this.hexBytes.length){
            endLen = chngWidth.length;
        }
        else {
            endLen = (offset / len) + vertLen;
        }

        int km = 0;
        for (int i = offset / len; i < endLen; i++){
            result[km] = chngWidth[i];
            //System.out.println(result[km]);
            km++;
        }
        for (int i = endLen; i < vertLen; i++){
            result[km][0] = new JLabel("" + (i) * (len - 1));
            for (int j = 1; j < len+1; j++){
                result[km][j] = "";
            }
            km++;
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
        utilByte ub = new utilByte(bIO.getBytes(), bIO.getHexByte());
        //System.out.println(bIO.getBytes()[0]);
        Object [][] b = ub.toArr(4, 0, 2);
        for (Object [] datum : b) {
            for (Object bdat : datum){
                System.out.print(bdat + " ");
            }
            System.out.println();
        }
    }
}

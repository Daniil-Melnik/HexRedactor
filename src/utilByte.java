import javax.swing.*;
import java.io.IOException;

public class utilByte {
    private final byte [] bytes;
    private final String [] hexBytes;

    public utilByte(byte [] bytes, String [] hexBytes){
        this.bytes = bytes;
        this.hexBytes = hexBytes;
    }

    public Object [][] toArr(int len, int offset, int vertLen){
        int endLen;
        if ((offset + len * vertLen) >= this.hexBytes.length){
            endLen = (this.hexBytes.length - offset) / len + 1;
        }
        else {
            endLen = vertLen;
        }
        Object [][] chngWidth = new Object[endLen][len + 1];

        for (int i = offset; i < offset + (len * endLen); i++){
            if (i < this.hexBytes.length){
                chngWidth[(i-offset) / len][(i-offset) % len] = "" + this.hexBytes[i];
            }
        }
        int iOfft = offset;
        for (int i = 0; i < endLen; i++){
            chngWidth[i][0] = new JLabel("" + (iOfft));
            iOfft += len - 1;
        }
        System.out.println(endLen);
        return chngWidth;
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
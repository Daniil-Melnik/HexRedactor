import javax.swing.*;
import java.io.IOException;

public class utilByte {
    private final byte [] bytes;
    private final String [] hexBytes;
    private String [] hexBytesOfft;

    public utilByte(String [] hexBytesOfft){
        this.hexBytesOfft = hexBytesOfft;
        this.bytes = null;
        this.hexBytes = null;
    }

    public Object [][] toArr(int len, int offset, int vertLen){
        int endLen;
        endLen = vertLen;
        Object [][] chngWidth = new Object[vertLen][len + 1];

        System.out.println("len = " + len);

        // for (int k = 0; k < this.hexBytesOfft.length; k++){
        //     System.out.print(this.hexBytesOfft[k] + " ");
        // }
        // System.out.println();

        for (int i = 0; i < this.hexBytesOfft.length; i++){
            chngWidth[i / (len - 1)][i % (len - 1) + 1] = "" + this.hexBytesOfft[i];
            //System.out.println(i + " " + (i / (len - 1)) + " " + (i % (len - 1) + 1) + " " + this.hexBytesOfft[i]);
            //System.out.println(i / len + " " + (i % (len - 1) + 1) + " " + this.hexBytesOfft.length);
        }
        int iOfft = offset;
        for (int i = 0; i < endLen; i++){
            chngWidth[i][0] = new JLabel("" + (iOfft));
            iOfft += len - 1;
        }
        System.out.println(endLen);
        for (int i = 0; i < vertLen; i++){
            for (int j = 1; j < len; j++){
                System.out.print(chngWidth[i][j] + " ");
            }
            System.out.println();
        }
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

    // добавить чтение байт в 16ричном формате

    public static void main(String [] args) throws IOException {
        ByteIO bIO = new ByteIO("src/1.txt");
        bIO.getByteOfFile();
        // utilByte ub = new utilByte(bIO.getBytes(), bIO.getHexByte(), );
        // //System.out.println(bIO.getBytes()[0]);
        // Object [][] b = ub.toArr(4, 0, 2);
        // for (Object [] datum : b) {
        //     for (Object bdat : datum){
        //         System.out.print(bdat + " ");
        //     }
        //     System.out.println();
        // }
    }
}
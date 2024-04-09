import javax.swing.*;
import java.io.IOException;

public class utilByte {

    public Object [][] toSimpleArr(int rowLen, int columnLen, String [] data){
        //////Дописать перевод в простой массив//////
        return null;
    }

    public Object [][] toArr(int len, int offset, int vertLen){
        int endLen;
        endLen = vertLen;
        Object [][] chngWidth = new Object[vertLen][len + 1];

        for (int i = 0; i < this.hexBytesOfft.length; i++){
            chngWidth[i / (len - 1)][i % (len - 1) + 1] = "" + this.hexBytesOfft[i];
        }
        int iOfft = offset;
        for (int i = 0; i < endLen; i++){
            chngWidth[i][0] = new JLabel("" + (iOfft));
            iOfft += len - 1;
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

}
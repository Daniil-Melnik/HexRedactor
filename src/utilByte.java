import javax.swing.*;
import java.io.IOException;
// toArr -> toLabeledArr

public class utilByte {
    ///////////////////////////////////////////////
    /////////Получить простой массив строк/////////
    ///////////////////////////////////////////////
    public Object [][] toSimpleArr(int rowLen, int columnLen, String [] data){
        Object [][] arr = new Object[columnLen][rowLen];
        for (int i = 0; i < data.length; i++){
            arr[i / rowLen][i % rowLen] = data[i];
        }
        return arr;
    }

    ////////////////////////////////////////////////
    ////////Получить массив строк с лэйблами////////
    ////////////////////////////////////////////////
    public Object [][] toLabeledArr(int rowLen, int columnLen, String [] data, int offt){
        Object [][] resArr = new Object[columnLen][rowLen + 1];

        for (int i = 0; i < data.length; i++){
            resArr[i / rowLen][(i % rowLen) + 1] = data[i];
        }
        for (int i = 0; i < columnLen; i++){
            resArr[i][0] = new JLabel("" + (offt + (i * rowLen)));
        }
        return resArr;
    }

    public static void main(String[] args){
        ByteIO bIO = new ByteIO("src/1.txt");
        String [] data = bIO.getHexBytesOfft(0, 16);
        utilByte ub = new utilByte();
        Object [][] resArr = ub.toSimpleArr(4, 4, data);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                System.out.print(resArr[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
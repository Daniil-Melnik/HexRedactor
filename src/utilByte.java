import javax.swing.*;
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


    /////////////////////////////////////////////////
    /////////////Обнуление части массива/////////////
    /////////////////////////////////////////////////
    public String [] addZerosToArr(String [] data, int len, int startPosition){
        String [] res = data;
        for (int i = startPosition; i < startPosition + len; i++){
            res[i] = "00";
        }
        return res;
    }

    ////////////////////////////////////////////////
    /////////////Удаление части массива/////////////
    ////////////////////////////////////////////////
    public String [] removeFromArr(String [] data, int len, int startPosition){
        String [] res = null;

        int newLen = data.length - len;
        int currArrPosition = 0;
        int newLeftPosition = startPosition + len;
        res = new String[newLen];

        for (int i = 0; i < startPosition; i++){
            res[currArrPosition] = data[i];
            currArrPosition++;
        }

        for (int i = newLeftPosition; i < data.length; i++){
            res[currArrPosition] = data[i];
            currArrPosition++;
        }

        return res;
    }

    ////////////////////////////////////////////////
    /////////////Склейка двух массивов//////////////
    ////////////////////////////////////////////////
    public String [] concatArrs(String [] dataLeft, String [] dataRight){
        String [] res = null;

        int newLen = dataLeft.length + dataRight.length;
        int currPosition = 0;

        res = new String[newLen];

        for (int i = 0; i < dataLeft.length; i++){
            res[currPosition] = dataLeft[i];
            currPosition++;
        }

        for (int i = 0; i < dataRight.length; i++){
            res[currPosition] = dataRight[i];
            currPosition++;
        }
        return res;
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
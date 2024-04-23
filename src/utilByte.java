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
    public String [] removeFromArrZero(String [] data, int len, int startPosition){
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

    ///////////////////////////////////////////////  offt - сдвиг внутри таблицы
    ////////////Вставка нулей в массив/////////////
    ///////////////////////////////////////////////
    public String [] fillInZeros (String [] data, int offt, int len){
        int newDataLen = data.length + len;
        String [] res = new String [newDataLen];
        int currIndex = 0;
        for (int i = 0; i <= offt; i++){
            res[currIndex] = data[i];
            currIndex++;
        }
        for (int j = 0; j < len; j++){
            res[currIndex] = "00";
            currIndex++;
        }
        for (int k = offt + 1; k < data.length; k++){
            res[currIndex] = data[k];
            currIndex++;
        }
        return res;
    }

    //////////////////////////////////////////////  offt - сдвиг внутри таблицы
    /////////Дополнение массива звёздами//////////
    //////////////////////////////////////////////

    public String [] fillInStars(String [] data, int len){
        int oldLen = data.length;
        int newLen = oldLen + len;
        String [] res = new String[newLen];
        for (int i = 0; i < oldLen; i++){
            res[i] = data[i];
        }
        for (int j = oldLen; j < newLen; j++) {
            res[j] = "**";
        }
        return res;
    }

    public static void main(String[] args){
        ByteIO bIO = new ByteIO("src/1.txt");
        String [] data = bIO.getHexBytesOfft(0, 16);
        utilByte ub = new utilByte();
        String [] resArr = ub.removeFromArrZero(data, 5, 1);
        for (int i = 0; i < 16; i++){
            System.out.print(resArr[i] + " ");
        }
    }
}
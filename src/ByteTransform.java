import java.math.BigInteger;

/*
* Класс с утилитами
* Назначение:
* 1) Преобразование байт или блоков байт в А2
* 2) Выполнение операций поиска
* */

public class ByteTransform {
    
    /////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    /////////////// Вернуть двоичную строку из байт ///////////////
    ///////////////////////////////////////////////////////////////
    public String getBinaryStr(String [] data, int offt, int len){
        int [] inIntArr = new int[len];
        for (int i = 0; i < len; i++){
            inIntArr[i] = Integer.parseInt(data[offt + i], 16);
        }

        String [] binaryArr = new String [len];
        for (int j = 0; j < len; j++){
            binaryArr[j] = Integer.toBinaryString(inIntArr[j]);
            int p = binaryArr[j].length();
            int numberOfZeros = 8 - p;
            if (numberOfZeros > 0) {
                StringBuilder zerosBuilder = new StringBuilder();
                for (int i = 0; i < numberOfZeros; i++) {
                    zerosBuilder.append('0');
                }
                String zeros = zerosBuilder.toString();
                binaryArr[j] = zeros + binaryArr[j];
            }
        }
        
        String concBinStr = "";
        for (String str : binaryArr) concBinStr += str;

        return concBinStr;
    }    
    
    //////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    //////////////// Преобразование байт в знаковое ////////////////
    ////////////////////////////////////////////////////////////////
    public long getSigned(String [] data, int offt, int len){
        String concBinStr = getBinaryStr(data, offt, len);
        String subStr = concBinStr.substring(1);
        long res = Long.parseLong(subStr, 2);

        res *= concBinStr.charAt(0) == '1' ? -1 : 1;

        return res;
    }

    ///////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    /////////////// Преобразование байт в беззнаковое ///////////////
    /////////////////////////////////////////////////////////////////
    public BigInteger getUnsigned(String [] data, int offt, int len){
        String concBinStr = getBinaryStr(data, offt, len);

        BigInteger res = new BigInteger(concBinStr, 2);
   
        return res;
    }

    /////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    //////// Преобразование байт в вещественное 1 точночти ////////
    ///////////////////////////////////////////////////////////////
    public float getFloat(String [] data, int offt, int len){
        String concBinStr = getBinaryStr(data, offt, len);

        String subStr = concBinStr.substring(1);
        int intValue = Integer.parseInt(subStr, 2);

        float res = Float.intBitsToFloat(intValue);
        res *= concBinStr.charAt(0) == '1' ? -1 : 1;

        return res;
    }

    /////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    //////// Преобразование байт в вещественное 2 точночти ////////
    ///////////////////////////////////////////////////////////////
    public double getDouble(String [] data, int offt, int len){
        String concBinStr = getBinaryStr(data, offt, len);
        String subStr = concBinStr.substring(1);
        long longValue = Long.parseLong(subStr, 2);
        double res = Double.longBitsToDouble(longValue);
        
        res *= concBinStr.charAt(0) == '1' ? -1 : 1;

        return res;
    }

    ////////////////////////////////////////////////////////////////
    //////////////////// Поиск блоков по целому ////////////////////
    ////////////////////////////////////////////////////////////////
    public int [] getByteOffsetsValue(String [] data, int len, BigInteger  num){
        int cnt = 0;
        String cntStr = "";
        String targetStr = num.toString(2);
        int tmp = len * 8 - targetStr.length();
        for (int i = 0; i < tmp; i++){
            targetStr = "0" + targetStr;
        }
        for (int i = 0; i < data.length - 7; i++){
            cntStr = getBinaryStr(data, i, len);
            if (cntStr.equals(targetStr)){
                cnt++;
            }
        }

        int [] res = new int[cnt];
        int tCnt = 0;
        for (int j = 0; j < data.length - 7; j++){
            cntStr = getBinaryStr(data, j, len);
            if (cntStr.equals(targetStr)){
                res[tCnt] = j;
                tCnt++;
            }
        }

        return res;
    }

    ////////////////////////////////////////////////////////////////
    /////////// Проверка валидности байта в А2 маске ///////////////
    ////////////////////////////////////////////////////////////////
    public boolean isValidForMask(String dataEl, String mask){
        int cnt = 0;

        for (int i = 0; i < mask.length(); i++){
            if ((dataEl.charAt(cnt) == mask.charAt(cnt)) || (mask.charAt(cnt) == '*')){
                cnt++;
            }
        }
        return mask.length() == cnt;
    }

    ////////////////////////////////////////////////////////////////
    /////////////// Подгонка маски под размер блока ////////////////
    ////////////////////////////////////////////////////////////////
    public String updMask (String oldMask, int currLen){
        String res = oldMask;
        int oldLen = oldMask.length();
        if (oldLen < currLen){
            int disLen = currLen - oldLen;
            for (int i = 0; i < disLen; i++){
                res += "*";
            }
        }
        else if (oldLen > currLen){
            res = oldMask.substring(0, currLen);
        }
        return  res;
    }

    ////////////////////////////////////////////////////////////////
    //////////// Получить набор сдвигов валидных блоков ////////////
    ////////////////////////////////////////////////////////////////
    public int [] getBytesOffsetMask(String [] data, int len, String mask){
        int numEl = 0;
        int maskByteLen = 8;
        String newMask = updMask(mask, len * maskByteLen);
        for (int i = 0; i < data.length - maskByteLen + 1; i++){
            String tmpStr = getBinaryStr(data, i, len);
            if (isValidForMask(tmpStr, newMask)) numEl++;
        }

        int [] res = new int [numEl];
        int k = 0;

        for (int i = 0; i < data.length - maskByteLen + 1; i++){
            String tmpStr = getBinaryStr(data, i, len);
            if (isValidForMask(tmpStr, newMask)) {
                res[k] = i;
                k++;
            }
        }
        return res;
    }
}

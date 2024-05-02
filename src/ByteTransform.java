import java.math.BigInteger;

public class ByteTransform {
    
    /////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    /////////////// Вернуть двоичную строку из байт ///////////////
    ///////////////////////////////////////////////////////////////
    public String getBinaryStr(String [] data, int offt, int len){
        int [] inIntArr = new int[len];
        //System.out.println("------------------------------------------");
        for (int i = 0; i < len; i++){
            inIntArr[i] = Integer.parseInt(data[offt + i], 16);
        }

        String [] binaryArr = new String [len];
        for (int j = 0; j < len; j++){
            binaryArr[j] = Integer.toBinaryString(inIntArr[j]);
            int p = binaryArr[j].length();
            switch (p){
                case 0:
                break;
                case 1:
                    binaryArr[j] = "0000000" + binaryArr[j];
                    break;
                case 2:
                    binaryArr[j] = "000000" + binaryArr[j];
                    break;
                case 3:
                    binaryArr[j] = "00000" + binaryArr[j];
                    break;
                case 4:
                    binaryArr[j] = "0000" + binaryArr[j];
                    break;
                case 5:
                    binaryArr[j] = "000" + binaryArr[j];
                    break;
                case 6:
                    binaryArr[j] = "00" + binaryArr[j];
                    break;
                case 7:
                    binaryArr[j] = "0" + binaryArr[j];
                    break;
                case 8:
                    break;
            }
            //System.out.println(binaryArr[j]);
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
}

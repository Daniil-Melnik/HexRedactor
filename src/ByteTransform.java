public class ByteTransform {
    
    /////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    /////////////// Вернуть двоичную строку из байт ///////////////
    ///////////////////////////////////////////////////////////////
    public String getBinaryStr(String [] data, int offt, int len){
        int [] inIntArr = new int[len];
        System.out.println("------------------------------------------");
        for (int i = 0; i < len; i++){
            inIntArr[i] = Byte.parseByte(data[offt + i], 16);
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
    public int getSigned(String [] data, int offt, int len){
        String concBinStr = getBinaryStr(data, offt, len);
        // System.out.println(concBinStr);
        int res = Integer.parseInt(concBinStr, 2);

        return res;
    }

    ///////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    /////////////// Преобразование байт в беззнаковое ///////////////
    /////////////////////////////////////////////////////////////////
    public long getUnsigned(String [] data, int offt, int len){
        String concBinStr = getBinaryStr(data, offt, len);
        long res = Long.parseUnsignedLong(concBinStr, 2);

        return res;
    }

    /////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    //////// Преобразование байт в вещественное 1 точночти ////////
    ///////////////////////////////////////////////////////////////
    public float getFloat(String [] data, int offt, int len){
        String concBinStr = getBinaryStr(data, offt, len);
        int intValue = Integer.parseInt(concBinStr, 2);
        float res = Float.intBitsToFloat(intValue);

        return res;
    }

    /////////////////////////////////////////////////////////////// offt - сдвиг в таблице
    //////// Преобразование байт в вещественное 2 точночти ////////
    ///////////////////////////////////////////////////////////////
    public double getDouble(String [] data, int offt, int len){
        String concBinStr = getBinaryStr(data, offt, len);
        long longValue = Long.parseLong(concBinStr, 2);
        double res = Double.longBitsToDouble(longValue);

        return res;
    }
}

import java.math.BigInteger;

/**
 * Utility class for byte transformation and operations.
 * <p>
 * This class provides methods to:
 * <ul>
 * <li>Convert bytes or blocks of bytes to binary, signed, unsigned, float, and
 * double values.</li>
 * <li>Perform search operations on byte arrays.</li>
 * </ul>
 * 
 * @autor DMelnik
 * @version 1.0
 */

public class ByteTransform {

    /**
     * Converts bytes to a binary string.
     * 
     * @param data the input byte array in hexadecimal format
     * @param offt the offset in the array
     * @param len  the length of bytes to convert
     * @return a binary string representation of the specified bytes
     */
    public String getBinaryStr(String[] data, int offt, int len) {
        int[] inIntArr = new int[len];
        for (int i = 0; i < len; i++) {
            inIntArr[i] = Integer.parseInt(data[offt + i], 16);
        }

        String[] binaryArr = new String[len];
        for (int j = 0; j < len; j++) {
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

        StringBuilder concBinStrBuilder = new StringBuilder();
        for (String str : binaryArr) {
            concBinStrBuilder.append(str);
        }  

        return concBinStrBuilder.toString();
    }

    /**
     * Converts bytes to a signed long value.
     * 
     * @param data the input byte array in hexadecimal format
     * @param offt the offset in the array
     * @param len  the length of bytes to convert
     * @return the signed long value represented by the specified bytes
     */
    public long getSigned(String[] data, int offt, int len) {
        String concBinStr = getBinaryStr(data, offt, len);
        String subStr = concBinStr.substring(1);
        long res = Long.parseLong(subStr, 2);

        res *= concBinStr.charAt(0) == '1' ? -1 : 1;

        return res;
    }

    /**
     * Converts bytes to an unsigned BigInteger value.
     * 
     * @param data the input byte array in hexadecimal format
     * @param offt the offset in the array
     * @param len  the length of bytes to convert
     * @return the unsigned BigInteger value represented by the specified bytes
     */
    public BigInteger getUnsigned(String[] data, int offt, int len) {
        String concBinStr = getBinaryStr(data, offt, len);

        BigInteger res = new BigInteger(concBinStr, 2);

        return res;
    }

    /**
     * Converts bytes to a float value.
     * 
     * @param data the input byte array in hexadecimal format
     * @param offt the offset in the array
     * @param len  the length of bytes to convert
     * @return the float value represented by the specified bytes
     */
    public float getFloat(String[] data, int offt, int len) {
        String concBinStr = getBinaryStr(data, offt, len);

        String subStr = concBinStr.substring(1);
        int intValue = Integer.parseInt(subStr, 2);

        float res = Float.intBitsToFloat(intValue);
        res *= concBinStr.charAt(0) == '1' ? -1 : 1;

        return res;
    }

    /**
     * Converts bytes to a double value.
     * 
     * @param data the input byte array in hexadecimal format
     * @param offt the offset in the array
     * @param len  the length of bytes to convert
     * @return the double value represented by the specified bytes
     */
    public double getDouble(String[] data, int offt, int len) {
        String concBinStr = getBinaryStr(data, offt, len);
        String subStr = concBinStr.substring(1);
        long longValue = Long.parseLong(subStr, 2);
        double res = Double.longBitsToDouble(longValue);

        res *= concBinStr.charAt(0) == '1' ? -1 : 1;

        return res;
    }

    /**
     * Searches for blocks with a specific integer value.
     * 
     * @param data the input byte array in hexadecimal format
     * @param len  the length of the blocks to search
     * @param num  the target integer value as BigInteger
     * @return an array of offsets where the target value is found
     */
    public int[] getByteOffsetsValue(String[] data, int len, BigInteger num) {
        int cnt = 0;
        String cntStr = "";
        String targetStr = num.toString(2);
        int tmp = len * 8 - targetStr.length();
        for (int i = 0; i < tmp; i++) {
            targetStr = "0" + targetStr;
        }
        for (int i = 0; i < data.length - 7; i++) {
            cntStr = getBinaryStr(data, i, len);
            if (cntStr.equals(targetStr)) {
                cnt++;
            }
        }

        int[] res = new int[cnt];
        int tCnt = 0;
        for (int j = 0; j < data.length - 7; j++) {
            cntStr = getBinaryStr(data, j, len);
            if (cntStr.equals(targetStr)) {
                res[tCnt] = j;
                tCnt++;
            }
        }

        return res;
    }

    /**
     * Checks if a byte is valid according to a mask.
     * 
     * @param dataEl the byte to check
     * @param mask   the mask to use for validation
     * @return true if the byte is valid, false otherwise
     */
    public boolean isValidForMask(String dataEl, String mask) {
        int cnt = 0;

        for (int i = 0; i < mask.length(); i++) {
            if ((dataEl.charAt(cnt) == mask.charAt(cnt)) || (mask.charAt(cnt) == '*')) {
                cnt++;
            }
        }
        return mask.length() == cnt;
    }

    /**
     * Adjusts the mask to fit the size of the block.
     * 
     * @param oldMask the original mask
     * @param currLen the current length of the block
     * @return the adjusted mask
     */
    public String updMask(String oldMask, int currLen) {
        String res = oldMask;
        int oldLen = oldMask.length();
        if (oldLen < currLen) {
            int disLen = currLen - oldLen;
            for (int i = 0; i < disLen; i++) {
                res += "*";
            }
        } else if (oldLen > currLen) {
            res = oldMask.substring(0, currLen);
        }
        return res;
    }

    /**
     * Gets the offsets of valid blocks according to a mask.
     * 
     * @param data the input byte array in hexadecimal format
     * @param len  the length of the blocks to check
     * @param mask the mask to use for validation
     * @return an array of offsets where the valid blocks are found
     */
    public int[] getBytesOffsetMask(String[] data, int len, String mask) {
        int numEl = 0;
        int maskByteLen = 8;
        String newMask = updMask(mask, len * maskByteLen);
        for (int i = 0; i < data.length - maskByteLen + 1; i++) {
            String tmpStr = getBinaryStr(data, i, len);
            if (isValidForMask(tmpStr, newMask))
                numEl++;
        }

        int[] res = new int[numEl];
        int k = 0;

        for (int i = 0; i < data.length - maskByteLen + 1; i++) {
            String tmpStr = getBinaryStr(data, i, len);
            if (isValidForMask(tmpStr, newMask)) {
                res[k] = i;
                k++;
            }
        }
        return res;
    }
}

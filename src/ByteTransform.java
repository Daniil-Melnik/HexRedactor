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
     * @param data   the input byte array in hexadecimal format
     * @param offset the offset in the array
     * @param len    the length of bytes to convert
     * @return a binary string representation of the specified bytes
     */
    public String getBinaryString(String[] data, int offset, int len) {
        int[] inputIntArr = new int[len];
        for (int i = 0; i < len; i++) {
            inputIntArr[i] = Integer.parseInt(data[offset + i], 16);
        }

        String[] binaryString = new String[len];
        for (int j = 0; j < len; j++) {
            binaryString[j] = Integer.toBinaryString(inputIntArr[j]);
            int p = binaryString[j].length();
            int numberOfZeros = 8 - p;
            if (numberOfZeros > 0) {
                StringBuilder zeroBuilder = new StringBuilder();
                for (int i = 0; i < numberOfZeros; i++) {
                    zeroBuilder.append('0');
                }
                String zeros = zeroBuilder.toString();
                binaryString[j] = zeros + binaryString[j];
            }
        }

        StringBuilder concatBinStrBuilder = new StringBuilder();
        for (String str : binaryString) {
            concatBinStrBuilder.append(str);
        }

        return concatBinStrBuilder.toString();
    }

    /**
     * Converts bytes to a signed long value.
     * 
     * @param data   the input byte array in hexadecimal format
     * @param offset the offset in the array
     * @param len    the length of bytes to convert
     * @return the signed long value represented by the specified bytes
     */
    public long getSignedInt(String[] data, int offset, int len) {
        String concatBinaryString = getBinaryString(data, offset, len);
        String subStr = concatBinaryString.substring(1);
        long res = Long.parseLong(subStr, 2);

        res *= concatBinaryString.charAt(0) == '1' ? -1 : 1;

        return res;
    }

    /**
     * Converts bytes to an unsigned BigInteger value.
     * 
     * @param data   the input byte array in hexadecimal format
     * @param offset the offset in the array
     * @param len    the length of bytes to convert
     * @return the unsigned BigInteger value represented by the specified bytes
     */
    public BigInteger getUnsignedInt(String[] data, int offset, int len) {
        String concBinStr = getBinaryString(data, offset, len);

        BigInteger res = new BigInteger(concBinStr, 2);

        return res;
    }

    /**
     * Converts bytes to a float value.
     * 
     * @param data   the input byte array in hexadecimal format
     * @param offset the offset in the array
     * @param len    the length of bytes to convert
     * @return the float value represented by the specified bytes
     */
    public float getFloatNumber(String[] data, int offset, int len) {
        float res;
        try {
            String concBinStr = getBinaryString(data, offset, len);

            String subStr = concBinStr.substring(1);
            int intValue = Integer.parseInt(subStr, 2);

            res = Float.intBitsToFloat(intValue);
            res *= concBinStr.charAt(0) == '1' ? -1 : 1;
        } finally {
            res = -1;
        }
        return res;
    }

    /**
     * Converts bytes to a double value.
     * 
     * @param data   the input byte array in hexadecimal format
     * @param offset the offset in the array
     * @param len    the length of bytes to convert
     * @return the double value represented by the specified bytes
     */
    public double getDoubleNumber(String[] data, int offset, int len) {
        String concatBinaryString = getBinaryString(data, offset, len);
        String subStr = concatBinaryString.substring(1);
        long longValue = Long.parseLong(subStr, 2);
        double res = Double.longBitsToDouble(longValue);

        res *= concatBinaryString.charAt(0) == '1' ? -1 : 1;

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
    public int[] getByteValueByOffsets(String[] data, int len, BigInteger num) {
        int cnt = 0;
        String cntStr;
        String targetStr = num.toString(2);
        int tmp = len * 8 - targetStr.length();
        for (int i = 0; i < tmp; i++) {
            targetStr = "0" + targetStr;
        }
        for (int i = 0; i < data.length - 7; i++) {
            cntStr = getBinaryString(data, i, len);
            if (cntStr.equals(targetStr)) {
                cnt++;
            }
        }

        int[] res = new int[cnt];
        int tCnt = 0;
        for (int j = 0; j < data.length - 7; j++) {
            cntStr = getBinaryString(data, j, len);
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
     * @param dataElelement the byte to check
     * @param mask          the mask to use for validation
     * @return true if the byte is valid, false otherwise
     */
    public boolean isValidMask(String dataElelement, String mask) {
        int cnt = 0;

        for (int i = 0; i < mask.length(); i++) {
            if ((dataElelement.charAt(cnt) == mask.charAt(cnt)) || (mask.charAt(cnt) == '*')) {
                cnt++;
            }
        }
        return mask.length() == cnt;
    }

    /**
     * Adjusts the mask to fit the size of the block.
     * 
     * @param oldMask    the original mask
     * @param currentLen the current length of the block
     * @return the adjusted mask
     */
    public String updMask(String oldMask, int currentLen) {
        String res = oldMask;
        int oldLen = oldMask.length();
        if (oldLen < currentLen) {
            int deltaLen = currentLen - oldLen;
            for (int i = 0; i < deltaLen; i++) {
                res += "*";
            }
        } else if (oldLen > currentLen) {
            res = oldMask.substring(0, currentLen);
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
    public int[] getBytesOffsetByMask(String[] data, int len, String mask) {
        int numEl = 0;
        int maskByteLen = 8;
        String newMask = updMask(mask, len * maskByteLen);
        for (int i = 0; i < data.length - maskByteLen + 1; i++) {
            String tmpStr = getBinaryString(data, i, len);
            if (isValidMask(tmpStr, newMask))
                numEl++;
        }

        int[] res = new int[numEl];
        int k = 0;

        for (int i = 0; i < data.length - maskByteLen + 1; i++) {
            String tmpStr = getBinaryString(data, i, len);
            if (isValidMask(tmpStr, newMask)) {
                res[k] = i;
                k++;
            }
        }
        return res;
    }
}

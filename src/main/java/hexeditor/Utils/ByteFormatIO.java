package main.java.hexeditor.Utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static java.lang.Math.toIntExact;

/**
 * Class for working with file byte data.
 * 
 * @autor DMelnik
 * @version 1.0
 */
public class ByteFormatIO {
    private final String fileName;

    /**
     * Constructor - creates a new object with a specified file name.
     * 
     * @param fileName the name of the file the object will work with
     */

    public ByteFormatIO(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets an array of hexadecimal strings by offset and length.
     * 
     * @param offset the offset in bytes
     * @param len    the length in bytes
     * @return an array of strings in hexadecimal format
     */

    public String[] getHexBytesByOffset(int offset, int len) {
        String[] hexBytesOffset = null;
        try (RandomAccessFile file = new RandomAccessFile(this.fileName, "r")) {
            byte[] bytes = new byte[len];
            file.seek(offset);
            file.read(bytes);
            hexBytesOffset = new String[len];
            for (int i = 0; i < len; i++) {
                hexBytesOffset[i] = String.format("%02X", bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hexBytesOffset;
    }

    /**
     * Gets the length of the file in bytes.
     * 
     * @param fileName the name of the file
     * @return the length of the file in bytes
     * @throws IOException if the file cannot be opened
     */

    public long getFileLength(String fileName) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            return file.length();
        }
    }

    /**
     * Converts an array of hexadecimal format strings to a byte array.
     * 
     * @param data an array of strings in hexadecimal format
     * @return a byte array
     */
    public byte[] transformToBytesArr(String[] data) {
        int endIndex = data.length - 1;
        int nStars = 0;
        for (int i = endIndex; i >= 0; i--) {
            if (data[i].equals("**")) {
                nStars++;
            }
        }
        int actualLen = data.length - nStars;
        byte[] res = new byte[actualLen];
        int cnt = 0;
        for (int i = 0; i < data.length; i++) {
            if (!data[i].equals("**")) {
                res[cnt] = (byte) (Integer.parseInt(data[i], 16));
                cnt++;
            }
        }
        return res;
    }

    /**
     * Splits the input hexadecimal format string into an array of strings.
     * 
     * @param inputHexString a string in hexadecimal format separated by semicolons
     * @return an array of strings
     */
    public String[] splitHexBytes(String inputHexString) {
        return inputHexString.split(";");
    }

    /**
     * Writes data to a file at a given offset and length.
     * 
     * @param offset   the offset in bytes
     * @param data     an array of strings in hexadecimal format
     * @param deltaLen the length of the data
     * @param newName  the new file name
     */
    public void printData(int offset, String[] data, int deltaLen, String newName) {
        RandomAccessFile randomAccessFile = null;
        try {
            String tmpFileName = "tmpFile.txt";
            File tmpFile = new File(tmpFileName);
            int index = 0;

            int buffer = 8;
            byte[] fullPackDataByte = new byte[buffer];
            byte[] preEmptyDataByte = new byte[offset % buffer];
            String[] fullPackDataStr = new String[buffer];
            String[] preEmptyDataStr = null;

            randomAccessFile = new RandomAccessFile(tmpFile, "rw");

            int numberOfFullPacks = offset / buffer;

            for (int i = 0; i < numberOfFullPacks; i++) {
                fullPackDataStr = getHexBytesByOffset(index, buffer);

                fullPackDataByte = transformToBytesArr(fullPackDataStr);
                randomAccessFile.write(fullPackDataByte);

                index += buffer;
            }

            preEmptyDataStr = getHexBytesByOffset(index, offset % buffer);
            preEmptyDataByte = transformToBytesArr(preEmptyDataStr);
            randomAccessFile.write(preEmptyDataByte);

            index = offset;

            randomAccessFile.write(transformToBytesArr(data));

            index += data.length + deltaLen;

            long pInd = (getFileLength(this.fileName) - index) / buffer;

            for (int k = 0; k < pInd; k++) {
                fullPackDataStr = getHexBytesByOffset(index, buffer);
                fullPackDataByte = transformToBytesArr(fullPackDataStr);
                randomAccessFile.write(fullPackDataByte);
                index += buffer;
            }
            if (index < getFileLength(this.fileName)) {
                int nPreEmptyBytes = toIntExact((getFileLength(this.fileName) - index) % buffer);
                preEmptyDataStr = getHexBytesByOffset(index, nPreEmptyBytes);
                preEmptyDataByte = transformToBytesArr(preEmptyDataStr);
                randomAccessFile.write(preEmptyDataByte);
                index += nPreEmptyBytes;
            }

            randomAccessFile.close();
            FileManager fM = new FileManager();
            boolean isCopy = newName.equals(this.fileName) ? false : true;

            fM.setFile(tmpFileName, newName, isCopy);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

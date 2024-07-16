import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import static java.lang.Math.toIntExact;

<<<<<<< HEAD
/** 
 * Class for working with file byte data.
 *  
 * @autor DMelnik 
 * @version 1.0
 */ 
public class ByteIO {
    private final String fName;


    /** 
=======
/**
 * Class for working with file byte data.
 * 
 * @autor DMelnik
 * @version 2.1
 */
public class ByteIO {
    private final String fName;

    /**
>>>>>>> 6e5d30da045d75c229737ad2be2628380ba95a0f
     * Constructor - creates a new object with a specified file name.
     * 
     * @param fName the name of the file the object will work with
     */

    public ByteIO(String fName) {
        this.fName = fName;
    }

<<<<<<< HEAD
    /** 
     * Gets an array of hexadecimal strings by offset and length.
     * 
     * @param offt the offset in bytes
     * @param len the length in bytes
=======
    /**
     * Gets an array of hexadecimal strings by offset and length.
     * 
     * @param offt the offset in bytes
     * @param len  the length in bytes
>>>>>>> 6e5d30da045d75c229737ad2be2628380ba95a0f
     * @return an array of strings in hexadecimal format
     */

    public String[] getHexBytesOfft(int offt, int len) {
        String[] hexBytesOfft = null;
        try (RandomAccessFile file = new RandomAccessFile(this.fName, "r")) {
            byte[] bytes = new byte[len];
            file.seek(offt);
            file.read(bytes);
            hexBytesOfft = new String[len];
            for (int i = 0; i < len; i++) {
                hexBytesOfft[i] = String.format("%02X", bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hexBytesOfft;
    }

<<<<<<< HEAD
    /** 
     * Gets the length of the file in bytes.
     * 
     * @param fName the name of the file
     * @return the length of the file in bytes
     * @throws IOException if the file cannot be opened
     */

=======
>>>>>>> 6e5d30da045d75c229737ad2be2628380ba95a0f
    public long getFileLength(String fName) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(fName, "r")) {
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
     * @param inHexStr a string in hexadecimal format separated by semicolons
     * @return an array of strings
     */
    public String[] splitHexBytes(String inHexStr) {
        return inHexStr.split(";");
    }

    /** 
     * Writes data to a file at a given offset and length.
     * 
     * @param offt the offset in bytes
     * @param data an array of strings in hexadecimal format
     * @param dLen the length of the data
     * @param newName the new file name
     */
    public void printData(int offt, String[] data, int dLen, String newName) {
        RandomAccessFile randomAccessFile = null;
        try {
            String tmpFileName = "tmpFile.txt";
            File tmpFile = new File(tmpFileName);
            int index = 0;

            int buf = 8;
            byte[] fullPackDataByte = new byte[buf];
            byte[] preEmptyDataByte = new byte[offt % buf];
            String[] fullPackDataStr = new String[buf];
            String[] preEmptyDataStr = null;

            randomAccessFile = new RandomAccessFile(tmpFile, "rw");

            int nFullPacks = offt / buf;

            for (int i = 0; i < nFullPacks; i++) {
                fullPackDataStr = getHexBytesOfft(index, buf);

                fullPackDataByte = transformToBytesArr(fullPackDataStr);
                randomAccessFile.write(fullPackDataByte);

                index += buf;
            }

            preEmptyDataStr = getHexBytesOfft(index, offt % buf);
            preEmptyDataByte = transformToBytesArr(preEmptyDataStr);
            randomAccessFile.write(preEmptyDataByte);

            index = offt;

            randomAccessFile.write(transformToBytesArr(data));

            index += data.length + dLen;

            long pInd = (getFileLength(this.fName) - index) / buf;

            for (int k = 0; k < pInd; k++) {
                fullPackDataStr = getHexBytesOfft(index, buf);
                fullPackDataByte = transformToBytesArr(fullPackDataStr);
                randomAccessFile.write(fullPackDataByte);
                index += buf;
            }
            if (index < getFileLength(this.fName)) {
                int nPreEmptyBytes = toIntExact((getFileLength(this.fName) - index) % buf);
                preEmptyDataStr = getHexBytesOfft(index, nPreEmptyBytes);
                preEmptyDataByte = transformToBytesArr(preEmptyDataStr);
                randomAccessFile.write(preEmptyDataByte);
                index += nPreEmptyBytes;
            }

            randomAccessFile.close();
            FileManager fM = new FileManager();
            boolean isCopy = newName.equals(this.fName) ? false : true;

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

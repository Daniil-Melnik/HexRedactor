package hexeditor.Utils;

import javax.swing.JLabel;
import javax.swing.JTable;

/**
 * Utility class for various byte array manipulations and conversions.
 *
 * @author DMelnik
 * @version 1.0
 */

public class UtilByte {
    /**
     * Converts a flat array of strings into a 2D array without labels.
     *
     * @param rowLen    the number of rows
     * @param columnLen the number of columns
     * @param data      the flat array of strings
     * @return a 2D array of objects
     */
    public Object[][] toSimpleArr(int rowLen, int columnLen, String[] data) {
        Object[][] arr = new Object[columnLen][rowLen];
        for (int i = 0; i < data.length; i++) {
            arr[i / rowLen][i % rowLen] = data[i];
        }
        return arr;
    }

    /**
     * Converts a flat array of strings into a 2D array with labels.
     *
     * @param rowLen    the number of rows
     * @param columnLen the number of columns
     * @param data      the flat array of strings
     * @param offt      the offset for labels
     * @return a 2D array of objects with labels
     * @see JLabel
     */
    public Object[][] toLabeledArr(int rowLen, int columnLen, String[] data, int offt) {
        Object[][] resArr = new Object[columnLen][rowLen + 1];
        for (int i = 0; i < data.length; i++) {
            resArr[i / rowLen][(i % rowLen) + 1] = data[i];
        }
        for (int i = 0; i < columnLen; i++) {
            resArr[i][0] = new JLabel(Integer.toString(offt + (i * rowLen)));
        }
        return resArr;
    }

    /**
     * Sets a part of the array to "00".
     *
     * @param data          the original array
     * @param len           the length of the segment to be zeroed
     * @param startPosition the starting position of the segment
     * @return the modified array
     */
    public String[] removeFromArrZero(String[] data, int len, int startPosition) {
        String[] res = data;
        for (int i = startPosition; i < startPosition + len; i++) {
            res[i] = "00";
        }
        return res;
    }

    /**
     * Removes a part of the array.
     *
     * @param data          the original array
     * @param len           the length of the segment to be removed
     * @param startPosition the starting position of the segment
     * @return the modified array
     */
    public String[] removeFromArr(String[] data, int len, int startPosition) {
        String[] res = null;

        int newLen = data.length - len;
        int currArrPosition = 0;
        int newLeftPosition = startPosition + len;
        res = new String[newLen];

        for (int i = 0; i < startPosition; i++) {
            res[currArrPosition] = data[i];
            currArrPosition++;
        }

        for (int i = newLeftPosition; i < data.length; i++) {
            res[currArrPosition] = data[i];
            currArrPosition++;
        }

        return res;
    }

    /**
     * Concatenates two arrays.
     *
     * @param dataLeft  the left array
     * @param dataRight the right array
     * @return the concatenated array
     */
    public String[] concatArrs(String[] dataLeft, String[] dataRight) {
        String[] res = null;

        int newLen = dataLeft.length + dataRight.length;
        int currPosition = 0;

        res = new String[newLen];

        for (int i = 0; i < dataLeft.length; i++) {
            res[currPosition] = dataLeft[i];
            currPosition++;
        }

        for (int i = 0; i < dataRight.length; i++) {
            res[currPosition] = dataRight[i];
            currPosition++;
        }
        return res;
    }

    /**
     * Inserts zeros into the array.
     *
     * @param data the original array
     * @param offt the offset where zeros should be inserted
     * @param len  the number of zeros to insert
     * @return the modified array
     */
    public String[] fillInZeros(String[] data, int offt, int len) {
        int newDataLen = data.length + len;
        String[] res = new String[newDataLen];
        int currIndex = 0;
        for (int i = 0; i <= offt; i++) {
            res[currIndex] = data[i];
            currIndex++;
        }
        for (int j = 0; j < len; j++) {
            res[currIndex] = "00";
            currIndex++;
        }
        for (int k = offt + 1; k < data.length; k++) {
            res[currIndex] = data[k];
            currIndex++;
        }
        return res;
    }

    /**
     * Adds stars to the end of the array.
     *
     * @param data the original array
     * @param len  the number of stars to add
     * @return the modified array
     */

    public String[] fillInStars(String[] data, int len) {
        int oldLen = data.length;
        int newLen = oldLen + len;
        String[] res = new String[newLen];
        for (int i = 0; i < oldLen; i++) {
            res[i] = data[i];
        }
        for (int j = oldLen; j < newLen; j++) {
            res[j] = "**";
        }
        return res;
    }

    /**
     * Counts the number of stars in the array.
     *
     * @param data the array to count stars in
     * @return the number of stars
     */

    public int getNumOfStars(String[] data) {
        int res = 0;
        for (int i = 0; i < data.length; i++)
            res += data[i].equals("**") ? 1 : 0;
        return res;
    }

    /**
     * Removes stars from the array.
     *
     * @param oldData the original array
     * @return the modified array without stars
     */
    public String[] clearStars(String[] oldData) {
        int starLen = getNumOfStars(oldData);
        String[] res = new String[oldData.length - starLen];
        int cnt = 0;

        for (int j = 0; j < oldData.length; j++) {
            if (!oldData[j].equals("**")) {
                res[cnt] = oldData[j];
                cnt++;
            }
        }
        return res;
    }

    /**
     * Concatenates two arrays with a seven-byte offset.
     *
     * @param leftData  the left array
     * @param rightData the right array
     * @return the concatenated array
     */

    public String[] fillInSevenBytes(String[] leftData, String[] rightData) {
        int oldLen = leftData.length;
        int newLen = oldLen + 7;
        String[] res = new String[newLen];

        String[] unZeroData = clearStars(leftData);

        res = concatArrs(unZeroData, rightData);
        return res;
    }

    /**
     * Inserts data into the array with a shift.
     *
     * @param oldData the original array
     * @param addData the data to insert
     * @param offt    the offset where data should be inserted
     * @return the modified array
     */
    public String[] addDataShift(String[] oldData, String[] addData, int offt) {
        int newDataLen = oldData.length + addData.length;
        String[] res = new String[newDataLen];
        int currIndex = 0;
        for (int i = 0; i <= offt; i++) {
            res[currIndex] = oldData[i];
            currIndex++;
        }
        for (int j = 0; j < addData.length; j++) {
            res[currIndex] = addData[j];
            currIndex++;
        }
        for (int k = offt + 1; k < oldData.length; k++) {
            res[currIndex] = oldData[k];
            currIndex++;
        }
        return res;
    }

    /**
     * Inserts data into the array with substitution.
     *
     * @param oldData the original array
     * @param addData the data to insert
     * @param offt    the offset where data should be inserted
     * @return the modified array
     */
    public String[] addDataSubst(String[] oldData, String[] addData, int offt) {
        String[] res = oldData;
        int rightBorder = offt + addData.length;
        int newQ = 0;
        for (int i = offt + 1; i < rightBorder + 1; i++) {
            res[i] = addData[newQ];
            newQ++;
        }
        return res;
    }

    /**
     * Get values of highlight cells from table.
     *
     * @param table          table sheet with hexstring interpitations of bytes
     * @param highlightCells 2D array of highlight cells coordinates
     * @return array of hexstring values
     */
    public String[] getValuesOfHighlt(JTable table, int[][] highlightCells) {
        int arrLen = highlightCells.length;
        String[] res = new String[arrLen];
        for (int i = 0; i < arrLen; i++) {
            int[] cellArr = highlightCells[i];
            res[i] = table.getValueAt(cellArr[0], cellArr[1]).toString();
        }
        return res;
    }
}
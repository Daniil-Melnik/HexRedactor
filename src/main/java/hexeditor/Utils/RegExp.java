package hexeditor.Utils;

import java.util.ArrayList;

/**
 * <p>
 * Class for validating through regular expressions:
 * </p>
 * <list>
 * <li>byte arrays</li>
 * <li>data types</li>
 * </list>
 * 
 * @see ArrayList
 * @version 1.0
 * @author DMelnik
 */

public class RegExp {

    /**
     * Сhecking the validity of one byte
     * 
     * @param str byte value in string interpretation to be checked
     * @return boolean value of value correctness
     */

    public boolean isValid(String str) {
        return (str.matches("^[0-9A-Fa-f]{2}$"));
    }

    /**
     * Сhecking the mask from input field (SearchPanel.java)
     * A mask is considered to be a string consisting of characters 1, 0, * with a
     * length of at least 1 and no more than 64
     * 
     * @param str string value from mask's input field, to be checked
     * @return boolean value of value correctness
     */
    public boolean isMask(String str) {
        return (str.matches("^[10*]{1,64}$"));
    }

    /**
     * Сhecking input value of unsigned int
     * 
     * @param str string interpretation of value to be checked
     * @return boolean value of value correctness
     */
    public boolean isValue(String str) {
        return (str.matches("^[0-9]{1,10}$"));
    }

    /**
     * Byte array validation for get positions of invalid bytes
     * 
     * @param arr  array of string interpretations of byte values
     * @param offt start offset of array in all data
     * 
     * @return array of invalid byte's offsets
     */
    public ArrayList<Integer> isValidArr(String[] arr, int offt) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (!isValid(arr[i])) {
                res.add(offt + i);
            }
        }
        return res;
    }

    /**
     * Byte array validation to get validity of the array as a whole
     * 
     * @param arr array of string interpretations of byte values
     * 
     * @return boolean value of array validity
     */
    public boolean isValidArrBool(String[] arr) {
        boolean res = true;
        int i = 0;
        while (res && (i < arr.length)) {
            res = isValid(arr[i]);
            i++;
        }
        return res;
    }

    /**
     * Gives the coordinates of table cells that have errors
     * 
     * @param rowLen    length of table row
     * @param offt      offset in whole data
     * @param errorOfft list of error bytes offsets
     * 
     * @return array of cell coordinates in row-column format
     */
    public int[][] getErrorCells(int rowLen, int offt, ArrayList<Integer> errorOfft) {
        int[][] res = new int[errorOfft.size()][2];
        int k;
        for (Integer i : errorOfft) {
            k = errorOfft.indexOf(i);
            res[k][0] = (i - offt) / rowLen;
            res[k][1] = (i - offt) % rowLen + 1;
        }
        return res;
    }
}

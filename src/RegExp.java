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

    ////////////////////////////////////////////////////////
    //////////////// Валидация одного байта ////////////////
    ////////////////////////////////////////////////////////
    /**
     * Сhecking the validity of one byte
     * 
     * @param str byte value in string interpretation to be checked
     */

    public boolean isValid(String str) {
        return (str.matches("^[0-9A-Fa-f]{2}$"));
    }

    ///////////////////////////////////////////////////////
    ////////////////// Проверка на маску //////////////////
    ///////////////////////////////////////////////////////
    public boolean isMask(String str) {
        return (str.matches("^[10*]{1,64}$"));
    }

    ///////////////////////////////////////////////////////
    /////// Проверка на беззнаковое целое значение ////////
    ///////////////////////////////////////////////////////
    public boolean isValue(String str) {
        return (str.matches("^[0-9]{1,10}$"));
    }

    ////////////////////////////////////////////////////////
    /////////////// Валидация массива байтов ///////////////
    ////////////////////////////////////////////////////////
    public ArrayList<Integer> isValidArr(String[] arr, int offt) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (!isValid(arr[i])) {
                res.add(offt + i);
            }
        }
        return res;
    }

    /////////////////////////////////////////////////////////
    /////////// Условная валидация массива байтов ///////////
    /////////////////////////////////////////////////////////
    public boolean isValidArrBool(String[] arr) {
        boolean res = true;
        int i = 0;
        while (res && (i < arr.length)) {
            res = isValid(arr[i]);
            i++;
        }
        return res;
    }

    ////////////////////////////////////////////////////////
    ////////////// Получение координат ошибок //////////////
    ////////////////////////////////////////////////////////
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

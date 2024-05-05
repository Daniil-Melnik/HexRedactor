import java.util.ArrayList;

public class RegExp {

    ////////////////////////////////////////////////////////
    //////////////// Валидация одного байта ////////////////
    ////////////////////////////////////////////////////////
    public boolean isValid(String str){
        return (str.matches("^[0-9A-Fa-f]{2}$"));
    }

    ///////////////////////////////////////////////////////
    ////////////////// Проверка на маску //////////////////
    ///////////////////////////////////////////////////////
    public boolean isMask(String str){
        return (str.matches("^[10*]{8,64}$"));
    }

    ////////////////////////////////////////////////////////
    /////////////// Валидация массива байтов ///////////////
    ////////////////////////////////////////////////////////
    public ArrayList<Integer> isValidArr(String [] arr, int offt){
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++){
            if (!isValid(arr[i])) {
                res.add(offt + i);
            }
        }
        return  res;
    }

    ////////////////////////////////////////////////////////
    ////////////// Получение координат ошибок //////////////
    ////////////////////////////////////////////////////////
    public int [][] getErrorCells(int rowLen, int offt, ArrayList<Integer> errorOfft){
        int [][] res = new int[errorOfft.size()][2];
        int k;
        for (Integer i : errorOfft){
            k = errorOfft.indexOf(i);
            res[k][0] = (i - offt) / rowLen;
            res[k][1] = (i - offt) % rowLen + 1;
        }
        return res;
    }
}

import java.util.ArrayList;

public class RegExp {

    public boolean isValid(String str){
        return (str.matches("^[0-9A-Fa-f]{2}$"));
    }

    public ArrayList<Integer> isValidArr(String [] arr, int offt){
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++){
            if (!isValid(arr[i])){
                res.add(offt + i);
            }
            //System.out.println(isValid(arr[i]));
        }
        res.add(-1);
        return  res;
    }
}

public class ChangeHandler {
    private int type;
    /*
     * 0 - изменение одной ячейки
     * 1 - удаление с обнулением
     * 2 - удаление со сдвигом
     * 3 - вставка с заменой
     * 4 - вставка со сдвигом
     * 5 - вырезка с обнулением
     * 6 - вырезка со сдвигом
     */
    private String [] changedCells;
    private int offt;
    private int len;

    public ChangeHandler(int type, int offt, int len, String [] data){
        this.type = type;
        this.offt = offt;
        this.len = len;
        this.changedCells = data;
    }

    public int getOfft(){
        return this.offt;
    }

    public int getLen(){
        return this.len;
    }

    public int getType(){
        return this.type;
    }
    public String [] getData(){
        return this.changedCells;
    }
}

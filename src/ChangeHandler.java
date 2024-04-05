public class ChangeHandler {
    private int type;
    /*
     * 0 - change one cell
     * 1 - deleted cells
     * 2 - added cells
     */
    private String [] changedCells;
    private int offt;
    private int len; //for one cell - 1

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

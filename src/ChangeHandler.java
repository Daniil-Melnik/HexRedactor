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

    public int getOfft(){
        return this.offt;
    }

    public int getLen(){
        return this.len;
    }

    public int getType(){
        return this.type;
    }
}

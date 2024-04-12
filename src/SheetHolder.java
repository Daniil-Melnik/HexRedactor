public class SheetHolder {
    private String [] data;
    private int rowLen;
    private int columnLen;
    private int offt;

    public void setRowLen(int width){
        this.rowLen = width;
    }

    public void setColumnLen(int height){
        this.columnLen = height;
    }

    public void setOfft(int offt){
        this.offt = offt;
    }

    public void setData(String [] data){
        this.data = data;
    }

    public String [] getData (){
        return this.data;
    }

    public int getRowLen(){
        return this.rowLen;
    }

    public int getHeight(){
        return this.columnLen;
    }

    public void makeHandle(ChangeHandler chH){
        int index = chH.getOfft() % (this.rowLen * this.columnLen);
        if (chH.getType() == 0){
            System.out.println("GET HANDLE ZERO");
            this.data[index] = chH.getData()[0]; // дописать про изменение одного байта в массиве
        }
    }
}

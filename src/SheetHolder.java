public class SheetHolder {
    private String [] data;
    private String [] reserveData;
    private int rowLen;
    private int columnLen;
    private int offt;

    public void setRowLen(int width){
        this.rowLen = width;
    }

    public void setOfft(int offt){
        this.offt = offt;
    }

    public void setColumnLen(int height){
        this.columnLen = height;
    }

    public void setData(String [] data){
        this.data = data;
    }

    public void setReserveData(String [] data){
        this.reserveData = data;
    }

    public void setAllData(String [] data){
        this.reserveData = data;
        this.data = data;
    }

    public String [] getData (){
        return this.data;
    }

    public String [] getReserveData (){
        return this.reserveData;
    }

    public int getRowLen(){
        return this.rowLen;
    }

    public int getHeight(){
        return this.columnLen;
    }

    public SheetHolder(){
        this.offt = 0;
    }



    public void makeHandle(ChangeHandler chH){
        int index = chH.getOfft() % (this.rowLen * this.columnLen);
        switch (chH.getType()) {
            case 0:
                System.out.println("GET HANDLE ZERO");
                this.data[index] = chH.getData()[0]; // дописать про изменение одного байта в массиве
                break;
            
            case 1:
                utilByte uB = new utilByte();
                int len = chH.getLen();
                int currOfft = chH.getOfft() - this.offt;
                uB.removeFromArrZero(this.data, len, currOfft);
                break;
            default:
                break;
        } 
    }
}

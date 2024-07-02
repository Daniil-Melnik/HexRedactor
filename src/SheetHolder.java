/*
* Класс-храниетель
* Назначение: хранит информацию об открытой странице данных
* */

public class SheetHolder {
    private String [] data;
    private String [] reserveData;
    private String fName;
    private int rowLen;
    private int columnLen;
    private int currentRow, currentColumn;
    private int dLen;
    private int offt;
    private int [][] hCells;
    private int [][] fCells;
    private int [][] erCells;

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

    public void setfName(String fName){
        this.fName = fName;
    }

    public void setHCells(int [][] hCells){
        this.hCells = hCells;
    }

    public int [][] getHCells(){
        return this.hCells;
    }

    public void setAllData(String [] data){
        this.reserveData = data;
        this.data = data;
    }

    public void setCurrentRow(int currentRow){
        this.currentRow = currentRow;
    }

    public void setCurrentColumn(int currentColumn){
        this.currentColumn = currentColumn;
    }

    public int getCurrentRow(){
        return this.currentRow;
    }

    public int getCurrentColumn(){
        return this.currentColumn;
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

    public int getColumnLen(){
        return this.columnLen;
    }

    public int getDLen(){
        return this.dLen;
    }

    public int [][] getFCells(){ return this.fCells; }

    public void setFCells(int [][] fCells) {
        this.fCells = fCells;
    }

    public int [][] getErCells(){ return this.erCells; }

    public void setErCells(int [][] erCells){
        this.erCells = erCells;
    }

    public SheetHolder(String fName){
        this.offt = 0;
        this.fName = fName;
        this.hCells = new int [0][0];
        this.fCells = new int[0][0];
        this.erCells = new int[0][0];
    }

    public  String getfName(){
        return  this.fName;
    }

    ///////////////////////////////////////////////////////////////
    ////////////////// Получение координат ячеек //////////////////
    ///////////////////////////////////////////////////////////////

    public int [][] getTableCellCoords(int [] offts){
        int offtLen = offts.length;
        int [][] res = new int [offtLen][2];
        for (int i = 0; i < offtLen; i++){
            int singleOfft = offts[i];
            res[i][0] = singleOfft / this.rowLen;
            res[i][1] = singleOfft % this.rowLen + 1;
        }
        return res;
    }

    public void setDLen(int dLen){
        this.dLen = dLen;
    }

    public void resetSheet(MouseHig mH){
        this.setErCells(new int [0][0]);
        this.setHCells(new int [0][0]);

        mH.setCond((byte) 0);
    }

    public void clearStarsOnSheet(){
        utilByte uB = new utilByte();
        String [] newData =  uB.clearStars(this.data);
        this.data = newData;
    }

    public void fillInStarsOnSheet(){
        utilByte uB = new utilByte();

        int emptyCellDataShift;
        if (this.data.length % this.rowLen != 0){
            emptyCellDataShift = this.rowLen - this.data.length % this.rowLen;
        }
        else emptyCellDataShift = 0;

        this.data = uB.fillInStars(this.data, emptyCellDataShift);
    }

    public boolean isEmptyVolume(int addLen){
        boolean res = true;
        int currLen = this.data.length;

        res = currLen + addLen < 1048577;
        return res;
    }

    public void makeHandle(ChangeHandler chH){
        int index = chH.getOfft() % (this.rowLen * this.columnLen);
        int len = 0;
        int currOfft = 0;
        utilByte uB = new utilByte();
        ByteIO bIO = new ByteIO(this.fName);
        System.out.println("cN = " + this.columnLen + "\n rN = " + this.rowLen);
        switch (chH.getType()) {
            case 0:
                // System.out.println("GET HANDLE ZERO");
                this.data[index] = chH.getData()[0]; // дописать про изменение одного байта в массиве
                break;
            
            case 1:
                len = chH.getLen();
                currOfft = chH.getOfft() - this.offt;
                uB.removeFromArrZero(this.data, len, currOfft);
                break;

            case 2:
                this.clearStarsOnSheet();
                len = chH.getLen();
                currOfft = chH.getOfft() - this.offt;
                int newOfft = this.offt + this.rowLen * this.columnLen + this.dLen;
                if (newOfft < bIO.getFileLength(this.fName)){
                    String [] leftData = uB.removeFromArr(this.data, len, currOfft);
                    String [] rightData = bIO.getHexBytesOfft(newOfft, len);
                    this.data = uB.concatArrs(leftData, rightData);
                    this.dLen += len;
                    this.fillInStarsOnSheet();
                }
                break;
            
            case 3:
                this.clearStarsOnSheet();
                int offt = chH.getOfft();
                String [] newData = chH.getData();
                this.data = uB.addDataSubst(this.data, newData, offt);
                this.fillInStarsOnSheet();
                break;

            case 4:
                this.clearStarsOnSheet();
                len = chH.getLen();
                offt = chH.getOfft();
                String [] newDataShift = chH.getData();
                // следующей строкой д. б. дозабивка, а не присвоение 22.04.2024
                String [] tempDataShift = uB.addDataShift(this.data, newDataShift, offt);
                this.data = tempDataShift;
                this.fillInStarsOnSheet();
                // this.columnLen = this.data.length / this.rowLen; // Возможно ошибка здесь 16.05.2024
                break;

            case 7:
                this.clearStarsOnSheet();
                len = chH.getLen();
                offt = chH.getOfft();
                // следующей строкой д. б. дозабивка, а не присвоение 22.04.2024
                this.data = uB.fillInZeros(this.data, offt, len);
                this.fillInStarsOnSheet();
            default:
                break;
        } 
    }
}

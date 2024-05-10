public class SheetHolder {
    private String [] data;
    private String [] reserveData;
    private String fName;
    private int rowLen;
    private int columnLen;
    private int currentRow, currentColumn;
    private int dLen;
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

    public void setfName(String fName){
        this.fName = fName;
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

    public SheetHolder(String fName){
        this.offt = 0;
        this.fName = fName;
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


    public void makeHandle(ChangeHandler chH){
        int index = chH.getOfft() % (this.rowLen * this.columnLen);
        int len = 0;
        int currOfft = 0;
        utilByte uB = new utilByte();
        ByteIO bIO = new ByteIO(this.fName);
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
                len = chH.getLen();
                currOfft = chH.getOfft() - this.offt;
                int newOfft = this.offt + this.rowLen * this.columnLen + this.dLen; // 09.05.2024
                //int newOfft = this.offt + len;
                String [] leftData = uB.removeFromArr(this.data, len, currOfft);
                String [] rightData = bIO.getHexBytesOfft(newOfft, len);
                this.data = uB.concatArrs(leftData, rightData);
                this.dLen += len;
                // System.out.println("НОВЫЙ СДВИГ = " + newOfft + '\n' + "СВИГ В ТАБЛИЦЕ = " + this.offt + "\nДЛИНА = " + len);
                break;
            
            case 3:
                int offt = chH.getOfft();
                String [] newData = chH.getData();
                this.data = uB.addDataSubst(this.data, newData, offt);           
                break;

            case 4:
                len = chH.getLen();
                offt = chH.getOfft();
                String [] newDataShift = chH.getData();
                // следующей строкой д. б. дозабивка, а не присвоение 22.04.2024
                String [] tempDataShift = uB.addDataShift(this.data, newDataShift, offt);
                int emptyCellDataShift;
                if (tempDataShift.length % this.rowLen != 0){
                    emptyCellDataShift = this.rowLen - tempDataShift.length % this.rowLen;
                }
                else emptyCellDataShift = 0;
                this.data = uB.fillInStars(tempDataShift, emptyCellDataShift);
                this.columnLen = this.data.length / this.rowLen;
                break;

            case 7:
                len = chH.getLen();
                offt = chH.getOfft();
                // следующей строкой д. б. дозабивка, а не присвоение 22.04.2024
                String [] tempData = uB.fillInZeros(this.data, offt, len);
                int emptyCellData;
                if (tempData.length % this.rowLen != 0){
                    emptyCellData = this.rowLen - tempData.length % this.rowLen;
                }
                else emptyCellData = 0;
                this.data = uB.fillInStars(tempData, emptyCellData);
                this.columnLen = this.data.length / this.rowLen;
            default:
                break;
        } 
    }
}

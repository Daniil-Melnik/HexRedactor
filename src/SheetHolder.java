public class SheetHolder {
    private String [] data;
    private String [] reserveData;
    private String fName;
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

    public void setfName(String fName){
        this.fName = fName;
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

    public SheetHolder(String fName){
        this.offt = 0;
        this.fName = fName;
    }



    public void makeHandle(ChangeHandler chH){
        int index = chH.getOfft() % (this.rowLen * this.columnLen);
        int len = 0;
        int currOfft = 0;
        utilByte uB = new utilByte();
        ByteIO bIO = new ByteIO(this.fName);
        switch (chH.getType()) {
            case 0:
                System.out.println("GET HANDLE ZERO");
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
                int newOfft = this.offt + this.rowLen * this.columnLen + len - 1; // 21.04.2024
                //int newOfft = this.offt + len;
                String [] leftData = uB.removeFromArr(this.data, len, currOfft);
                String [] rightData = bIO.getHexBytesOfft(newOfft, len);
                this.data = uB.concatArrs(leftData, rightData);
                this.offt += len; // 21.04.2024 разобраться с установкой в mainGUI
                System.out.println("НОВЫЙ СДВИГ = " + newOfft + '\n' + "СВИГ В ТАБЛИЦЕ = " + this.offt + "\nДЛИНА = " + len);
                break;

            case 7:
                len = chH.getLen();
                offt = chH.getOfft(); // необходимо дозабить массив звёздочками если потребуется
                // следующей строкой д. б. дозабивка, а не присвоение 22.04.2024
                this.data = uB.fillInZeros(this.data, offt, len); // наполнение нулями действующих данных
            default:
                break;
        } 
    }
}

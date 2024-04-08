public class SheetHolder {
    private String [] data;
    private int width;
    private int height;
    private int offt;

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
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

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void getHandle(ChangeHandler chH){
        int index = chH.getOfft() % (this.width * this.height);
        if (chH.getType() == 0){
            this.data[index] = chH.getData()[0]; // дописать про изменение одного байта в массиве
        }
    }
}

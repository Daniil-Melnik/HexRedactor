import java.util.ArrayList;

public class HandlerQueue {
    private ArrayList<ChangeHandler> queue;
    private String [] data;

    public HandlerQueue(){
        this.queue = new ArrayList<ChangeHandler>();
        this.data = null;
    }

    public void setData(ByteIO bIO, int offset, int len){
        this.data = bIO.setHexBytesOfft(offset, len);
    }

    public void addChange (ChangeHandler chH){
        this.queue.add(chH);
    }

    public  void showQueue(){
        for (ChangeHandler c : this.queue) {
            System.out.println("type = " + c.getType() + "\noffset = " + c.getOfft() + "\nlen = " + c.getLen());
        }
    }
}


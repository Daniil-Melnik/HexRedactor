import java.util.ArrayList;

public class HandlerQueue {
    private ArrayList<ChangeHandler> queue;
    private String [] data;

    public HandlerQueue(String [] data){
        this.queue = new ArrayList<ChangeHandler>();
        this.data = null;
    }

    public void setData(ByteIO bIO, int offset, int len){
        utilByte ub = new utilByte(bIO.setHexBytesOfft(offset, len));
        //this.data = ub. дописать после добавления простого чтения байт в 16ричном виде в UtilByte
    }
}


import java.util.ArrayList;

/**
 * HandlerQueue class
 * Purpose: Represents a queue of ChangeHandler objects, currently unused
 * 
 * @author DMelnik
 * @version 1.0
 * @see ChangeHandler
 */

public class HandlerQueue {
    private ArrayList<ChangeHandler> queue;

    /**
     * Constructor for HandlerQueue class, initializes an empty queue
     */

    public HandlerQueue(){
        this.queue = new ArrayList<ChangeHandler>();
    }

    /**
     * Adds a ChangeHandler object to the queue
     * 
     * @param chH the ChangeHandler object to add to the queue
     * @param sOfft not used in current implementation
     */

    public void addChange (ChangeHandler chH, int sOfft){
        this.queue.add(chH);
    }

    /**
     * Displays information about each ChangeHandler object in the queue
     */

    public  void showQueue(){
        for (ChangeHandler c : this.queue) {
            System.out.println("type = " + c.getType() + "\noffset = " + c.getOfft() + "\nlen = " + c.getLen());
        }
    }
}


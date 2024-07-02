import java.util.ArrayList;

/*Заготовка под очередь изменений, не используется*/

public class HandlerQueue {
    private ArrayList<ChangeHandler> queue;

    public HandlerQueue(){
        this.queue = new ArrayList<ChangeHandler>();
    }

    public void addChange (ChangeHandler chH, int sOfft){
        this.queue.add(chH);
    }

    public  void showQueue(){
        for (ChangeHandler c : this.queue) {
            System.out.println("type = " + c.getType() + "\noffset = " + c.getOfft() + "\nlen = " + c.getLen());
        }
    }
}


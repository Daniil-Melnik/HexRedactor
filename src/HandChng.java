import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class HandChng {
    private JFrame frame;

    public HandChng(JFrame frame){
        this.frame = frame;
    }

    public int getOpPane(String title, String msg){
        int res = 0;
        res = JOptionPane.showConfirmDialog(
                                      frame, 
                                      msg,
                                      title,
                                      JOptionPane.YES_NO_CANCEL_OPTION);
        return res;
    }

    public void showOk(String title, String msg){
        JOptionPane.showMessageDialog(frame, msg, title, 0);;
    } 
}

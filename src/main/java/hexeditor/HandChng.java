import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * HandChng class
 * Purpose: Generates JOptionPane dialogs with specific text
 * 
 * @see JOptionPane
 * @author DMelnik
 */

public class HandChng {
    private JFrame frame;

    /**
     * Constructor for HandChng class
     * 
     * @param frame the JFrame to associate dialogs with
     */

    public HandChng(JFrame frame){
        this.frame = frame;
    }

    /**
     * Displays a confirmation dialog with specified title and message
     * 
     * @param title the title of the dialog window
     * @param msg the message displayed in the dialog
     * @return the user's response (JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, JOptionPane.CANCEL_OPTION)
     */

    public int getOpPane(String title, String msg){
        int res = 0;
        res = JOptionPane.showConfirmDialog(
                                      frame, 
                                      msg,
                                      title,
                                      JOptionPane.YES_NO_CANCEL_OPTION);
        return res;
    }

    /**
     * Displays an informational dialog with specified title and message
     * 
     * @param title the title of the dialog window
     * @param msg the message displayed in the dialog
     */

    public void showOk(String title, String msg){
        JOptionPane.showMessageDialog(frame, msg, title, 0);;
    } 
}

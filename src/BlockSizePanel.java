import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.jar.JarEntry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BlockSizePanel {
    private JPanel panel;
    private int xDim;
    private int yDim;

    public BlockSizePanel(int x, int y){
        this.panel = new JPanel();
        this.xDim = x;
        this.yDim = y;
    }

    public JPanel setPanel(){
        this.panel.setBounds(xDim, yDim, 300, 150);
        this.panel.setLayout(null);
        
        JLabel j1 = new JLabel("n байт");
        JLabel j2 = new JLabel("10-я с зн.");
        JLabel j3 = new JLabel("10-я б зн.");
        JLabel j4 = new JLabel("вещ. 1-тчн");
        JLabel j5 = new JLabel("вещ. 2-тчн");

        j1.setBounds(10, 10, 100, 20); // x, y, ширина, высота
        j2.setBounds(10, 40, 100, 20); // x, y, ширина, высота
        j3.setBounds(10, 70, 100, 20); // x, y, ширина, высота
        j4.setBounds(10, 100, 100, 20); // x, y, ширина, высота
        j5.setBounds(10, 130, 100, 20); 
        
        this.panel.add(j1);
        this.panel.add(j2);
        this.panel.add(j3);
        this.panel.add(j4);
        this.panel.add(j5);

        return this.panel;
    }

    public static void main(String [] args){
    }
}

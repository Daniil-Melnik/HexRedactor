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
        this.panel.setBounds(xDim, yDim, 350, 150);
        this.panel.setLayout(null);
        
        JLabel j01 = new JLabel("n байт");
        JLabel j02 = new JLabel("10-я с зн.");
        JLabel j03 = new JLabel("10-я б зн.");
        JLabel j04 = new JLabel("вещ. 1-тчн");
        JLabel j05 = new JLabel("вещ. 2-тчн");

        JLabel j11 = new JLabel("1 : ");
        JLabel j12 = new JLabel("*");
        JLabel j13 = new JLabel("*");
        JLabel j14 = new JLabel("*");
        JLabel j15 = new JLabel("*");

        JLabel j21 = new JLabel("2 : ");
        JLabel j22 = new JLabel("*");
        JLabel j23 = new JLabel("*");
        JLabel j24 = new JLabel("*");
        JLabel j25 = new JLabel("*");

        JLabel j31 = new JLabel("4 : ");
        JLabel j32 = new JLabel("*");
        JLabel j33 = new JLabel("*");
        JLabel j34 = new JLabel("*");
        JLabel j35 = new JLabel("*");

        JLabel j41 = new JLabel("8 : ");
        JLabel j42 = new JLabel("*");
        JLabel j43 = new JLabel("*");
        JLabel j44 = new JLabel("*");
        JLabel j45 = new JLabel("*");

        j01.setBounds(0, 10, 60, 20);
        j02.setBounds(60, 10, 60, 20);
        j03.setBounds(120, 10, 60, 20);
        j04.setBounds(180, 10, 60, 20);
        j05.setBounds(250, 10, 60, 20);

        j11.setBounds(0, 30, 60, 20);
        j12.setBounds(70, 30, 60, 20);
        j13.setBounds(130, 30, 60, 20);
        j14.setBounds(190, 30, 60, 20);
        j15.setBounds(260, 30, 60, 20);

        j21.setBounds(0, 50, 60, 20);
        j22.setBounds(70, 50, 60, 20);
        j23.setBounds(130, 50, 60, 20);
        j24.setBounds(190, 50, 60, 20);
        j25.setBounds(260, 50, 60, 20);

        j31.setBounds(0, 70, 60, 20);
        j32.setBounds(70, 70, 60, 20);
        j33.setBounds(130, 70, 60, 20);
        j34.setBounds(190, 70, 60, 20);
        j35.setBounds(260, 70, 60, 20);

        j41.setBounds(0, 90, 60, 20);
        j42.setBounds(70, 90, 60, 20);
        j43.setBounds(130, 90, 60, 20);
        j44.setBounds(190, 90, 60, 20);
        j45.setBounds(260, 90, 60, 20);
        
        
        
        this.panel.add(j01);
        this.panel.add(j02);
        this.panel.add(j03);
        this.panel.add(j04);
        this.panel.add(j05);

        this.panel.add(j11);
        this.panel.add(j12);
        this.panel.add(j13);
        this.panel.add(j14);
        this.panel.add(j15);

        this.panel.add(j21);
        this.panel.add(j22);
        this.panel.add(j23);
        this.panel.add(j24);
        this.panel.add(j25);

        this.panel.add(j31);
        this.panel.add(j32);
        this.panel.add(j33);
        this.panel.add(j34);
        this.panel.add(j35);

        this.panel.add(j41);
        this.panel.add(j42);
        this.panel.add(j43);
        this.panel.add(j44);
        this.panel.add(j45);

        return this.panel;
    }

    public static void main(String [] args){
    }
}

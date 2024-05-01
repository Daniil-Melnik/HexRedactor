import javax.swing.JLabel;
import javax.swing.JPanel;

public class BlockSizePanel {
    private JPanel panel;
    private int xDim;
    private int yDim;
    private JLabel j01, j02, j03, j04, j05;
    private JLabel j11, j12, j13, j14, j15;
    private JLabel j21, j22, j23, j24, j25;
    private JLabel j31, j32, j33, j34, j35;
    private JLabel j41, j42, j43, j44, j45;

    public BlockSizePanel(int x, int y){
        this.panel = new JPanel();
        this.xDim = x;
        this.yDim = y;

        this.panel.setBounds(xDim, yDim, 450, 150);
        this.panel.setBorder(new RoundedBorder(10));
        this.panel.setLayout(null);

        this.j01 = new JLabel("n байт");
        this.j02 = new JLabel("10-я с зн.");
        this.j03 = new JLabel("10-я б зн.");
        this.j04 = new JLabel("вещ. 1-тчн");
        this.j05 = new JLabel("вещ. 2-тчн");

        this.j11 = new JLabel("1 : ");
        this.j12 = new JLabel();
        this.j13 = new JLabel();
        this.j14 = new JLabel();
        this.j15 = new JLabel("*");

        this.j21 = new JLabel("2 : ");
        this.j22 = new JLabel();
        this.j23 = new JLabel();
        this.j24 = new JLabel();
        this.j25 = new JLabel("*");

        this.j31 = new JLabel("4 : ");
        this.j32 = new JLabel();
        this.j33 = new JLabel();
        this.j34 = new JLabel();
        this.j35 = new JLabel("*");

        this.j41 = new JLabel("8 : ");
        this.j42 = new JLabel();
        this.j43 = new JLabel();
        this.j44 = new JLabel();
        this.j45 = new JLabel("*");

        this.j01.setBounds(0, 10, 60, 20);
        this.j02.setBounds(60, 10, 60, 20);
        this.j03.setBounds(120, 10, 60, 20);
        this.j04.setBounds(220, 10, 80, 20);
        this.j05.setBounds(310, 10, 80, 20);

        this.j11.setBounds(0, 30, 60, 20);
        this.j12.setBounds(70, 30, 60, 20);
        this.j13.setBounds(150, 30, 60, 20);
        this.j14.setBounds(230, 30, 80, 20);
        this.j15.setBounds(320, 30, 80, 20);

        this.j21.setBounds(0, 50, 60, 20);
        this.j22.setBounds(70, 50, 60, 20);
        this.j23.setBounds(150, 50, 60, 20);
        this.j24.setBounds(230, 50, 80, 20);
        this.j25.setBounds(320, 50, 80, 20);

        this.j31.setBounds(0, 70, 60, 20);
        this.j32.setBounds(70, 70, 60, 20);
        this.j33.setBounds(150, 70, 60, 20);
        this.j34.setBounds(230, 70, 80, 20);
        this.j35.setBounds(320, 70, 80, 20);

        this.j41.setBounds(0, 90, 60, 20);
        this.j42.setBounds(70, 90, 60, 20);
        this.j43.setBounds(150, 90, 60, 20);
        this.j44.setBounds(230, 90, 80, 20);
        this.j45.setBounds(320, 90, 80, 20);
        
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
    }

    public JPanel getPanel(){
        return this.panel;
    }

    public JPanel setPanel(long [] intArr, long [] longArr, float [] floatArr, double [] doubleArr){
        this.j12.setText("" + intArr[0]);
        this.j13.setText("" + longArr[0]);
        this.j14.setText("" + floatArr[0]);
        this.j15.setText("" + doubleArr[0]);

        this.j22.setText("" + intArr[1]);
        this.j23.setText("" + longArr[1]);
        this.j24.setText("" + floatArr[1]);
        this.j25.setText("" + doubleArr[1]);

        this.j32.setText("" + intArr[2]);
        this.j33.setText("" + longArr[2]);
        this.j34.setText("" + floatArr[2]);
        this.j35.setText("" + doubleArr[2]);

        this.j42.setText("" + intArr[3]);
        this.j43.setText("" + longArr[3]);
        this.j44.setText("*");
        this.j45.setText("" + doubleArr[3]);

        return this.panel;
    }

    public static void main(String [] args){
    }
}

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BlockSizePanel extends JPanel {
    private JLabel j01, j02, j03, j04, j05;
    private JLabel j11, j12, j13, j14, j15;
    private JLabel j21, j22, j23, j24, j25;
    private JLabel j31, j32, j33, j34, j35;
    private JLabel j41, j42, j43, j44, j45;

    public BlockSizePanel(){


    setBorder(new RoundedBorder(10));
    setLayout(null);

    j01 = new JLabel("n байт");
    j02 = new JLabel("10-я с зн.");
    j03 = new JLabel("10-я б зн.");
    j04 = new JLabel("вещ. 1-тчн");
    j05 = new JLabel("вещ. 2-тчн");

    j11 = new JLabel("1 : ");
    j12 = new JLabel();
    j13 = new JLabel();
    j14 = new JLabel();
    j15 = new JLabel("*");

    j21 = new JLabel("2 : ");
    j22 = new JLabel();
    j23 = new JLabel();
    j24 = new JLabel();
    j25 = new JLabel("*");

    j31 = new JLabel("4 : ");
    j32 = new JLabel();
    j33 = new JLabel();
    j34 = new JLabel();
    j35 = new JLabel("*");

    j41 = new JLabel("8 : ");
    j42 = new JLabel();
    j43 = new JLabel();
    j44 = new JLabel();
    j45 = new JLabel("*");

    j01.setBounds(0, 10, 60, 20);
    j02.setBounds(60, 10, 60, 20);
    j03.setBounds(120, 10, 60, 20);
    j04.setBounds(220, 10, 80, 20);
    j05.setBounds(310, 10, 80, 20);

    j11.setBounds(0, 30, 60, 20);
    j12.setBounds(70, 30, 60, 20);
    j13.setBounds(150, 30, 60, 20);
    j14.setBounds(230, 30, 80, 20);
    j15.setBounds(320, 30, 80, 20);

    j21.setBounds(0, 50, 60, 20);
    j22.setBounds(70, 50, 60, 20);
    j23.setBounds(150, 50, 60, 20);
    j24.setBounds(230, 50, 80, 20);
    j25.setBounds(320, 50, 80, 20);

    j31.setBounds(0, 70, 60, 20);
    j32.setBounds(70, 70, 60, 20);
    j33.setBounds(150, 70, 60, 20);
    j34.setBounds(230, 70, 80, 20);
    j35.setBounds(320, 70, 80, 20);

    j41.setBounds(0, 90, 60, 20);
    j42.setBounds(70, 90, 60, 20);
    j43.setBounds(150, 90, 60, 20);
    j44.setBounds(230, 90, 80, 20);
    j45.setBounds(320, 90, 80, 20);
        
    add(j01);
    add(j02);
    add(j03);
    add(j04);
    add(j05);

    add(j11);
    add(j12);
    add(j13);
    add(j14);
    add(j15);

    add(j21);
    add(j22);
    add(j23);
    add(j24);
    add(j25);

    add(j31);
    add(j32);
    add(j33);
    add(j34);
    add(j35);

    add(j41);
    add(j42);
    add(j43);
    add(j44);
    add(j45);
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

        return this;
    }

    public static void main(String [] args){
    }
}

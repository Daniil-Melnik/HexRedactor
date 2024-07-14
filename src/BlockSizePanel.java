import java.awt.Font;
import java.math.BigInteger;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
* Описание встраиваемой панели
* Назначение: Панель Значения по блокам
* */

public class BlockSizePanel extends JPanel {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;
    
    private JLabel[][] tablePanel = new JLabel[ROWS][COLUMNS];
    private JLabel mainLabel;

    private int[] columnX = { 5, 100, 210, 340, 500 };
    private int[] rowY = { 40, 75, 100, 125, 150 };
    private int[] columnWidth = { 80, 80, 100, 150, 220 };

    public BlockSizePanel() {
        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font15p = new Font("Arial", Font.PLAIN, 15);
        Font font15b = new Font("Arial", Font.BOLD, 15);

        setBorder(new RoundedBorder(10));
        setLayout(null);

        mainLabel = new JLabel("Значения по блокам");
        mainLabel.setFont(font20);
        mainLabel.setBounds(260, 10, 280, 20);
        add(mainLabel);

        tablePanel[0][0] = new JLabel("");
        tablePanel[1][0] = new JLabel("целое сзн");
        tablePanel[2][0] = new JLabel("целое бзн");
        tablePanel[3][0] = new JLabel("вещ. 1-тчн");
        tablePanel[4][0] = new JLabel("вещ. 2-тчн");

        for (int i = 1; i < 5; i++) {
            for (int k = 1; k < 5; k++) {
                tablePanel[k][i] = new JLabel();
                tablePanel[k][i].setFont(font15p);
            }
        }

        for (int k = 0; k < 5; k++) {
            tablePanel[k][0].setFont(font15b);
        }

        tablePanel[0][1] = new JLabel("1 байт");
        tablePanel[0][2] = new JLabel("2 байта");
        tablePanel[0][3] = new JLabel("4 байта");
        tablePanel[0][4] = new JLabel("8 байт");

        tablePanel[0][1].setFont(font15b);
        tablePanel[0][2].setFont(font15b);
        tablePanel[0][3].setFont(font15b);
        tablePanel[0][4].setFont(font15b);

        tablePanel[0][0].setBounds(columnX[0], rowY[0], columnWidth[0], 20);
        tablePanel[0][1].setBounds(columnX[1], rowY[0], columnWidth[1], 20);
        tablePanel[0][2].setBounds(columnX[2], rowY[0], columnWidth[2], 20);
        tablePanel[0][3].setBounds(columnX[3], rowY[0], columnWidth[3], 20);
        tablePanel[0][4].setBounds(columnX[4], rowY[0], columnWidth[4], 20);

        tablePanel[1][0].setBounds(columnX[0], rowY[1], columnWidth[0], 20);
        tablePanel[1][1].setBounds(columnX[1], rowY[1], columnWidth[1], 20);
        tablePanel[1][2].setBounds(columnX[2], rowY[1], columnWidth[2], 20);
        tablePanel[1][3].setBounds(columnX[3], rowY[1], columnWidth[3], 20);
        tablePanel[1][4].setBounds(columnX[4], rowY[1], columnWidth[4], 20);

        tablePanel[2][0].setBounds(columnX[0], rowY[2], columnWidth[0], 20);
        tablePanel[2][1].setBounds(columnX[1], rowY[2], columnWidth[1], 20);
        tablePanel[2][2].setBounds(columnX[2], rowY[2], columnWidth[2], 20);
        tablePanel[2][3].setBounds(columnX[3], rowY[2], columnWidth[3], 20);
        tablePanel[2][4].setBounds(columnX[4], rowY[2], columnWidth[4], 20);

        tablePanel[3][0].setBounds(columnX[0], rowY[3], columnWidth[0], 20);
        tablePanel[3][1].setBounds(columnX[1], rowY[3], columnWidth[1], 20);
        tablePanel[3][2].setBounds(columnX[2], rowY[3], columnWidth[2], 20);
        tablePanel[3][3].setBounds(columnX[3], rowY[3], columnWidth[3], 20);
        tablePanel[3][4].setBounds(columnX[4], rowY[3], columnWidth[4], 20);

        tablePanel[4][0].setBounds(columnX[0], rowY[4], columnWidth[0], 20);
        tablePanel[4][1].setBounds(columnX[1], rowY[4], columnWidth[1], 20);
        tablePanel[4][2].setBounds(columnX[2], rowY[4], columnWidth[2], 20);
        tablePanel[4][3].setBounds(columnX[3], rowY[4], columnWidth[3], 20);
        tablePanel[4][4].setBounds(columnX[4], rowY[4], columnWidth[4], 20);

        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                add(tablePanel[i][k]);
            }
        }
    }

    public JPanel setPanel(long[] intArr, BigInteger[] longArr, float[] floatArr, double[] doubleArr) {
        int index;
        for (int i = 1; i < 5; i++) {
            index = i - 1;
            tablePanel[1][i].setText("" + intArr[index]);
            tablePanel[2][i].setText("" + longArr[index]);
            tablePanel[3][i].setText(i != 4 ? "" + floatArr[index] : "*");
            tablePanel[4][i].setText("" + doubleArr[index]);
        }

        return this;
    }
}

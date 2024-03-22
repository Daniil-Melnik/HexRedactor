import java.io.IOException;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class mainGui extends JFrame {

    public static void createGUI() throws IOException {

        JButton Hol, forward, back;
        JTextField widthField, heightField;
        final int[] offset = {0};
        final int[] rowLen = {4};
        final int[] columnLen = {4};

        ByteIO bIO = new ByteIO("src/1.txt");
        bIO.getByteOfFile();
        utilByte ub = new utilByte(bIO.getBytes(), bIO.getHexByte());

        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = new String[rowLen[0]];
        Arrays.fill(columnNames, "");

        // String[][] data = ub.toArr(4);
        Object[][] data = {};

        // Создаем модель таблицы
        MyModel mm = new MyModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return !(column == 0);
            }
        };
        JTable table = new JTable();

        table.setModel(mm);   

        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setDefaultRenderer(JLabel.class,  new Renderer());

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 0, 0);

        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0]);

        widthField = new JTextField(15);
        widthField.setToolTipText("Ширина");
        widthField.setBounds(100, 350, 80, 20);

        heightField = new JTextField(15);
        heightField.setToolTipText("Высота");
        heightField.setBounds(200, 350, 80, 20);

        Hol = new JButton("Блямс!");;
        Hol.setBounds(100, 400, 90, 20);
        Hol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                rowLen[0] = Integer.parseInt(widthField.getText());
                columnLen[0] = Integer.parseInt(heightField.getText());

                setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0]);
            }
        });

        forward = new JButton("уперёд");;
        forward.setBounds(200, 500, 90, 20);
        forward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //if (offset[0] + rowLen[0] * columnLen[0] <= data.length + rowLen[0]){
                    offset[0] = offset[0] + rowLen[0] * columnLen[0];
                //}
                System.out.println(offset[0] + " -++");
                setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0]);
            }
        });

        back = new JButton("взад");;
        back.setBounds(100, 500, 90, 20);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //if (offset[0] - rowLen[0] * columnLen[0] >= 0){
                    offset[0] = offset[0] - rowLen[0] * columnLen[0];
                //}
                System.out.println(offset[0] + " +--");
                setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0]);
            }
        });

        frame.getContentPane().add(scrollPane);
        frame.add(widthField);
        frame.add(heightField);
        frame.add(forward);
        frame.add(back);
        frame.add(Hol);

        frame.setSize(1000, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void setTable(JTable table, int len, int vertLen, ByteIO bIO, JScrollPane scrollPane, int offset){
        utilByte ub = new utilByte(bIO.getBytes(), bIO.getHexByte());
        Object [][] data = ub.toArr(len + 1, offset, vertLen);

        String[] newColumnNames = new String[len + 1];
        
        newColumnNames[0] = "off";
        for (int m = 1; m < len + 1; m++){
            newColumnNames[m] = "" + (m - 1);
        }

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnIdentifiers(newColumnNames);

        tableModel.getDataVector().removeAllElements();
        for (int i = data.length - 1; i >= 0; i--) {
            tableModel.insertRow(0, data[i]);
        }
        int leftColumnWidth = 40;
        int columnCount = len + 1;
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(leftColumnWidth);;

        // Устанавливаем ширину остальных столбцов
        int otherColumnWidth = 80;
        scrollPane.setBounds(0, 0, 650, 300);

        for (int i = 1; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(otherColumnWidth);;
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                try {
                    createGUI();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}


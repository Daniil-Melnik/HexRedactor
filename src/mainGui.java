import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class mainGui extends JFrame {

    public static void createGUI() throws IOException {

        JButton Hol;
        JTextField widthField, heightField;

        ByteIO bIO = new ByteIO("src/1.txt");
        bIO.getByteOfFile();
        utilByte ub = new utilByte(bIO.getBytes());

        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int len = 4;
        String[] columnNames = new String[len];
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

        setTable(table, 4, bIO);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 10, 800, 300);

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
                int len = Integer.parseInt(widthField.getText());
                setTable(table, len, bIO);
            }
        });

        frame.getContentPane().add(scrollPane);
        frame.add(widthField);
        frame.add(heightField);
        frame.add(Hol);

        frame.setSize(1000, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void setTable(JTable table, int len, ByteIO bIO){
        utilByte ub = new utilByte(bIO.getBytes());
        Object [][] data = ub.toArr(len + 1);

        String[] newColumnNames = new String[len + 1];

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnIdentifiers(newColumnNames);

        tableModel.getDataVector().removeAllElements();

        for (int i = data.length - 1; i >= 0; i--) {
            tableModel.insertRow(0, data[i]);
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


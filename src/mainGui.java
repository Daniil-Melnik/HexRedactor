import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
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
        String[][] data = ub.toArr(len);

        // Создаем модель таблицы
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

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
                int len1 = Integer.parseInt(widthField.getText()) + 1;
                String [][] data1 = ub.toArr(len1);

                String[] newColumnNames = new String[len1];
                Arrays.fill(columnNames, "");

                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                tableModel.setColumnIdentifiers(newColumnNames);

                tableModel.getDataVector().removeAllElements();

                for (int i = data1.length - 1; i >= 0; i--) {
                    Object ob = new Object();
                    tableModel.insertRow(0, data1[i]);
                }
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


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.*;

public class mainGui extends JFrame {

    public static void createGUI() throws IOException {

        JButton Hol, forward, back;
        JTextField widthField, heightField;
        HandlerQueue hQ = new  HandlerQueue();
        int[] offset = {0};
        int[] rowLen = {4};
        int[] columnLen = {4};
        boolean[] changed = {false};

        MouseHig mh = new MouseHig();
        RegExp rE = new RegExp();

        ByteIO bIO = new ByteIO("src/1.txt");
        bIO.getByteOfFile();

        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandChng hc = new HandChng(frame);

        String[] columnNames = new String[rowLen[0]];
        Arrays.fill(columnNames, "");

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
        int[][] highlightCells = {};

        // Создаем экземпляр класса Renderer, передавая массив координат
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 0, 0);
        table.setDefaultRenderer(JLabel.class,  new Renderer(highlightCells));
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    int coord [] = new int [2];
                    coord[0] = row;
                    coord[1] = column;
                    mh.addCoord(coord);
                    if (mh.getCond() == 2){
                        int [][]highlightCells = mh.getFullCoords(rowLen[0]);
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                        hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                    }
                    else{
                        int [][]highlightCells = {};
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                        hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                    }
                }
            }
        });

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (row != TableModelEvent.HEADER_ROW && column != TableModelEvent.ALL_COLUMNS) {
                    Object value = table.getValueAt(row, column);
                    String [] strData = {String.valueOf(value)};
                    ChangeHandler chH = new ChangeHandler(0, offset[0] + (rowLen[0] * row) + column - 1, 1, strData);
                    //System.out.println(row * rowLen[0] + column - 1);
                    hQ.addChange(chH, row * rowLen[0] + column - 1);
                    //hQ.showData();
                    String [] _strArr = hQ.getData();
                    //hQ.showQueue();
                    changed[0] = true;

                    ArrayList<Integer> a = rE.isValidArr(_strArr, offset[0]);
                    for (Integer integer : a) {
                        System.out.println(integer);
                    }
                }
            }
        });

        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
        hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);

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
                if (!changed[0]){
                    int [][] highlightCells = {};
                    rowLen[0] = Integer.parseInt(widthField.getText());
                    columnLen[0] = Integer.parseInt(heightField.getText());
                    setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                    hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                }
                else{
                    int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                    if (result == 0){
                        // вставить запись в файл изменений
                        int [][] highlightCells = {};
                        rowLen[0] = Integer.parseInt(widthField.getText());
                        columnLen[0] = Integer.parseInt(heightField.getText());
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                        hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                        changed[0] = false;
                    }
                    else if (result == 1){
                        // вставить оставить файл в старом виде
                        int [][] highlightCells = {};
                        rowLen[0] = Integer.parseInt(widthField.getText());
                        columnLen[0] = Integer.parseInt(heightField.getText());
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                        hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                        changed[0] = false;
                    }
                    // Cancel - ничего не делать

                }              
            }
        });

        forward = new JButton("уперёд");;
        forward.setBounds(200, 500, 90, 20);
        forward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int [][] highlightCells = {};
                offset[0] = offset[0] + rowLen[0] * columnLen[0];
                if (!changed[0]){
                    setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                    hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                }
                else{
                    int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                    if (result == 0){
                        // вставить запись в файл изменений
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                        hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                        changed[0] = false;
                    }
                    else if (result == 1){
                        // вставить оставить файл в старом виде
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                        hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                        changed[0] = false;
                    }
                    // Cancel - ничего не делать
                }       
            }
        });

        back = new JButton("взад");;
        back.setBounds(100, 500, 90, 20);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int [][] highlightCells = {};
                if (offset[0] - rowLen[0] * columnLen[0] >= 0){
                    offset[0] = offset[0] - rowLen[0] * columnLen[0];
                    if (!changed[0]){
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                        hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                    }
                    else{
                        int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                        if (result == 0){
                            // вставить запись в файл изменений
                            setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                            hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                            changed[0] = false;
                        }
                        else if (result == 1){
                            // вставить оставить файл в старом виде
                            setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells);
                            hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                            changed[0] = false;
                        }
                        // Cancel - ничего не делать
                    }
                }
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
    public static void handleRightClick(int row, int column) {
        System.out.println("Right Clicked on Row: " + row + ", Column: " + column);
        // Ваша обработка правого щелчка здесь...
    }

    public static void setTable(JTable table, int len, int vertLen, ByteIO bIO, JScrollPane scrollPane, int offset, int [][] highlightCells){
        utilByte ub = new utilByte(bIO.setHexBytesOfft(offset, vertLen * len));
        //System.out.println("=== " + bIO.setHexBytesOfft(offset, vertLen * len).length + "  " + offset + "  " + vertLen * len);
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

        // Создаем экземпляр класса Renderer, передавая массив координат
        Renderer renderer = new Renderer(highlightCells);
        // Устанавливаем рендерер для всех столбцов
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
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
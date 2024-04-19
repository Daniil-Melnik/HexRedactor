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


public class mainGui extends JFrame {

    public static void createGUI() throws IOException {

        JButton Hol, forward, back, removeZero;
        JTextField widthField, heightField;
        HandlerQueue hQ = new  HandlerQueue();
        int[] offset = {0};
        int[] rowLen = {4};
        int[] columnLen = {4};
        final int[][][] highlightCells = {{}};
        boolean[] changed = {false};
        final String[][] dat = {null, null};
        SheetHolder sH = new SheetHolder();

        MouseHig mh = new MouseHig();
        RegExp rE = new RegExp();

        ByteIO bIO = new ByteIO("src/1.txt");
        //bIO.getByteOfFile();

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
                return !(column == 0);
            }
        };
        JTable table = new JTable();

        table.setModel(mm);

        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        final int[][][] errorCells = {{}};

        // Создаем экземпляр класса Renderer, передавая массив координат
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 0, 0);
        table.setDefaultRenderer(JLabel.class,  new Renderer(highlightCells[0], errorCells[0]));

        //////////////////////////////////////////////////////////////////
        //////////////////// Перехват правых кликаний ////////////////////
        //////////////////////////////////////////////////////////////////

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
                        highlightCells[0] = mh.getFullCoords(rowLen[0]);
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                    }
                    else{
                        highlightCells[0] = new int[0][0];
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                    }
                }
            }
        });

        ///////////////////////////////////////////////////////////////////
        //////////////////// Отлов изменений в таблице ////////////////////
        ///////////////////////////////////////////////////////////////////

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (row != TableModelEvent.HEADER_ROW && column != TableModelEvent.ALL_COLUMNS) {
                    Object value = table.getValueAt(row, column);
                    String [] strData = {String.valueOf(value)};
                    ChangeHandler chH = new ChangeHandler(0, offset[0] + (rowLen[0] * row) + column - 1, 1, strData);
                    System.out.println("Тип: " + chH.getType());
                    System.out.println("Сдвиг: " + chH.getOfft());
                    System.out.println("Данные: " + chH.getData()[0]);
                    sH.makeHandle(chH); // добавть изменения в SheetHolder
                    ArrayList<Integer> aL = rE.isValidArr(sH.getData(), offset[0]);
                    // for (Integer integer : aL) {
                    //     System.out.println(integer);
                    // }
                    errorCells[0] = rE.getErrorCells(rowLen[0], offset[0], aL);
                    for (int i = 0; i < errorCells[0].length; i++){
                        System.out.println(errorCells[0][i][0] + " " + errorCells[0][i][1]);
                    }
                    if (aL.isEmpty()){
                        hQ.addChange(chH, row * rowLen[0] + column - 1); 
                    }
                    else{
                        String msgErrCells = "Значения в ячейках по сдвигу: ";
                        for (Integer integer : aL) {
                            msgErrCells += integer + " ";
                            //System.out.println(integer);
                        }
                        msgErrCells += "\nнекорректны\nДолжны быть 16-ричные цисла от 00 до FF.";
                        //hc.getOpPane("Ошибка заполнения ячеек", msgErrCells);
                    }
                    // ??
                    setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                    changed[0] = true;

                }
            }
        });
        dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
        sH.setAllData(dat[0]);
        sH.setColumnLen(4);
        sH.setRowLen(4);
        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);

        //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);

        widthField = new JTextField(15);
        widthField.setToolTipText("Ширина");
        widthField.setBounds(100, 350, 80, 20);

        heightField = new JTextField(15);
        heightField.setToolTipText("Высота");
        heightField.setBounds(200, 350, 80, 20);

        ///////////////////////////////////////////////////////////////////
        //////////////////// Кнопка установки размеров ////////////////////
        ///////////////////////////////////////////////////////////////////

        Hol = new JButton("Блямс!");;
        Hol.setBounds(100, 400, 90, 20);
        Hol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!changed[0]){
                    highlightCells[0] = new int [0][0];
                    rowLen[0] = Integer.parseInt(widthField.getText());
                    columnLen[0] = Integer.parseInt(heightField.getText());
                    sH.setRowLen(rowLen[0]);
                    sH.setColumnLen(columnLen[0]);
                    setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                }
                else{
                    int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                    if (result == 0){
                        // вставить запись в файл изменений
                        highlightCells[0] = new int[0][0];
                        rowLen[0] = Integer.parseInt(widthField.getText());
                        columnLen[0] = Integer.parseInt(heightField.getText());
                        sH.setRowLen(rowLen[0]);
                        sH.setColumnLen(columnLen[0]);
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                        changed[0] = false;
                    }
                    else if (result == 1){
                        // вставить оставить файл в старом виде
                        highlightCells[0] = new int[0][0];
                        rowLen[0] = Integer.parseInt(widthField.getText());
                        columnLen[0] = Integer.parseInt(heightField.getText());
                        sH.setRowLen(rowLen[0]);
                        sH.setColumnLen(columnLen[0]);
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                        changed[0] = false;
                    }
                    // Cancel - ничего не делать

                }
            }
        });
        ///////////////////////////////////////////////////////////////////
        //////////////////// Кнопка проллистать вперёд ////////////////////
        ///////////////////////////////////////////////////////////////////
        forward = new JButton("уперёд");;
        forward.setBounds(200, 500, 90, 20);
        forward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                highlightCells[0] = new int[0][0];
                offset[0] = offset[0] + rowLen[0] * columnLen[0];
                sH.setOfft(offset[0]);

                if (!changed[0]){
                    //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                    dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
                    sH.setAllData(dat[0]);
                    setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                }
                else{
                    int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                    if (result == 0){
                        // вставить запись в файл изменений
                        int cellOfft = offset[0] - rowLen[0] * columnLen[0];
                        bIO.printData(cellOfft, dat[0]); // добавлена печать в файл изменённого фрагмента
                        dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
                        sH.setAllData(dat[0]); // менять или нет сдвиг ??
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                        //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                        changed[0] = false;
                    }
                    else if (result == 1){
                        // оставить файл в старом виде
                        dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
                        sH.setAllData(dat[0]);
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                        //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                        changed[0] = false;
                    }
                    // Cancel - ничего не делать
                }
            }
        });

        //////////////////////////////////////////////////////////////////
        //////////////////// Кнопка проллистать назад ////////////////////
        //////////////////////////////////////////////////////////////////

        back = new JButton("взад");;
        back.setBounds(100, 500, 90, 20);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                highlightCells[0] = new int[0][0];
                if (offset[0] - rowLen[0] * columnLen[0] >= 0){
                    offset[0] = offset[0] - rowLen[0] * columnLen[0];
                    sH.setOfft(offset[0]);
                    dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
                    if (!changed[0]){
                        sH.setAllData(dat[0]);
                        setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                    }
                    else{
                        int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                        if (result == 0){
                            // вставить запись в файл изменений
                            sH.setAllData(dat[0]);
                            setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                            //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                            changed[0] = false;
                        }
                        else if (result == 1){
                            // вставить оставить файл в старом виде
                            sH.setAllData(dat[0]);
                            setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                            //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                            changed[0] = false;
                        }
                        // Cancel - ничего не делать
                    }
                }
            }
        });

        ///////////////////////////////////////////////////////////////////
        /////////////////// Кнопка удаления с обнулением //////////////////
        ///////////////////////////////////////////////////////////////////

        removeZero = new JButton("под ноль");;
        removeZero.setBounds(300, 400, 90, 20);
        removeZero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int [] startCoord = highlightCells[0][0];
                int offt = offset[0] + startCoord[0] * rowLen[0] + startCoord[1] - 1;
                int highlightLen = highlightCells[0].length;
                for (int j = 0; j < highlightCells[0].length; j++){
                    System.out.println("=== " + highlightCells[0][0] + " " + highlightCells[0][1]);
                } 
                ChangeHandler cHZero = new ChangeHandler(1, offt, highlightLen, null);
                sH.makeHandle(cHZero);
                String [] newData = sH.getData();
                for (int i = 0; i < newData.length; i++){
                    System.out.print(newData[i] + " ");
                }
                setTable(table, rowLen[0], columnLen[0], bIO, scrollPane, offset[0], highlightCells[0], errorCells[0], sH);
                System.out.println("ИТОГОВЫЙ СДВИГ = " + highlightLen);
            }
        });

        frame.getContentPane().add(scrollPane);
        frame.add(widthField);
        frame.add(removeZero);
        frame.add(heightField);
        frame.add(forward);
        frame.add(back);
        frame.add(Hol);

        frame.setSize(1000, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void setTable(JTable table, int rowLen, int columnLen, ByteIO bIO, JScrollPane scrollPane, int offt, int [][] highlightCells, int[][] errorCells, SheetHolder sH){
        String [] data = null;
        data = sH.getData();

        utilByte ub = new utilByte();
        Object [][] tableData = ub.toLabeledArr(rowLen, columnLen, data, offt);

        String[] newColumnNames = new String[rowLen + 1];

        newColumnNames[0] = "off";
        for (int m = 1; m < rowLen + 1; m++){
            newColumnNames[m] = "" + (m - 1);
        }

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnIdentifiers(newColumnNames);

        tableModel.getDataVector().removeAllElements();
        for (int i = tableData.length - 1; i >= 0; i--) {
            tableModel.insertRow(0, tableData[i]);
        }
        int leftColumnWidth = 40;
        int columnCount = columnLen + 1;
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(leftColumnWidth);;

        // Устанавливаем ширину остальных столбцов
        int otherColumnWidth = 80;
        scrollPane.setBounds(0, 0, 650, 300);

        for (int i = 1; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(otherColumnWidth);;
        }

        // Создаем экземпляр класса Renderer, передавая массив координат
        Renderer renderer = new Renderer(highlightCells, errorCells);
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
import java.awt.event.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


public class mainGui extends JFrame {

    public static void createGUI() throws IOException {

        JButton Hol, forward, back, removeZero, removeShift, fillZero, fillShift, fillSubst, cutToBufferZero, cutToBufferShift, fillInBuffer;
        JTextField widthField, heightField, lenField, dataField;
        HandlerQueue hQ = new  HandlerQueue();
        ByteTransform bT = new ByteTransform();
        int[] offset = {0, 0};
        int[] rowLen = {4};
        int[] columnLen = {4};
        int[] dLen = {0};

        EditBtnActions eBA = new EditBtnActions();

        final int[][][] highlightCells = {{}};
        final int[][][] findedCells = {{}};
        boolean[] changed = {false};
        final String[][] dat = {null, null};

        String [] maskValue = {""};
        final String[] byteSize = {""};
        SheetHolder sH = new SheetHolder("src/1.txt");

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
        final String[][] buffer = {{}};

        // Создаем экземпляр класса Renderer, передавая массив координат
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 0, 0);
        table.setDefaultRenderer(JLabel.class,  new Renderer(highlightCells[0], errorCells[0], findedCells[0]));

        BlockSizePanel bSP = new BlockSizePanel();
        bSP.setBounds(400, 10, 700, 175);
        frame.add(bSP);

        SearchPanel searchPanel = new SearchPanel();
        searchPanel.setBounds(400, 200, 400, 150);
        frame.add(searchPanel);

        searchPanel.addSearchButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String inputText = searchPanel.inputField.getText();
                
                byteSize[0] = (String) searchPanel.byteSizeComboBox.getSelectedItem();

                utilByte uB = new utilByte();
                int offt1 = offset[1] + rowLen[0] * columnLen[0];
                String [] rightData = bIO.getHexBytesOfft(offt1, 7);
                String [] data = uB.fillInSevenBytes(sH.getData(), rightData);

                maskValue[0] = inputText;
                int len = Integer.parseInt(byteSize[0]);

                int [] offts = {};
                RegExp rG = new RegExp();
                if (searchPanel.isSearchByMaskSelected()) {
                    if (rG.isMask(maskValue[0])){
                        offts = bT.getBytesOffsetMask(data, len, maskValue[0]);
                    }
                    else {
                        hc.showOk("Ошибка", "Маска - непустая строка состоящая из символов : {0, 1, *}");
                    }
                } else if (searchPanel.isSearchByValueSelected()) {
                    if (rG.isValue(maskValue[0])){
                        BigInteger val = new BigInteger(maskValue[0]);
                        offts = bT.getByteOffsetsValue(data, len, val);
                    }
                    else {
                        hc.showOk("Ошибка", "Значение - целое беззнаковое число");
                    }
                }

                findedCells[0] = sH.getTableCellCoords(offts);

                
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);

            }
        });

        EditPanel editPanel = new EditPanel();
        editPanel.setBounds(400, 360, 400, 180);
        editPanel.addEditButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String optText = (String) editPanel.comboBox.getSelectedItem();

                if (optText.equals("Вставить нули")){

                    JTextField lenField = editPanel.zeroField;
                    eBA.btnFillInZero(sH, lenField, highlightCells[0]);
                }
                else if (optText.equals("Удалить")){
                    System.out.println("qq");
                }
                highlightCells[0] = new int[0][0];
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                changed[0] = true;
                System.out.println(optText);
            }
        });
        frame.add(editPanel);

        TableInfoPanel tableInfoPanel = new TableInfoPanel(0, 0, 0, 0);
        tableInfoPanel.setBounds(820, 360, 280, 180);
        frame.add(tableInfoPanel);

        FileManagerPanel fileManagerPanel = new FileManagerPanel("initial.txt");
        fileManagerPanel.setBounds(820, 200, 280, 150);
        frame.add(fileManagerPanel);


        final int [] prevCol = {0};
        final int [] prevRow = {1};

        /////////////////////////////////////////////////////////////////
        ////////////// Перехват изменения строки фокуса /////////////////
        /////////////////////////////////////////////////////////////////
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int currentRow = table.getSelectedRow();
                    int currentCol = table.getSelectedColumn();
                    utilByte uB = new utilByte();
                    if (((currentRow != prevRow[0]) || (currentCol != prevCol[0])) && (currentRow != (-1)) && (currentCol != (-1)) ) {
                        prevRow[0] = currentRow;
                        prevCol[0] = currentCol;
                        long [] intArr = new long [4];
                        BigInteger [] longArr = new BigInteger[4];
                        float [] floatArr = new float[4];
                        double [] doubleArr  = new double[4];

                        int row = table.getSelectedRow();
                        int col = table.getSelectedColumn();

                        int offt1 = offset[1] + rowLen[0] * columnLen[0];
                        String [] rightData = bIO.getHexBytesOfft(offt1, 7);
                        String [] data = uB.fillInSevenBytes(sH.getData(), rightData);
                        // for (String str : data) System.out.print(str + " ");
                                

                        int offt2 = row * rowLen[0] + col - 1;

                        System.out.println(offt2);

                        intArr[0] =  bT.getSigned(data, offt2, 1);
                        intArr[1] = bT.getSigned(data, offt2, 2);
                        intArr[2] = bT.getSigned(data, offt2, 4);
                        intArr[3] = bT.getSigned(data, offt2, 8);

                        longArr[0] = bT.getUnsigned(data, offt2, 1);
                        longArr[1] = bT.getUnsigned(data, offt2, 2);
                        longArr[2] = bT.getUnsigned(data, offt2, 4);
                        longArr[3] = bT.getUnsigned(data, offt2, 8);

                        floatArr[0] = bT.getFloat(data, offt2, 1);
                        floatArr[1] = bT.getFloat(data, offt2, 2);
                        floatArr[2] = bT.getFloat(data, offt2, 4);
                        floatArr[3] = -1;

                        doubleArr[0] = bT.getDouble(data, offt2, 1);
                        doubleArr[1] = bT.getDouble(data, offt2, 2);
                        doubleArr[2] = bT.getDouble(data, offt2, 4);
                        doubleArr[3] = bT.getDouble(data, offt2, 8);
                        bSP.setPanel(intArr, longArr, floatArr, doubleArr);
                        System.out.println("1 Фокус установлен на ячейке: строка " + row + ", колонка " + col);
                    }
                }
            }
        });
        
        ////////////////////////////////////////////////////////////////
        ///////////// Перехват изменения столбца фокуса ////////////////
        ////////////////////////////////////////////////////////////////
        table.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int currentRow = table.getSelectedRow();
                    int currentCol = table.getSelectedColumn();
                    utilByte uB = new utilByte();
                    if (((currentRow != prevRow[0]) || (currentCol != prevCol[0])) && (currentRow != -1) && (currentCol != -1)) {
                        prevRow[0] = currentRow;
                        prevCol[0] = currentCol;
                        long [] intArr = new long [4];
                        BigInteger [] longArr = new BigInteger[4];
                        float [] floatArr = new float[4];
                        double [] doubleArr  = new double[4];

                        int row = table.getSelectedRow();
                        int col = table.getSelectedColumn();

                        int offt1 = offset[1] + rowLen[0] * columnLen[0];
                        String [] rightData = bIO.getHexBytesOfft(offt1, 7);
                        String [] data = uB.fillInSevenBytes(sH.getData(), rightData);
                        // for (String str : data) System.out.print(str + " ");
                                

                        int offt2 = row * rowLen[0] + col - 1;

                        System.out.println(offt2);

                        intArr[0] =  bT.getSigned(data, offt2, 1);
                        intArr[1] = bT.getSigned(data, offt2, 2);
                        intArr[2] = bT.getSigned(data, offt2, 4);
                        intArr[3] = bT.getSigned(data, offt2, 8);

                        longArr[0] = bT.getUnsigned(data, offt2, 1);
                        longArr[1] = bT.getUnsigned(data, offt2, 2);
                        longArr[2] = bT.getUnsigned(data, offt2, 4);
                        longArr[3] = bT.getUnsigned(data, offt2, 8);

                        floatArr[0] = bT.getFloat(data, offt2, 1);
                        floatArr[1] = bT.getFloat(data, offt2, 2);
                        floatArr[2] = bT.getFloat(data, offt2, 4);
                        floatArr[3] = -1;

                        doubleArr[0] = bT.getDouble(data, offt2, 1);
                        doubleArr[1] = bT.getDouble(data, offt2, 2);
                        doubleArr[2] = bT.getDouble(data, offt2, 4);
                        doubleArr[3] = bT.getDouble(data, offt2, 8);
                        bSP.setPanel(intArr, longArr, floatArr, doubleArr);
                        System.out.println("2 Фокус установлен на ячейке: строка " + row + ", колонка " + col);
                    }
                }
            }
        });


        //////////////////////////////////////////////////////////////////
        //////////////////// Перехват правых кликаний ////////////////////
        //////////////////////////////////////////////////////////////////

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    int [] coord = new int [2];
                    coord[0] = row;
                    coord[1] = column;
                    mh.addCoord(coord);
                    if (mh.getCond() == 2){
                        highlightCells[0] = mh.getFullCoords(rowLen[0]);
                        setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                    }
                    else{
                        highlightCells[0] = new int[0][0];
                        setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                    }
                }
            }
        });


        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_D )
                {
                    int row = table.getSelectionModel().getLeadSelectionIndex();
                    int column = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
                    int [] coord = new int [2];
                    coord[0] = row;
                    coord[1] = column;
                    mh.addCoord(coord);
                    if (mh.getCond() == 2){
                        highlightCells[0] = mh.getFullCoords(rowLen[0]);
                        setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                    }
                    else{
                        highlightCells[0] = new int[0][0];
                        setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                    }
                    System.out.println(coord[0] + " " + coord[1]);
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
                        //String msgErrCells = "Значения в ячейках по сдвигу: ";
                        // for (Integer integer : aL) {
                        //     msgErrCells += integer + " ";
                        //     //System.out.println(integer);
                        // }
                        // msgErrCells += "\nнекорректны\nДолжны быть 16-ричные цисла от 00 до FF.";
                        //hc.getOpPane("Ошибка заполнения ячеек", msgErrCells);
                    }
                    // ??
                    setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                    changed[0] = true;

                }
            }
        });
        sH.setColumnLen(columnLen[0]); // что-то не то, где то задана константа
        sH.setRowLen(rowLen[0]);
        dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
        sH.setAllData(dat[0]);

        setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);

        //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);

        widthField = new JTextField(15);
        widthField.setToolTipText("Ширина");

        heightField = new JTextField(15);
        heightField.setToolTipText("Высота");

        lenField = new JTextField(15);
        lenField.setToolTipText("Длина вставки");

        dataField = new JTextField(15);
        dataField.setToolTipText("Данные вставки");

        ///////////////////////////////////////////////////////////////////
        //////////////////// Кнопка установки размеров ////////////////////
        ///////////////////////////////////////////////////////////////////

        Hol = new JButton("размер");;
        Hol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!changed[0]){
                    highlightCells[0] = new int [0][0];
                    rowLen[0] = Integer.parseInt(widthField.getText());
                    columnLen[0] = Integer.parseInt(heightField.getText());
                    sH.setRowLen(rowLen[0]);
                    sH.setColumnLen(columnLen[0]);
                    String [] data = bIO.getHexBytesOfft(offset[1], rowLen[0] * columnLen[0]);
                    sH.setAllData(data);
                    setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                }
                else{
                    int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                    if (result == 0){
                        // какая то ошибка со сдвигами при печати
                        highlightCells[0] = new int[0][0];

                        dat[0] = sH.getData();
                        int cellOfft = offset[1] - rowLen[0] * columnLen[0];

                        bIO.printData(cellOfft, dat[0], dLen[0]); // добавлена печать в файл изменённого фрагмента

                        rowLen[0] = Integer.parseInt(widthField.getText());
                        columnLen[0] = Integer.parseInt(heightField.getText());
                        sH.setRowLen(rowLen[0]);
                        sH.setColumnLen(columnLen[0]);

                        dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
                        sH.setAllData(dat[0]); // менять или нет сдвиг ??

                        offset[0] = offset[1];
                        
                        String [] data = bIO.getHexBytesOfft(offset[1], rowLen[0] * columnLen[0]);
                        sH.setAllData(data);
                        setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                        changed[0] = false;
                        dLen[0] = 0;
                    }
                    else if (result == 1){
                        // вставить оставить файл в старом виде
                        highlightCells[0] = new int[0][0];
                        rowLen[0] = Integer.parseInt(widthField.getText());
                        columnLen[0] = Integer.parseInt(heightField.getText());
                        sH.setRowLen(rowLen[0]);
                        sH.setColumnLen(columnLen[0]);
                        setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                        changed[0] = false;
                    }
                    // Cancel - ничего не делать

                }
            }
        });
        ///////////////////////////////////////////////////////////////////
        //////////////////// Кнопка проллистать вперёд ////////////////////
        ///////////////////////////////////////////////////////////////////
        forward = new JButton("далее");
        forward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                highlightCells[0] = new int[0][0];
                findedCells[0] = new int[0][0];
                offset[0] = offset[0] + rowLen[0] * columnLen[0];
                offset[1] = offset[1] + rowLen[0] * columnLen[0];
                sH.setOfft(offset[0]);
                sH.setColumnLen(columnLen[0]);            

                if (!changed[0]){
                    //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                    dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
                    sH.setAllData(dat[0]);
                    dLen[0] = 0;
                    // setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                }
                else{
                    int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                    if (result == 0){
                        // вставить запись в файл изменений
                        int cellOfft = offset[1] - rowLen[0] * columnLen[0];
                        dat[0] = sH.getData();
                        bIO.printData(cellOfft, dat[0], dLen[0]); // добавлена печать в файл изменённого фрагмента
                        dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
                        sH.setAllData(dat[0]); // менять или нет сдвиг ??
                        offset[0] = offset[1]; // добавленого в тест
                        // setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                        //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                        dLen[0] = 0;
                        changed[0] = false;
                    }
                    else if (result == 1){
                        // оставить файл в старом виде
                        dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
                        sH.setAllData(dat[0]);
                        // setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                        //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                        changed[0] = false;
                    }
                    // Cancel - ничего не делать
                }

                // наследование поиска начало
                if ((!maskValue.equals("")) && (!byteSize[0].equals(""))){

                    utilByte uB = new utilByte();
                    int offt1 = offset[1] + rowLen[0] * columnLen[0];
                    String [] rightData = bIO.getHexBytesOfft(offt1, 7);
                    String [] data = uB.fillInSevenBytes(sH.getData(), rightData);

                    int len = Integer.parseInt(byteSize[0]);

                    int [] offts = {};

                    RegExp rG = new RegExp();

                    boolean isMask = rG.isMask(maskValue[0]);

                    if (isMask) {
                        offts = bT.getBytesOffsetMask(data, len, maskValue[0]);
                    } else {
                        BigInteger val = new BigInteger(maskValue[0]);
                        offts = bT.getByteOffsetsValue(data, len, val);
                    }

                    findedCells[0] = sH.getTableCellCoords(offts);
                }

                // наследование поиска конец    
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
            }
        });

        //////////////////////////////////////////////////////////////////
        //////////////////// Кнопка проллистать назад ////////////////////
        //////////////////////////////////////////////////////////////////

        back = new JButton("назад");;
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                highlightCells[0] = new int[0][0];
                if (offset[1] - rowLen[0] * columnLen[0] >= 0){
                    offset[0] = offset[0] - rowLen[0] * columnLen[0];
                    offset[1] = offset[1] - rowLen[0] * columnLen[0];
                    sH.setOfft(offset[0]);
                    dat[0] = bIO.getHexBytesOfft(offset[0], rowLen[0]*columnLen[0]);
                    if (!changed[0]){
                        sH.setAllData(dat[0]);
                        setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                    }
                    else{
                        int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                        if (result == 0){
                            // вставить запись в файл изменений
                            sH.setAllData(dat[0]);
                            setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                            //hQ.setData(bIO, offset[0], rowLen[0] * columnLen[0]);
                            changed[0] = false;
                        }
                        else if (result == 1){
                            // вставить оставить файл в старом виде
                            sH.setAllData(dat[0]);
                            setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
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

        removeZero = new JButton("уд. 0");
        removeZero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int [] startCoord = highlightCells[0][0];
                int offt = offset[1] + startCoord[0] * rowLen[0] + startCoord[1] - 1; // поменяно 21.04.2024 offset с 0 на 1
                int highlightLen = highlightCells[0].length;
                ChangeHandler cHZero = new ChangeHandler(1, offt, highlightLen, null);
                sH.makeHandle(cHZero);
                dat[0] = sH.getData();
                for (int i = 0; i < dat[0].length; i++){
                    System.out.print(dat[0][i] + " ");
                }
                changed[0] = true;
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                System.out.println("ИТОГОВЫЙ СДВИГ = " + highlightLen);
            }
        });

        ///////////////////////////////////////////////////////////////////
        //////////////////// Кнопка удаления со сдвигом ///////////////////
        ///////////////////////////////////////////////////////////////////

        removeShift = new JButton("уд. сдв.");
        removeShift.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int [] startCoord = highlightCells[0][0];
                int offt = offset[1] + startCoord[0] * rowLen[0] + startCoord[1] - 1; // поменяно 21.04.2024 offset с 0 на 1
                int highlightLen = highlightCells[0].length;
                dLen[0] += highlightLen; // добавлен доп. сдвиг в файле для печати
                ChangeHandler cHShift = new ChangeHandler(2, offt, highlightLen, null);
                sH.makeHandle(cHShift);
                dat[0] = sH.getData();
                for (int i = 0; i < dat[0].length; i++){
                    System.out.print(dat[0][i] + " ");
                }
                changed[0] = true;
                offset[0] = offset[0] + highlightLen;
                highlightCells[0] = new int[0][0];
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                System.out.println("ИТОГОВЫЙ СДВИГ = " + highlightLen);
            }
        });

        ///////////////////////////////////////////////////////////////////
        /////////////////////// Кнопка вставки нулей //////////////////////
        ///////////////////////////////////////////////////////////////////
        fillZero = new JButton("вст. 0");
        fillZero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                // int [] startCoord = highlightCells[0][0];
                // int len = Integer.parseInt(lenField.getText());
                // int currOfft = startCoord[0] * rowLen[0] + startCoord[1] - 1; // сдвиг в таблице по координате
                // ChangeHandler chH = new ChangeHandler(7, currOfft, len, null);
                // sH.makeHandle(chH);

                eBA.btnFillInZero(sH, lenField, highlightCells[0]);

                highlightCells[0] = new int[0][0];
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                changed[0] = true;
            }
        });

        //////////////////////////////////////////////////////////////////
        //////////////////// Кнопка вставки со сдвигом ///////////////////
        //////////////////////////////////////////////////////////////////
        fillShift = new JButton("вст. cдв.");
        fillShift.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String adStr = dataField.getText();
                String [] currData = bIO.splitHexBytes(adStr);
                int [] startCoord = highlightCells[0][0];
                int currOfft = startCoord[0] * rowLen[0] + startCoord[1] - 1;
                int len = currData.length;
                ChangeHandler chH = new ChangeHandler(4, currOfft, len, currData);
                sH.makeHandle(chH);
                highlightCells[0] = new int[0][0];
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                changed[0] = true;
            }
        });

        //////////////////////////////////////////////////////////////////
        /////////////// Кнопка вставки со сдвигом из буфера //////////////
        //////////////////////////////////////////////////////////////////
        fillInBuffer = new JButton("вст. буф.");
        fillInBuffer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int [] startCoord = highlightCells[0][0];
                int currOfft = startCoord[0] * rowLen[0] + startCoord[1] - 1;
                int len = buffer[0].length;
                ChangeHandler chH = new ChangeHandler(4, currOfft, len, buffer[0]);
                sH.makeHandle(chH);
                highlightCells[0] = new int[0][0];
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                changed[0] = true;
            }
        });

        //////////////////////////////////////////////////////////////////
        /////////////////// Кнопка вставки с замещением //////////////////
        //////////////////////////////////////////////////////////////////
        fillSubst = new JButton("вст. зам.");
        fillSubst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String adStr = dataField.getText();
                String [] currData = bIO.splitHexBytes(adStr);
                int [] startCoord = highlightCells[0][0];
                int currOfft = startCoord[0] * rowLen[0] + startCoord[1] - 1; // сдвиг в таблице по координате
                int len = currData.length;
                ChangeHandler chH = new ChangeHandler(3, currOfft, len, currData);
                sH.makeHandle(chH);
                highlightCells[0] = new int[0][0];
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                changed[0] = true;                
            }
        });

        ////////////////////////////////////////////////////////////////
        /////////////////// Кнопка вырезки со сдвигом //////////////////
        ////////////////////////////////////////////////////////////////
        cutToBufferShift = new JButton("вырез. сдв.");
        cutToBufferShift.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                int [] startCoord = highlightCells[0][0];
                int offt = offset[1] + startCoord[0] * rowLen[0] + startCoord[1] - 1;
                int highlightLen = highlightCells[0].length;

                utilByte uB = new utilByte();
                String [] strArr = uB.getValuesOfHighlt(table, highlightCells[0]);
                buffer[0] = strArr;

                ChangeHandler chH = new ChangeHandler(2, offt, highlightLen, null);
                sH.makeHandle(chH);
                highlightCells[0] = new int[0][0];
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);
                changed[0] = true;  

                for (String str : buffer[0]) System.out.println(str);
            }
        });

        ////////////////////////////////////////////////////////////////
        ////////////////// Кнопка вырезки с обнулением /////////////////
        ////////////////////////////////////////////////////////////////
        cutToBufferZero = new JButton("вырез. 0.");
        cutToBufferZero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                int [] startCoord = highlightCells[0][0];
                int offt = offset[1] + startCoord[0] * rowLen[0] + startCoord[1] - 1; // поменяно 21.04.2024 offset с 0 на 1
                int highlightLen = highlightCells[0].length;

                utilByte uB = new utilByte();
                String [] strArr = uB.getValuesOfHighlt(table, highlightCells[0]);
                buffer[0] = strArr;

                ChangeHandler cHZero = new ChangeHandler(1, offt, highlightLen, null);
                sH.makeHandle(cHZero);
                dat[0] = sH.getData();

                changed[0] = true;
                setTable(table, scrollPane, offset[1], highlightCells[0], errorCells[0], findedCells[0], sH);

                for (String str : buffer[0]) System.out.println(str);
            }
        });

        fillZero.setBounds(700, 610, 110, 20);
        fillShift.setBounds(600, 120, 110, 20);
        fillSubst.setBounds(600, 150, 110, 20);
        fillInBuffer.setBounds(600, 250, 110, 20);

        removeShift.setBounds(600, 50, 110, 20);
        removeZero.setBounds(600, 20, 110, 20);

        cutToBufferShift.setBounds(600, 190, 110, 20);
        cutToBufferZero.setBounds(600, 220, 110, 20);

        Hol.setBounds(440, 400, 90, 20);
        forward.setBounds(500, 500, 90, 20);
        back.setBounds(400, 500, 90, 20);

        widthField.setBounds(400, 350, 80, 20);
        heightField.setBounds(500, 350, 80, 20);
        lenField.setBounds(720, 90, 80, 20);
        dataField.setBounds(720, 150, 150, 20);

          
        

        frame.getContentPane().add(scrollPane);
        // frame.add(widthField);
        // frame.add(removeZero);
        // frame.add(removeShift);
        frame.add(fillZero);
        // frame.add(fillShift);
        // frame.add(fillSubst);
        // frame.add(fillInBuffer);
        // frame.add(cutToBufferShift);
        // frame.add(cutToBufferZero);
        // frame.add(heightField);
        // frame.add(lenField);
        // frame.add(dataField);
        // frame.add(forward);
        // frame.add(back);
        // frame.add(Hol);

        frame.setSize(1150, 620);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void setTable(JTable table, JScrollPane scrollPane, int offt, int [][] highlightCells, int[][] errorCells, int[][] findedCells, SheetHolder sH){
        String [] data = null;
        data = sH.getData();
        utilByte ub = new utilByte();
        int rowLen = sH.getRowLen();
        int columnLen = sH.getColumnLen();

        System.out.println("Количество строк = " + columnLen);
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
        int columnCount = rowLen + 1; // количество столбцов, а не строк
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(leftColumnWidth);;

        // Устанавливаем ширину остальных столбцов
        int otherColumnWidth = 80;
        scrollPane.setBounds(0, 0, 350, 588);

        for (int i = 1; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(otherColumnWidth);
        }

        // Создаем экземпляр класса Renderer, передавая массив координат
        Renderer renderer = new Renderer(highlightCells, errorCells, findedCells);
        table.changeSelection(0, 1, false, false);
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
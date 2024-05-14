import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


public class mainGui extends JFrame {

    public static void createGUI() throws IOException {

        JButton forward, back;
        JTextField widthField, heightField, lenField, dataField;
        HandlerQueue hQ = new  HandlerQueue();
        ByteTransform bT = new ByteTransform();
        int[] offset = {0, 0};

        EditBtnActions eBA = new EditBtnActions();

        boolean[] changed = {false};
        final String[][] dat = {null, null};

        String [] maskValue = {""};
        final String [] fName = {""};
        final String[] byteSize = {""};

        final SheetHolder [] sH = {null};
        final ByteIO [] bIO = {null};

        MouseHig mh = new MouseHig();
        RegExp rE = new RegExp();

        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandChng hc = new HandChng(frame);

        Object[][] data = {};

        JTable table = new JTable();

        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        final String[][] buffer = {{}};

        // Создаем экземпляр класса Renderer, передавая массив координат
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 0, 0);

        int [][] highlightCells = new int[0][0];
        int [][] findedCells = new int[0][0];
        int [][] errorCells = new int [0][0];
        table.setDefaultRenderer(JLabel.class,  new Renderer(highlightCells, errorCells, findedCells));

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

                int rowLen = sH[0].getRowLen();
                int columnLen = sH[0].getColumnLen();

                int offt1 = offset[1] + rowLen * columnLen;
                String [] rightData = bIO[0].getHexBytesOfft(offt1, 7);
                String [] data = uB.fillInSevenBytes(sH[0].getData(), rightData);

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

                int [][] findedCells = sH[0].getTableCellCoords(offts);
                sH[0].setFCells(findedCells);
                setTable(table, scrollPane, offset[1], sH[0]);

            }
        });

        ////////////////////////////////////////////////////////////////
        //////////////////////// Операции панели ///////////////////////
        ////////////////////////////////////////////////////////////////

        EditPanel editPanel = new EditPanel();
        editPanel.setBounds(400, 360, 400, 180);
        editPanel.addEditButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String optText = (String) editPanel.comboBox.getSelectedItem();
                int [][] highlightCells = sH[0].getHCells();

                if (optText.equals("Вставить нули")){

                    JTextField lenField = editPanel.zeroField;
                    eBA.btnFillInZero(sH[0], lenField, highlightCells);
                }

                else if (optText.equals("Удалить")){
                    if (editPanel.isZeroSelected()){
                        eBA.btnRemoveZero(sH[0], offset, highlightCells);
                    }
                    if (editPanel.isShiftSelected()){
                        eBA.btnRemoveShift(sH[0], offset, highlightCells);
                    }
                }

                else if (optText.equals("Вставить")){
                    boolean shiftCond = editPanel.isShiftPasteSelected();
                    boolean replaceCond = editPanel.isReplaceSelected();

                    System.out.println(shiftCond + " " + replaceCond);

                    String bufferValue = (String) editPanel.valueBuffer.getSelectedItem();

                    if (bufferValue.equals("задать")){
                        String adStr = editPanel.valueField.getText();
                        String [] currData = bIO[0].splitHexBytes(adStr);
                        if (shiftCond){
                            System.out.println("****");
                            eBA.btnPasteShift(sH[0], currData, highlightCells);
                        }
                        else if (replaceCond){
                            eBA.btnPasteSubst(sH[0], currData, highlightCells);
                        }
                    }
                    if ((bufferValue.equals("из буфера"))){
                        if (shiftCond){
                            eBA.btnPasteShift(sH[0], buffer[0], highlightCells);
                        }
                        else if (replaceCond){
                            eBA.btnPasteSubst(sH[0], buffer[0], highlightCells);
                        }
                    }
                }
                else if(optText.equals("Вырезать")){
                    if (editPanel.isShiftSelected()){
                        eBA.btnCutShift(table, sH[0], offset, buffer, highlightCells);
                    }
                    if (editPanel.isZeroSelected()){
                        eBA.btnCutZero(table, sH[0], offset, buffer, highlightCells);
                    }
                }
                highlightCells = new int[0][0];
                int [][] findedCells = new int[0][0];
                sH[0].setHCells(highlightCells);
                sH[0].setFCells(findedCells);
                setTable(table, scrollPane, offset[1], sH[0]);
                changed[0] = true;
                System.out.println(optText);
            }
        });
        frame.add(editPanel);

        ////////////////////////////////////////////////////////////////
        ////////////////// Изменение размеров таблицы //////////////////
        ////////////////////////////////////////////////////////////////

        TableInfoPanel tableInfoPanel = new TableInfoPanel(0, 0, 0, 0);
        tableInfoPanel.setBounds(820, 360, 280, 180);
        frame.add(tableInfoPanel);
        tableInfoPanel.addChangeSizeButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ChangeSizeDialog changeSizeDialog = new ChangeSizeDialog();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        changeSizeDialog,
                        "Введите значения",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (result == JOptionPane.OK_OPTION) {
                    String height = changeSizeDialog.getHeightValue();
                    String width = changeSizeDialog.getWidthValue();

                    int rowLen;
                    int columnLen;
                    if (!changed[0]){
                        int [][] highlightCells = new int [0][0];
                        sH[0].setHCells(highlightCells);

                        rowLen = Integer.parseInt(height);
                        sH[0].setRowLen(rowLen);

                        columnLen = Integer.parseInt(width);
                        sH[0].setColumnLen(columnLen);

                        String [] data = bIO[0].getHexBytesOfft(offset[1], rowLen * columnLen);
                        sH[0].setAllData(data);
                    }
                    else{
                        int resultChng = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                        if (resultChng == 0){
                            int [][] highlightCells = new int[0][0]; // reset
                            sH[0].setHCells(highlightCells);

                            dat[0] = sH[0].getData();

                            rowLen = sH[0].getRowLen();
                            columnLen = sH[0].getColumnLen();

                            int cellOfft = offset[1] - rowLen * columnLen;
                            int tmpDLen = sH[0].getDLen();
                            bIO[0].printData(cellOfft, dat[0], tmpDLen, fName[0]); // добавлена печать в файл изменённого фрагмента

                            rowLen = Integer.parseInt(height);
                            columnLen = Integer.parseInt(width);

                            sH[0].setRowLen(rowLen);
                            sH[0].setColumnLen(columnLen);

                            dat[0] = bIO[0].getHexBytesOfft(offset[0], rowLen*columnLen);
                            sH[0].setAllData(dat[0]); // менять или нет сдвиг ??

                            offset[0] = offset[1];
                            
                            String [] data = bIO[0].getHexBytesOfft(offset[1], rowLen * columnLen);
                            sH[0].setAllData(data);

                            changed[0] = false;
                            sH[0].setDLen(0);
                        }
                        else if (resultChng == 1){
                            // вставить оставить файл в старом виде
                            int [][] highlightCells = new int[0][0];
                            sH[0].setHCells(highlightCells);
                            rowLen = Integer.parseInt(height);
                            columnLen = Integer.parseInt(width);

                            sH[0].setRowLen(rowLen);
                            sH[0].setColumnLen(columnLen);
                            changed[0] = false;
                        }
                    }
                    

                } else {
                    System.out.println("Отмена или закрытие");
                }

                setTable(table, scrollPane, offset[1], sH[0]);
            }
        });

        

        FileManagerPanel fileManagerPanel = new FileManagerPanel("null");
        fileManagerPanel.setBounds(820, 200, 280, 150);
        frame.add(fileManagerPanel);
        fileManagerPanel.addOpenFileButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // Открываем диалоговое окно для выбора файла
                int result = fileChooser.showOpenDialog(frame);

                // Если пользователь выбрал файл, получаем путь к нему
                if (result == JFileChooser.APPROVE_OPTION) {
                    fName[0] = fileChooser.getSelectedFile().getAbsolutePath();
                    bIO[0] = new ByteIO(fName[0]);

                    sH[0] = new SheetHolder(fName[0]);

                    sH[0].setColumnLen(4); // что-то не то, где то задана константа
                    sH[0].setRowLen(4);
                    sH[0].setDLen(0);
                    dat[0] = bIO[0].getHexBytesOfft(offset[0], 4*4);
                    sH[0].setAllData(dat[0]);
                    setTable(table, scrollPane, offset[1], sH[0]);
                    String [] smallFName = fName[0].split("\\\\");
                    fileManagerPanel.setCurrentFile(smallFName[smallFName.length - 1]);
                    //System.out.println("Selected file: " + fName[0]);
                } else {
                    System.out.println("File selection cancelled.");
                }
            }
        });
        
        fileManagerPanel.addSaveAsButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Создаем JFileChooser для сохранения файла
                JFileChooser fileChooser = new JFileChooser();
                    
                // Настраиваем диалог на режим сохранения
                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    
                // Предлагаем имя по умолчанию (можно изменить по желанию)
                fileChooser.setSelectedFile(new File("default_filename.txt"));
    
                // Открываем диалоговое окно "Сохранить как"
                int result = fileChooser.showSaveDialog(frame);
    
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
    
                    // Убеждаемся, что файл имеет нужное расширение, например, ".txt"
                    if (!filePath.toLowerCase().endsWith(".txt")) {
                        filePath += ".txt";
                    }
                    int rowLen = sH[0].getRowLen();
                    int columnLen = sH[0].getColumnLen();

                    int cellOfft = offset[1] - rowLen * columnLen;
                    dat[0] = sH[0].getData();
                    int tmpDLen = sH[0].getDLen();
                    bIO[0].printData(cellOfft, dat[0], tmpDLen, filePath);
                } else {
                    System.out.println("Save operation cancelled.");
                }
            }
            
        });

        final int [] prevCol = {0};
        final int [] prevRow = {1};

        /////////////////////////////////////////////////////////////////
        ////////////// Перехват изменения строки фокуса /////////////////
        /////////////////////////////////////////////////////////////////
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    sH[0].setCurrentRow(table.getSelectedRow());
                    sH[0].setCurrentColumn(table.getSelectedColumn());
                    int currentRow = sH[0].getCurrentRow(); 
                    int currentCol = sH[0].getCurrentColumn();
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

                        int rowLen = sH[0].getRowLen();
                        int columnLen = sH[0].getColumnLen();

                        int offt1 = offset[1] + rowLen * columnLen;
                        String [] rightData = bIO[0].getHexBytesOfft(offt1, 7);
                        String [] data = uB.fillInSevenBytes(sH[0].getData(), rightData);
                        // for (String str : data) System.out.print(str + " ");
                                

                        int offt2 = row * rowLen + col - 1;

                        System.out.println(offt2);

                        for (int i = 0; i < 4; i++){
                            intArr[i] = bT.getSigned(data, offt2, (int) Math.pow(2, i));
                            longArr[i] = bT.getUnsigned(data, offt2, (int) Math.pow(2, i));
                            floatArr[i] = (i < 3) ? bT.getFloat(data, offt2, (int) Math.pow(2, i)) : -1;
                            doubleArr[i] = bT.getDouble(data, offt2, (int) Math.pow(2, i));
                        }

                        bSP.setPanel(intArr, longArr, floatArr, doubleArr);
                        System.out.println("1 Фокус установлен на ячейке: строка " + row + ", колонка " + col);
                    }
                    int [][] highlightCells = sH[0].getHCells();
                    tableInfoPanel.updTableInfo(sH[0], highlightCells, offset);
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

                    sH[0].setCurrentRow(table.getSelectedRow());
                    sH[0].setCurrentColumn(table.getSelectedColumn());
                    int currentRow = sH[0].getCurrentRow(); 
                    int currentCol = sH[0].getCurrentColumn();

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

                        int rowLen = sH[0].getRowLen();
                        int columnLen = sH[0].getColumnLen();

                        int offt1 = offset[1] + rowLen * columnLen;
                        String [] rightData = bIO[0].getHexBytesOfft(offt1, 7);
                        String [] data = uB.fillInSevenBytes(sH[0].getData(), rightData);
                        // for (String str : data) System.out.print(str + " ");
                                

                        int offt2 = row * rowLen + col - 1;

                        System.out.println(offt2);

                        for (int i = 0; i < 4; i++){
                            intArr[i] = bT.getSigned(data, offt2, (int) Math.pow(2, i));
                            longArr[i] = bT.getUnsigned(data, offt2, (int) Math.pow(2, i));
                            floatArr[i] = (i < 3) ? bT.getFloat(data, offt2, (int) Math.pow(2, i)) : -1;
                            doubleArr[i] = bT.getDouble(data, offt2, (int) Math.pow(2, i));
                        }

                        bSP.setPanel(intArr, longArr, floatArr, doubleArr);
                        System.out.println("2 Фокус установлен на ячейке: строка " + row + ", колонка " + col);
                    }
                    int [][] highlightCells = sH[0].getHCells();
                    tableInfoPanel.updTableInfo(sH[0], highlightCells, offset);
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

                    int rowLen = sH[0].getRowLen();

                    if (mh.getCond() == 2){
                        int [][] highlightCells = mh.getFullCoords(rowLen);
                        sH[0].setHCells(highlightCells);
                        setTable(table, scrollPane, offset[1], sH[0]);
                    }
                    else{
                        int [][] highlightCells = new int[0][0]; // reset
                        sH[0].setHCells(highlightCells);
                        setTable(table, scrollPane, offset[1], sH[0]);
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

                    int rowLen = sH[0].getRowLen();

                    mh.addCoord(coord);
                    if (mh.getCond() == 2){
                        int [][] highlightCells = mh.getFullCoords(rowLen);
                        sH[0].setHCells(highlightCells);
                        setTable(table, scrollPane, offset[1], sH[0]);
                    }
                    else{
                        int [][] highlightCells = new int[0][0]; // reset
                        sH[0].setHCells(highlightCells);
                        setTable(table, scrollPane, offset[1], sH[0]);
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

                    int rowLen = sH[0].getRowLen();

                    ChangeHandler chH = new ChangeHandler(0, offset[0] + (rowLen * row) + column - 1, 1, strData);
                    System.out.println("Тип: " + chH.getType());
                    System.out.println("Сдвиг: " + chH.getOfft());
                    System.out.println("Данные: " + chH.getData()[0]);
                    sH[0].makeHandle(chH); // добавть изменения в SheetHolder
                    ArrayList<Integer> aL = rE.isValidArr(sH[0].getData(), offset[0]);
                    int [][] errorCells = rE.getErrorCells(rowLen, offset[0], aL);
                    sH[0].setErCells(errorCells);
                    for (int i = 0; i < errorCells.length; i++){
                        System.out.println(errorCells[i][0] + " " + errorCells[i][1]);
                    }
                    if (aL.isEmpty()){
                        hQ.addChange(chH, row * rowLen + column - 1); 
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
                    setTable(table, scrollPane, offset[1], sH[0]);
                    changed[0] = true;

                }
            }
        });

        widthField = new JTextField(15);
        widthField.setToolTipText("Ширина");

        heightField = new JTextField(15);
        heightField.setToolTipText("Высота");

        lenField = new JTextField(15);
        lenField.setToolTipText("Длина вставки");

        dataField = new JTextField(15);
        dataField.setToolTipText("Данные вставки");

        ///////////////////////////////////////////////////////////////////
        //////////////////// Кнопка проллистать вперёд ////////////////////
        ///////////////////////////////////////////////////////////////////

        forward = new JButton();
        forward.setIcon(new ImageIcon("src/icons/forward.png"));
        forward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int [][] highlightCells = new int[0][0];
                sH[0].setHCells(highlightCells);

                int [][] findedCells = new int[0][0];
                sH[0].setFCells(findedCells);


                int rowLen = sH[0].getRowLen();
                int columnLen = sH[0].getColumnLen();

                offset[0] = offset[0] + rowLen * columnLen;
                offset[1] = offset[1] + rowLen * columnLen;

                sH[0].setOfft(offset[0]);
                sH[0].setColumnLen(columnLen);            

                if (!changed[0]){
                    dat[0] = bIO[0].getHexBytesOfft(offset[0], rowLen*columnLen);
                    sH[0].setAllData(dat[0]);
                    sH[0].setDLen(0);
                }
                else{
                    int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                    if (result == 0){
                        // вставить запись в файл изменений
                        int cellOfft = offset[1] - rowLen * columnLen;
                        dat[0] = sH[0].getData();
                        int tmpDLen = sH[0].getDLen();
                        bIO[0].printData(cellOfft, dat[0], tmpDLen, fName[0]);
                        dat[0] = bIO[0].getHexBytesOfft(offset[0], rowLen*columnLen);
                        sH[0].setAllData(dat[0]); // менять или нет сдвиг ??
                        offset[0] = offset[1]; // добавленого в тест
                        changed[0] = false;
                        sH[0].setDLen(0);
                    }
                    else if (result == 1){
                        // оставить файл в старом виде
                        dat[0] = bIO[0].getHexBytesOfft(offset[0], rowLen*columnLen);
                        sH[0].setAllData(dat[0]);
                        changed[0] = false;
                    }
                }

                // наследование поиска начало
                if ((!maskValue.equals("")) && (!byteSize[0].equals(""))){
                    utilByte uB = new utilByte();
                    int offt1 = offset[1] + rowLen * columnLen;
                    String [] rightData = bIO[0].getHexBytesOfft(offt1, 7);
                    String [] data = uB.fillInSevenBytes(sH[0].getData(), rightData);

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

                    findedCells = sH[0].getTableCellCoords(offts);
                    sH[0].setFCells(findedCells);
                }

                // наследование поиска конец
                setTable(table, scrollPane, offset[1], sH[0]);
            }
        });

        //////////////////////////////////////////////////////////////////
        //////////////////// Кнопка проллистать назад ////////////////////
        //////////////////////////////////////////////////////////////////

        back = new JButton();
        back.setIcon(new ImageIcon("src/icons/back.png"));
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int [][] highlightCells = new int[0][0];
                sH[0].setHCells(highlightCells);

                int rowLen = sH[0].getRowLen();
                int columnLen = sH[0].getColumnLen();

                if (offset[1] - rowLen * columnLen >= 0){
                    offset[0] = offset[0] - rowLen * columnLen;
                    offset[1] = offset[1] - rowLen * columnLen;
                    sH[0].setOfft(offset[0]);
                    dat[0] = bIO[0].getHexBytesOfft(offset[0], rowLen*columnLen);
                    if (!changed[0]){
                        sH[0].setAllData(dat[0]);
                        setTable(table, scrollPane, offset[1], sH[0]);
                    }
                    else{
                        int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                        if (result == 0){
                            // вставить запись в файл изменений
                            sH[0].setAllData(dat[0]);
                            setTable(table, scrollPane, offset[1], sH[0]);
                            changed[0] = false;
                        }
                        else if (result == 1){
                            // вставить оставить файл в старом виде
                            sH[0].setAllData(dat[0]);
                            setTable(table, scrollPane, offset[1], sH[0]);
                            changed[0] = false;
                        }
                    }
                }
            }
        });
        forward.setBounds(755, 550, 345, 40);
        back.setBounds(400, 550, 345, 40);

        widthField.setBounds(400, 350, 350, 20);
        heightField.setBounds(500, 350, 350, 20);
        lenField.setBounds(720, 90, 80, 20);
        dataField.setBounds(720, 150, 150, 20);

        frame.getContentPane().add(scrollPane);
        frame.add(forward);
        frame.add(back);

        frame.setSize(1150, 620);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void setTable(JTable table, JScrollPane scrollPane, int offt, SheetHolder sH){
        String [] data = null;
        data = sH.getData();
        utilByte ub = new utilByte();
        int rowLen = sH.getRowLen();
        int columnLen = sH.getColumnLen();

        int [][] highlightCells = sH.getHCells();
        int [][] findedCells = sH.getFCells();
        int [][] errorCells = sH.getErCells();

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
        table.requestFocus(); 
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
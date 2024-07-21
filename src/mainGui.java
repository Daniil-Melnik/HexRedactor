import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class mainGui extends JFrame {

    public static void createGUI() throws IOException {

        JButton forward, back, info;
        JTextField dataField;
        JTextField heightField;
        JTextField lenField;
        JTextField widthField;
        HandlerQueue hQ = new HandlerQueue();
        ByteTransform bT = new ByteTransform();
        int[] offset = { 0, 0 };

        EditBtnActions eBA = new EditBtnActions();

        boolean[] changed = { false };
        final String[][] dat = { null, null };

        String[] maskValue = { "" };
        final String[] fileName = { "" };
        final String[] byteSize = { "" };

        final SheetHolder[] sH = { null };
        final ByteFormatIO[] bIO = { null };

        MouseHig mh = new MouseHig();
        RegExp rE = new RegExp();

        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandChng hc = new HandChng(frame);

        // Object[][] data = {};

        JTable table = new JTable();

        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        final String[][] buffer = { {} };

        // Создаем экземпляр класса Renderer, передавая массив координат
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 0, 0);

        int[][] highlightCells = new int[0][0];
        int[][] findedCells = new int[0][0];
        int[][] errorCells = new int[0][0];
        table.setDefaultRenderer(JLabel.class, new Renderer(highlightCells, errorCells, findedCells));

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

                UtilByte uB = new UtilByte();

                int rowLen = sH[0].getRowLen();
                int columnLen = sH[0].getColumnLen();

                int offt1 = offset[1] + rowLen * columnLen;
                String[] rightData = bIO[0].getHexBytesByOffset(offt1, 7);
                String[] data = uB.fillInSevenBytes(sH[0].getData(), rightData);

                maskValue[0] = inputText;
                int len = Integer.parseInt(byteSize[0]);

                int[] offsets = {};
                RegExp rG = new RegExp();
                if (searchPanel.isSearchByMaskSelected()) {
                    if (rG.isMask(maskValue[0])) {
                        offsets = bT.getBytesOffsetByMask(data, len, maskValue[0]);
                    } else {
                        hc.showOk("Ошибка", "Маска - непустая строка состоящая из символов : {0, 1, *}");
                    }
                } else if (searchPanel.isSearchByValueSelected()) {
                    if (rG.isValue(maskValue[0])) {
                        BigInteger val = new BigInteger(maskValue[0]);
                        offsets = bT.getByteValueByOffsets(data, len, val);
                    } else {
                        hc.showOk("Ошибка", "Значение - целое беззнаковое число");
                    }
                }

                int[][] findedCells = sH[0].getTableCellCoords(offsets);
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

                // String optText = (String) editPanel.comboOperationType.getSelectedItem();
                EditTypes selectedAction = (EditTypes) editPanel.comboOperationType.getSelectedItem();
                int[][] highlightCells = sH[0].getHCells();
                try {
                    switch (selectedAction) {
                        case DELETE:
                            if (editPanel.isZeroSelected()) {
                                eBA.btnRemoveZero(sH[0], offset, highlightCells);
                            }
                            if (editPanel.isShiftSelected()) {
                                eBA.btnRemoveShift(sH[0], offset, highlightCells);
                            }
                            break;
                        
                        case INSERT:
                            boolean shiftCond = editPanel.isShiftPasteSelected();
                            boolean replaceCond = editPanel.isReplaceSelected();

                            String bufferValue = (String) editPanel.valueBuffer.getSelectedItem();

                            if (bufferValue.equals("задать")) {
                                String adStr = editPanel.valueField.getText();
                                String[] currData = bIO[0].splitHexBytes(adStr);
                                if (rE.isValidArrBool(currData)) {
                                    if (shiftCond) {
                                        if (sH[0].isEmptyVolume(currData.length)) {
                                            eBA.btnPasteShift(sH[0], currData, highlightCells);
                                        } else
                                            hc.showOk("Ошибка", "Количество байт на странице превышает 1МБ");
                                    } else if (replaceCond) {
                                        eBA.btnPasteSubst(sH[0], currData, highlightCells);
                                    }
                                } else {
                                    hc.showOk("Ошибка", "Введите через ';' список байт от 00 до FF");
                                }
                            }
                            if ((bufferValue.equals("из буфера"))) {
                                if (shiftCond) {
                                    if (sH[0].isEmptyVolume(buffer[0].length)) {
                                        eBA.btnPasteShift(sH[0], buffer[0], highlightCells);
                                    } else
                                        hc.showOk("Ошибка", "Количество байт на странице превышает 1МБ");
                                } else if (replaceCond) {
                                    eBA.btnPasteSubst(sH[0], buffer[0], highlightCells);
                                }
                            }
                            break;
                        
                        case INSERT_ZERO:
                            JTextField lenField = editPanel.zeroNumberField;
                            eBA.btnFillInZero(sH[0], lenField, highlightCells);
                            break;
                        
                        case CUT:
                            if (editPanel.isShiftSelected()) {
                                eBA.btnCutShift(table, sH[0], offset, buffer, highlightCells);
                            }
                            if (editPanel.isZeroSelected()) {
                                eBA.btnCutZero(table, sH[0], offset, buffer, highlightCells);
                            }
                            break;
                    
                        default:
                            break;
                    }
                    // if (optText.equals("Вставить нули")) {

                    //     JTextField lenField = editPanel.zeroNumberField;
                    //     eBA.btnFillInZero(sH[0], lenField, highlightCells);
                    // }

                    // else if (optText.equals("Удалить")) {
                    //     if (editPanel.isZeroSelected()) {
                    //         eBA.btnRemoveZero(sH[0], offset, highlightCells);
                    //     }
                    //     if (editPanel.isShiftSelected()) {
                    //         eBA.btnRemoveShift(sH[0], offset, highlightCells);
                    //     }
                    // }

                    // else if (optText.equals("Вставить")) {
                    //     boolean shiftCond = editPanel.isShiftPasteSelected();
                    //     boolean replaceCond = editPanel.isReplaceSelected();

                    //     String bufferValue = (String) editPanel.valueBuffer.getSelectedItem();

                    //     if (bufferValue.equals("задать")) {
                    //         String adStr = editPanel.valueField.getText();
                    //         String[] currData = bIO[0].splitHexBytes(adStr);
                    //         if (rE.isValidArrBool(currData)) {
                    //             if (shiftCond) {
                    //                 if (sH[0].isEmptyVolume(currData.length)) {
                    //                     eBA.btnPasteShift(sH[0], currData, highlightCells);
                    //                 } else
                    //                     hc.showOk("Ошибка", "Количество байт на странице превышает 1МБ");
                    //             } else if (replaceCond) {
                    //                 eBA.btnPasteSubst(sH[0], currData, highlightCells);
                    //             }
                    //         } else {
                    //             hc.showOk("Ошибка", "Введите через ';' список байт от 00 до FF");
                    //         }
                    //     }
                    //     if ((bufferValue.equals("из буфера"))) {
                    //         if (shiftCond) {
                    //             if (sH[0].isEmptyVolume(buffer[0].length)) {
                    //                 eBA.btnPasteShift(sH[0], buffer[0], highlightCells);
                    //             } else
                    //                 hc.showOk("Ошибка", "Количество байт на странице превышает 1МБ");
                    //         } else if (replaceCond) {
                    //             eBA.btnPasteSubst(sH[0], buffer[0], highlightCells);
                    //         }
                    //     }
                    // } 
                    // else if (optText.equals("Вырезать")) {
                    //     if (editPanel.isShiftSelected()) {
                    //         eBA.btnCutShift(table, sH[0], offset, buffer, highlightCells);
                    //     }
                    //     if (editPanel.isZeroSelected()) {
                    //         eBA.btnCutZero(table, sH[0], offset, buffer, highlightCells);
                    //     }
                    // } 
                    // else if (optText.equals("Копировать")) {
                    //     eBA.btnCopy(table, sH[0], offset, buffer, highlightCells);
                    // }
                    sH[0].resetSheet(mh);
                    setTable(table, scrollPane, offset[1], sH[0]);
                    changed[0] = true;
                } catch (ArrayIndexOutOfBoundsException exception) {
                    hc.showOk("Ошибка", "Нельзя редактировать область **");
                }
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
                try {
                    ChangeSizeDialog changeSizeDialog = new ChangeSizeDialog();

                    int result = JOptionPane.showConfirmDialog(
                            frame,
                            changeSizeDialog,
                            "Введите значения",
                            JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        String height = changeSizeDialog.getHeightValue();
                        String width = changeSizeDialog.getWidthValue();

                        int preColumnLen = Integer.parseInt(height);
                        int preRowLen = Integer.parseInt(width);

                        if (preRowLen * preColumnLen < 1048577) {
                            int rowLen;
                            int columnLen;
                            if (!changed[0]) {
                                int[][] highlightCells = new int[0][0];
                                sH[0].setHCells(highlightCells);

                                rowLen = Integer.parseInt(width);
                                sH[0].setRowLen(rowLen);

                                columnLen = Integer.parseInt(height);
                                sH[0].setColumnLen(columnLen);

                                String[] data = bIO[0].getHexBytesByOffset(offset[1], rowLen * columnLen);
                                sH[0].setAllData(data);
                            } else {
                                int resultChng = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                                if (resultChng == 0) {
                                    sH[0].setHCells(new int[0][0]);

                                    dat[0] = sH[0].getData();

                                    rowLen = sH[0].getRowLen();
                                    columnLen = sH[0].getColumnLen();

                                    int cellOfft = offset[1] - rowLen * columnLen;
                                    int tmpDLen = sH[0].getDLen();
                                    bIO[0].printData(cellOfft, dat[0], tmpDLen, fileName[0]); // добавлена печать в файл
                                                                                           // изменённого фрагмента

                                    rowLen = Integer.parseInt(height);
                                    columnLen = Integer.parseInt(width);

                                    sH[0].setRowLen(rowLen);
                                    sH[0].setColumnLen(columnLen);

                                    dat[0] = bIO[0].getHexBytesByOffset(offset[0], rowLen * columnLen);
                                    sH[0].setAllData(dat[0]); // менять или нет сдвиг ??

                                    offset[0] = offset[1];

                                    String[] data = bIO[0].getHexBytesByOffset(offset[1], rowLen * columnLen);
                                    sH[0].setAllData(data);

                                    changed[0] = false;
                                    sH[0].setDLen(0);
                                } else if (resultChng == 1) {
                                    // вставить оставить файл в старом виде
                                    sH[0].setHCells(new int[0][0]);
                                    rowLen = Integer.parseInt(height);
                                    columnLen = Integer.parseInt(width);

                                    sH[0].setRowLen(rowLen);
                                    sH[0].setColumnLen(columnLen);
                                    changed[0] = false;
                                }
                            }
                        } else {
                            hc.showOk("Ошибка", "Размер страницы не более 1МБ");
                        }

                    } 

                    setTable(table, scrollPane, offset[1], sH[0]);
                } catch (NumberFormatException ex) {
                    hc.showOk("Ошибка", "Размерности - целое число ячеек");
                }
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

                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    fileName[0] = fileChooser.getSelectedFile().getAbsolutePath();
                    bIO[0] = new ByteFormatIO(fileName[0]);

                    sH[0] = new SheetHolder(fileName[0]);

                    sH[0].setColumnLen(4); // что-то не то, где то задана константа
                    sH[0].setRowLen(4);
                    sH[0].setDLen(0);
                    dat[0] = bIO[0].getHexBytesByOffset(offset[0], 4 * 4);
                    sH[0].setAllData(dat[0]);
                    setTable(table, scrollPane, offset[1], sH[0]);
                    String[] smallFName = fileName[0].split("\\\\");
                    fileManagerPanel.setCurrentFile(smallFName[smallFName.length - 1]);
                }
            }
        });

        fileManagerPanel.addSaveAsButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                fileChooser.setSelectedFile(new File("default_filename.txt"));

                int result = fileChooser.showSaveDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();

                    if (!filePath.toLowerCase().endsWith(".txt")) {
                        filePath += ".txt";
                    }
                    int rowLen = sH[0].getRowLen();
                    int columnLen = sH[0].getColumnLen();

                    int cellOfft = offset[1] - rowLen * columnLen;
                    dat[0] = sH[0].getData();
                    int tmpDLen = sH[0].getDLen();
                    bIO[0].printData(cellOfft, dat[0], tmpDLen, filePath);
                }
            }

        });

        final int[] prevCol = { 0 };
        final int[] prevRow = { 1 };

        /////////////////////////////////////////////////////////////////
        ////////////// Перехват изменения строки фокуса /////////////////
        /////////////////////////////////////////////////////////////////
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rowColLen = 4;
                if (!e.getValueIsAdjusting()) {
                    sH[0].setCurrentRow(table.getSelectedRow());
                    sH[0].setCurrentColumn(table.getSelectedColumn());
                    int currentRow = sH[0].getCurrentRow();
                    int currentCol = sH[0].getCurrentColumn();
                    UtilByte uB = new UtilByte();
                    if (((currentRow != prevRow[0]) || (currentCol != prevCol[0])) && (currentRow != (-1))
                            && (currentCol != (-1))) {
                        prevRow[0] = currentRow;
                        prevCol[0] = currentCol;
                        long[] intArr = new long[rowColLen];
                        BigInteger[] longArr = new BigInteger[rowColLen];
                        float[] floatArr = new float[rowColLen];
                        double[] doubleArr = new double[rowColLen];

                        int row = table.getSelectedRow();
                        int col = table.getSelectedColumn();

                        int rowLen = sH[0].getRowLen();
                        int columnLen = sH[0].getColumnLen();

                        int offt1 = offset[1] + rowLen * columnLen;
                        String[] rightData = bIO[0].getHexBytesByOffset(offt1, 7);
                        String[] data = uB.fillInSevenBytes(sH[0].getData(), rightData);

                        int offt2 = row * rowLen + col - 1;

                        for (int i = 0; i < 4; i++) {
                            intArr[i] = bT.getSignedInt(data, offt2, (int) Math.pow(2, i));
                            longArr[i] = bT.getUnsignedInt(data, offt2, (int) Math.pow(2, i));
                            floatArr[i] = (i < 3) ? bT.getFloatNumber(data, offt2, (int) Math.pow(2, i)) : -1;
                            doubleArr[i] = bT.getDoubleNumber(data, offt2, (int) Math.pow(2, i));
                        }

                        bSP.setPanel(intArr, longArr, floatArr, doubleArr);
                    }
                    int[][] highlightCells = sH[0].getHCells();
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
                int rowColLen = 4;
                if (!e.getValueIsAdjusting()) {

                    sH[0].setCurrentRow(table.getSelectedRow());
                    sH[0].setCurrentColumn(table.getSelectedColumn());
                    int currentRow = sH[0].getCurrentRow();
                    int currentCol = sH[0].getCurrentColumn();

                    UtilByte uB = new UtilByte();
                    if (((currentRow != prevRow[0]) || (currentCol != prevCol[0])) && (currentRow != -1)
                            && (currentCol != -1)) {
                        prevRow[0] = currentRow;
                        prevCol[0] = currentCol;
                        long[] intArr = new long[rowColLen];
                        BigInteger[] longArr = new BigInteger[rowColLen];
                        float[] floatArr = new float[rowColLen];
                        double[] doubleArr = new double[rowColLen];

                        int row = table.getSelectedRow();
                        int col = table.getSelectedColumn();

                        int rowLen = sH[0].getRowLen();
                        int columnLen = sH[0].getColumnLen();

                        int offt1 = offset[1] + rowLen * columnLen;
                        String[] rightData = bIO[0].getHexBytesByOffset(offt1, 7);
                        String[] data = uB.fillInSevenBytes(sH[0].getData(), rightData);

                        int offt2 = row * rowLen + col - 1;

                        for (int i = 0; i < 4; i++) {
                            intArr[i] = bT.getSignedInt(data, offt2, (int) Math.pow(2, i));
                            longArr[i] = bT.getUnsignedInt(data, offt2, (int) Math.pow(2, i));
                            floatArr[i] = (i < 3) ? bT.getFloatNumber(data, offt2, (int) Math.pow(2, i)) : -1;
                            doubleArr[i] = bT.getDoubleNumber(data, offt2, (int) Math.pow(2, i));
                        }

                        bSP.setPanel(intArr, longArr, floatArr, doubleArr);
                    }
                    int[][] highlightCells = sH[0].getHCells();
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
                    int[] coord = new int[2];
                    coord[0] = row;
                    coord[1] = column;
                    if (!((String) table.getValueAt(row, column)).equals("**")) {
                        mh.addCoord(coord);

                        int rowLen = sH[0].getRowLen();

                        if (mh.getCond() == 2) {
                            int[][] highlightCells = mh.getFullCoords(rowLen);
                            sH[0].setHCells(highlightCells);
                            setTable(table, scrollPane, offset[1], sH[0]);
                        } else {
                            sH[0].setHCells(new int[0][0]);
                            setTable(table, scrollPane, offset[1], sH[0]);
                        }
                    }
                }
            }
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    int row = table.getSelectionModel().getLeadSelectionIndex();
                    int column = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
                    int[] coord = new int[2];
                    coord[0] = row;
                    coord[1] = column;

                    int rowLen = sH[0].getRowLen();

                    mh.addCoord(coord);
                    if (mh.getCond() == 2) {
                        int[][] highlightCells = mh.getFullCoords(rowLen);
                        sH[0].setHCells(highlightCells);
                        setTable(table, scrollPane, offset[1], sH[0]);
                    } else {
                        sH[0].setHCells(new int[0][0]);
                        setTable(table, scrollPane, offset[1], sH[0]);
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
                    String[] strData = { String.valueOf(value) };

                    int rowLen = sH[0].getRowLen();

                    String operationType = ChangeTypes.MODIFI_CELL.getValue();

                    ChangeHandler chH = new ChangeHandler(operationType, offset[0] + (rowLen * row) + column - 1, 1, strData);
                    sH[0].makeHandle(chH); // добавть изменения в SheetHolder
                    ArrayList<Integer> aL = rE.isValidArr(sH[0].getData(), offset[0]);
                    int[][] errorCells = rE.getErrorCells(rowLen, offset[0], aL);
                    sH[0].setErCells(errorCells);
                    if (aL.isEmpty()) {
                        hQ.addChange(chH, row * rowLen + column - 1);
                    } else {
                        String msgErrCells = "Значения в ячейках по сдвигу: ";
                        for (Integer integer : aL) {
                            msgErrCells += integer + " ";
                        }
                        msgErrCells += "\nнекорректны\nДолжны быть 16-ричные цисла от 00 до FF.";
                        hc.getOpPane("Ошибка заполнения ячеек", msgErrCells);
                    }
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
                try {

                    long fileLen = bIO[0].getFileLength(sH[0].getfName());
                    int rowLen = sH[0].getRowLen();
                    int columnLen = sH[0].getColumnLen();
                    if ((offset[0] + (long) rowLen * columnLen) < fileLen) {
                        sH[0].setHCells(new int[0][0]);

                        int[][] findedCells = new int[0][0];
                        sH[0].setFCells(findedCells);

                        offset[0] = offset[0] + rowLen * columnLen;
                        offset[1] = offset[1] + rowLen * columnLen;

                        sH[0].setOfft(offset[0]);
                        sH[0].setColumnLen(columnLen);

                        if (!changed[0]) {
                            dat[0] = bIO[0].getHexBytesByOffset(offset[0], rowLen * columnLen);
                            sH[0].setAllData(dat[0]);
                            sH[0].setDLen(0);
                        } else {
                            int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                            if (result == 0) {
                                // вставить запись в файл изменений
                                int cellOfft = offset[1] - rowLen * columnLen;
                                dat[0] = sH[0].getData();
                                int tmpDLen = sH[0].getDLen();
                                bIO[0].printData(cellOfft, dat[0], tmpDLen, fileName[0]);
                                dat[0] = bIO[0].getHexBytesByOffset(offset[0], rowLen * columnLen);
                                sH[0].setAllData(dat[0]);
                                offset[0] = offset[1];
                                changed[0] = false;
                                sH[0].setDLen(0);
                            } else if (result == 1) {
                                // оставить файл в старом виде
                                dat[0] = bIO[0].getHexBytesByOffset(offset[0], rowLen * columnLen);
                                sH[0].setAllData(dat[0]);
                                changed[0] = false;
                            }
                        }

                        // наследование поиска начало
                        if ((!maskValue.equals("")) && (!byteSize[0].equals(""))) {
                            UtilByte uB = new UtilByte();
                            int offt1 = offset[1] + rowLen * columnLen;
                            String[] rightData = bIO[0].getHexBytesByOffset(offt1, 7);
                            String[] data = uB.fillInSevenBytes(sH[0].getData(), rightData);

                            int len = Integer.parseInt(byteSize[0]);

                            int[] offsets = {};

                            RegExp rG = new RegExp();

                            boolean isMask = rG.isMask(maskValue[0]);

                            if (isMask) {
                                offsets = bT.getBytesOffsetByMask(data, len, maskValue[0]);
                            } else {
                                BigInteger val = new BigInteger(maskValue[0]);
                                offsets = bT.getByteValueByOffsets(data, len, val);
                            }

                            findedCells = sH[0].getTableCellCoords(offsets);
                            sH[0].setFCells(findedCells);
                        }

                        // наследование поиска конец
                        sH[0].resetSheet(mh);
                        setTable(table, scrollPane, offset[1], sH[0]);
                    }
                } catch (IOException e) {
                    System.err.println("An error occurred while trying to get the file length: " + e.getMessage());
                }
            }
        });

        //////////////////////////////////////////////////////////////////
        //////////////////// Кнопка проллистать назад ////////////////////
        //////////////////////////////////////////////////////////////////

        back = new JButton();
        back.setIcon(new ImageIcon("src/icons/back.png"));
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int rowLen = sH[0].getRowLen();
                int columnLen = sH[0].getColumnLen();
                if ((offset[0] - (long) rowLen * columnLen) >= 0) {
                    sH[0].setHCells(new int[0][0]);

                    int[][] findedCells = new int[0][0];
                    sH[0].setFCells(findedCells);

                    offset[0] = offset[0] - rowLen * columnLen;
                    offset[1] = offset[1] - rowLen * columnLen;

                    sH[0].setOfft(offset[0]);
                    sH[0].setColumnLen(columnLen);

                    if (!changed[0]) {
                        dat[0] = bIO[0].getHexBytesByOffset(offset[0], rowLen * columnLen);
                        sH[0].setAllData(dat[0]);
                        sH[0].setDLen(0);
                    } else {
                        int result = hc.getOpPane("Сохранение", "Данные изменены. Сохранить?");
                        if (result == 0) {
                            int cellOfft = offset[1] + rowLen * columnLen;
                            dat[0] = sH[0].getData();
                            int tmpDLen = sH[0].getDLen();
                            bIO[0].printData(cellOfft, dat[0], tmpDLen, fileName[0]);
                            dat[0] = bIO[0].getHexBytesByOffset(offset[0], rowLen * columnLen);
                            sH[0].setAllData(dat[0]);
                            offset[0] = offset[1];
                            changed[0] = false;
                            sH[0].setDLen(0);
                        } else if (result == 1) {
                            dat[0] = bIO[0].getHexBytesByOffset(offset[0], rowLen * columnLen);
                            sH[0].setAllData(dat[0]);
                            changed[0] = false;
                        }
                    }

                    if ((!maskValue.equals("")) && (!byteSize[0].equals(""))) {
                        UtilByte uB = new UtilByte();
                        int offt1 = offset[1] + rowLen * columnLen;
                        String[] rightData = bIO[0].getHexBytesByOffset(offt1, 7);
                        String[] data = uB.fillInSevenBytes(sH[0].getData(), rightData);

                        int len = Integer.parseInt(byteSize[0]);

                        int[] offsets = {};

                        RegExp rG = new RegExp();

                        boolean isMask = rG.isMask(maskValue[0]);

                        if (isMask) {
                            offsets = bT.getBytesOffsetByMask(data, len, maskValue[0]);
                        } else {
                            BigInteger val = new BigInteger(maskValue[0]);
                            offsets = bT.getByteValueByOffsets(data, len, val);
                        }

                        findedCells = sH[0].getTableCellCoords(offsets);
                        sH[0].setFCells(findedCells);
                    }

                    sH[0].resetSheet(mh);
                    setTable(table, scrollPane, offset[1], sH[0]);
                }
            }
        });

        info = new JButton("инфо");
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = "src/text/instruction.txt";
                StringBuilder content = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                String[] paragraphs = content.toString().split("\n\n");

                JFrame textFrame = new JFrame("Text Content");
                textFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                textFrame.setSize(400, 300);

                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                for (String paragraph : paragraphs) {
                    textArea.append(paragraph + "\n\n");
                }

                JScrollPane scrollPane = new JScrollPane(textArea);
                textFrame.getContentPane().add(scrollPane);

                textFrame.setVisible(true);
            }
        });
        forward.setBounds(710, 550, 300, 40);
        back.setBounds(400, 550, 300, 40);
        info.setBounds(1020, 550, 80, 40);

        widthField.setBounds(400, 350, 350, 20);
        heightField.setBounds(500, 350, 350, 20);
        lenField.setBounds(720, 90, 80, 20);
        dataField.setBounds(720, 150, 150, 20);

        frame.getContentPane().add(scrollPane);
        frame.add(forward);
        frame.add(back);
        frame.add(info);

        frame.setSize(1150, 620);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void setTable(JTable table, JScrollPane scrollPane, int offt, SheetHolder sH) {
        String[] data = null;
        data = sH.getData();
        UtilByte ub = new UtilByte();
        int rowLen = sH.getRowLen();
        // int columnLen = sH.getColumnLen();

        int[][] highlightCells = sH.getHCells();
        int[][] findedCells = sH.getFCells();
        int[][] errorCells = sH.getErCells();

        int tmpColumnLen = (sH.getData().length % sH.getRowLen() == 0) ? sH.getData().length / sH.getRowLen()
                : sH.getData().length / sH.getRowLen() + 1;
        Object[][] tableData = ub.toLabeledArr(rowLen, tmpColumnLen, data, offt); // 16.05.2024

        String[] newColumnNames = new String[rowLen + 1];

        newColumnNames[0] = "off";
        for (int m = 1; m < rowLen + 1; m++) {
            newColumnNames[m] = Integer.toString(m - 1);
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
        table.getColumnModel().getColumn(0).setPreferredWidth(leftColumnWidth);
        ;

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
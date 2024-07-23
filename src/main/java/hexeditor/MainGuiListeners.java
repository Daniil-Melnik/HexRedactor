package main.java.hexeditor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;

public class MainGuiListeners {
    public void searchButtonListener(SheetHolder[] sH, SearchPanel searchPanel, String[] byteSize, int[] offset,
            String[] maskValue, ByteFormatIO[] bIO, HandChng hc) {
        String inputText = searchPanel.inputField.getText();

        byteSize[0] = (String) searchPanel.byteSizeComboBox.getSelectedItem();

        ByteTransform bT = new ByteTransform();

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
    }

    public void editButtonListener(EditTypes selectedAction, EditPanel editPanel, SheetHolder[] sH, int[] offset,
            int[][] highlightCells, ByteFormatIO[] bIO, HandChng hc, String[][] buffer, JTable table) {
        EditBtnActions eBA = new EditBtnActions();
        RegExp rE = new RegExp();
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

            case COPY:
                eBA.btnCopy(table, sH[0], offset, buffer, highlightCells);
                break;

            default:
                break;
        }
    }

    public void changeSizeBtnListener(SheetHolder[] sH, JFrame frame, boolean[] changed, int[] offset, HandChng hc,
            ByteFormatIO[] bIO, String[] fileName, String[][] dat) {
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
    }

    public void forvardButtonListener(ByteFormatIO[] bIO, SheetHolder[] sH, int[] offset, boolean[] changed,
            String[][] dat, String[] fileName, HandChng hc, String[] maskValue, String[] byteSize, int rowLen,
            int columnLen) {
        sH[0].setHCells(new int[0][0]);

        ByteTransform bT = new ByteTransform();

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
        if ((!maskValue[0].isEmpty()) && (!byteSize[0].isEmpty())) {
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
    }

    public void backButtonListener(SheetHolder[] sH, int[] offset, int rowLen, int columnLen, boolean[] changed,
            ByteFormatIO[] bIO, String[][] dat, String[] fileName, HandChng hc, String[] byteSize, String[] maskValue) {
        sH[0].setHCells(new int[0][0]);
        ByteTransform bT = new ByteTransform();

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

        if ((!maskValue[0].isEmpty()) && (!byteSize[0].equals(""))) {
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
    }

    public void infoButtonListener() {
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

    public void tableChangeListener(TableModelEvent e, JTable table, SheetHolder[] sH, JScrollPane scrollPane,
            HandChng hc, int[] offset, int row, int column) {
        RegExp rE = new RegExp();
        HandlerQueue hQ = new HandlerQueue();

        Object value = table.getValueAt(row, column);
        String[] strData = { String.valueOf(value) };

        int rowLen = sH[0].getRowLen();

        String operationType = ChangeTypes.MODIFI_CELL.getValue();

        ChangeHandler chH = new ChangeHandler(operationType, offset[0] + (rowLen * row) + column - 1, 1,
                strData);
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
            hc.showOk("Ошибка заполнения ячеек", msgErrCells);
        }
    }
}

import java.math.BigInteger;

import javax.swing.JTable;
import javax.swing.JTextField;

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

            default:
                break;
        }
    }
}

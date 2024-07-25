package hexeditor.Listeners;

import java.math.BigInteger;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import hexeditor.SheetHolder;
import hexeditor.Panels.BlockSizePanel;
import hexeditor.Panels.TableInfoPanel;
import hexeditor.Utils.ByteFormatIO;
import hexeditor.Utils.ByteTransform;
import hexeditor.Utils.UtilByte;

/**
 * Contains methods for intercepting changes in focus on a cell
 * 
 * @autor DMelnik
 * @version 1.0
 */

public class ChangeFoculListeners {

    /**
     * Contains methods for intercepting changes in focus on a cell
     * 
     * @autor DMelnik
     * @version 1.0
     */
    public void rowFocusListener(ListSelectionEvent e, SheetHolder[] sH, JTable table, int[] prevCol, int[] prevRow,
            int[] offset, ByteFormatIO[] bIO, BlockSizePanel bSP, TableInfoPanel tableInfoPanel) {
        int rowColLen = 4;
        ByteTransform bT = new ByteTransform();
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

    public void columnFocusListener(ListSelectionEvent e, SheetHolder[] sH, JTable table, int[] prevCol, int[] prevRow,
            int[] offset, ByteFormatIO[] bIO, BlockSizePanel bSP, TableInfoPanel tableInfoPanel) {
        ByteTransform bT = new ByteTransform();
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
}

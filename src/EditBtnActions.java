import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Class containing actions for editing cells in a table.
 * <p>
 * This class provides methods to perform various operations such as filling
 * with zeros,
 * removing with zeroing or shifting, pasting with replacement or shifting, and
 * cutting or copying.
 * </p>
 * 
 * @autor DMelnik
 * @version 1.0
 */

public class EditBtnActions {

    /**
     * Fills the selected cells with zeros.
     *
     * @param sH             the SheetHolder object to perform the operation on
     * @param lenField       the JTextField containing the length of the fill
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnFillInZero(SheetHolder sH, JTextField lenField, int[][] highlightCells) {

        int[] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int len = Integer.parseInt(lenField.getText());
        int currOfft = startCoord[0] * rowLen + startCoord[1] - 1; // сдвиг в таблице по координате
        ChangeHandler chH = new ChangeHandler(7, currOfft, len, null);
        sH.makeHandle(chH);

    }

    /**
     * Removes the selected cells and fills with zeros.
     *
     * @param sH             the SheetHolder object to perform the operation on
     * @param offset         the offset for the operation
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnRemoveZero(SheetHolder sH, int[] offset, int[][] highlightCells) {
        int[] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int offt = offset[1] + startCoord[0] * rowLen + startCoord[1] - 1; // поменяно 21.04.2024 offset с 0 на 1
        int highlightLen = highlightCells.length;
        ChangeHandler cHZero = new ChangeHandler(1, offt, highlightLen, null);
        sH.makeHandle(cHZero);
    }

    /**
     * Removes the selected cells with shifting.
     *
     * @param sH             the SheetHolder object to perform the operation on
     * @param offset         the offset for the operation
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnRemoveShift(SheetHolder sH, int[] offset, int[][] highlightCells) {

        int[] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int offt = offset[1] + startCoord[0] * rowLen + startCoord[1] - 1; // поменяно 21.04.2024 offset с 0 на 1
        int highlightLen = highlightCells.length;
        offset[0] = offset[0] + highlightLen;
        ChangeHandler cHShift = new ChangeHandler(2, offt, highlightLen, null);
        sH.makeHandle(cHShift);
    }

    /**
     * Pastes data into the selected cells with shifting.
     *
     * @param sH             the SheetHolder object to perform the operation on
     * @param currData       the data to be pasted
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnPasteShift(SheetHolder sH, String[] currData, int[][] highlightCells) {
        int[] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int currOfft = startCoord[0] * rowLen + startCoord[1] - 1 - 1; // 02.07.2024 сдвиг на 1 по ячейкам
        int len = currData.length;
        ChangeHandler chH = new ChangeHandler(4, currOfft, len, currData);
        sH.makeHandle(chH);
    }

    /**
     * Pastes data into the selected cells with replacement.
     *
     * @param sH             the SheetHolder object to perform the operation on
     * @param currData       the data to be pasted
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnPasteSubst(SheetHolder sH, String[] currData, int[][] highlightCells) {
        int[] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int currOfft = startCoord[0] * rowLen + startCoord[1] - 1 - 1; // сдвиг в таблице по координате

        int len = currData.length;
        ChangeHandler chH = new ChangeHandler(3, currOfft, len, currData);
        sH.makeHandle(chH);
    }

    /**
     * Cuts the selected cells with shifting.
     *
     * @param table          the JTable object to perform the operation on
     * @param sH             the SheetHolder object to perform the operation on
     * @param offset         the offset for the operation
     * @param buffer         the buffer to store the cut data
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnCutShift(JTable table, SheetHolder sH, int[] offset, String[][] buffer, int[][] highlightCells) {
        int[] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int offt = offset[1] + startCoord[0] * rowLen + startCoord[1] - 1;
        int highlightLen = highlightCells[0].length;

        utilByte uB = new utilByte();
        String[] strArr = uB.getValuesOfHighlt(table, highlightCells);
        buffer[0] = strArr;

        ChangeHandler chH = new ChangeHandler(2, offt, highlightLen, null);
        sH.makeHandle(chH);
    }

    /**
     * Copies the selected cells.
     *
     * @param table          the JTable object to perform the operation on
     * @param sH             the SheetHolder object to perform the operation on
     * @param offset         the offset for the operation
     * @param buffer         the buffer to store the copied data
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnCopy(JTable table, SheetHolder sH, int[] offset, String[][] buffer, int[][] highlightCells) {
        utilByte uB = new utilByte();
        String[] strArr = uB.getValuesOfHighlt(table, highlightCells);
        buffer[0] = strArr;
        for (String s : buffer[0]) {
            System.out.print(s + " ");
        }
    }

    /**
     * Cuts the selected cells and fills with zeros.
     *
     * @param table          the JTable object to perform the operation on
     * @param sH             the SheetHolder object to perform the operation on
     * @param offset         the offset for the operation
     * @param buffer         the buffer to store the cut data
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnCutZero(JTable table, SheetHolder sH, int[] offset, String[][] buffer, int[][] highlightCells) {
        int[] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int offt = offset[1] + startCoord[0] * rowLen + startCoord[1] - 1; // поменяно 21.04.2024 offset с 0 на 1
        int highlightLen = highlightCells.length;

        utilByte uB = new utilByte();
        String[] strArr = uB.getValuesOfHighlt(table, highlightCells);
        buffer[0] = strArr;

        ChangeHandler cHZero = new ChangeHandler(1, offt, highlightLen, null);
        sH.makeHandle(cHZero);
    }
}

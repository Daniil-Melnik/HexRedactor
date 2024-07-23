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

        int[] startCoordinate = highlightCells[0];
        int rowLen = sH.getRowLen();
        String operationType = ChangeTypes.INSERT_ZEROS.getValue();

        int len = Integer.parseInt(lenField.getText());
        int currentOffset = startCoordinate[0] * rowLen + startCoordinate[1] - 1; // сдвиг в таблице по координате
        ChangeHandler chH = new ChangeHandler(operationType, currentOffset, len, null);
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
        int[] startCoordinate = highlightCells[0];
        int rowLen = sH.getRowLen();
        String operationType = ChangeTypes.DELETE_WITH_ZEROING.getValue();

        int offt = offset[1] + startCoordinate[0] * rowLen + startCoordinate[1] - 1; // поменяно 21.04.2024 offset с 0
                                                                                     // на 1
        int highlightCellsLen = highlightCells.length;
        ChangeHandler cHZero = new ChangeHandler(operationType, offt, highlightCellsLen, null);
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

        int[] startCoordinate = highlightCells[0];
        int rowLen = sH.getRowLen();
        String operationType = ChangeTypes.DELETE_WITH_SHIFT.getValue();

        int offt = offset[1] + startCoordinate[0] * rowLen + startCoordinate[1] - 1; // поменяно 21.04.2024 offset с 0
                                                                                     // на 1
        int highlightCellsLen = highlightCells.length;
        offset[0] = offset[0] + highlightCellsLen;
        ChangeHandler cHShift = new ChangeHandler(operationType, offt, highlightCellsLen, null);
        sH.makeHandle(cHShift);
    }

    /**
     * Pastes data into the selected cells with shifting.
     *
     * @param sH             the SheetHolder object to perform the operation on
     * @param currentData    the data to be pasted
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnPasteShift(SheetHolder sH, String[] currentData, int[][] highlightCells) {
        int[] startCoordinate = highlightCells[0];
        int rowLen = sH.getRowLen();
        String operationType = ChangeTypes.INSERT_WITH_SHIFT.getValue();

        int currentOffset = startCoordinate[0] * rowLen + startCoordinate[1] - 1 - 1; // 02.07.2024 сдвиг на 1 по
                                                                                      // ячейкам
        int len = currentData.length;
        ChangeHandler chH = new ChangeHandler(operationType, currentOffset, len, currentData);
        sH.makeHandle(chH);
    }

    /**
     * Pastes data into the selected cells with replacement.
     *
     * @param sH             the SheetHolder object to perform the operation on
     * @param currentData    the data to be pasted
     * @param highlightCells the coordinates of the highlighted cells
     */

    public void btnPasteSubst(SheetHolder sH, String[] currentData, int[][] highlightCells) {
        int[] startCoordinate = highlightCells[0];
        int rowLen = sH.getRowLen();
        String operationType = ChangeTypes.INSERT_WITH_REPLACE.getValue();

        int currentOffset = startCoordinate[0] * rowLen + startCoordinate[1] - 1 - 1; // сдвиг в таблице по координате

        int len = currentData.length;
        ChangeHandler chH = new ChangeHandler(operationType, currentOffset, len, currentData);
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
        int[] startCoordinate = highlightCells[0];
        int rowLen = sH.getRowLen();
        String operationType = ChangeTypes.DELETE_WITH_SHIFT.getValue();

        int offt = offset[1] + startCoordinate[0] * rowLen + startCoordinate[1] - 1;
        int highlightCellsLen = highlightCells[0].length;

        UtilByte uB = new UtilByte();
        String[] strArr = uB.getValuesOfHighlt(table, highlightCells);
        buffer[0] = strArr;

        ChangeHandler chH = new ChangeHandler(operationType, offt, highlightCellsLen, null);
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
        UtilByte uB = new UtilByte();
        String[] strArr = uB.getValuesOfHighlt(table, highlightCells);
        buffer[0] = strArr;
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
        int[] startCoordinate = highlightCells[0];
        int rowLen = sH.getRowLen();
        String operationType = ChangeTypes.DELETE_WITH_ZEROING.getValue();

        int offt = offset[1] + startCoordinate[0] * rowLen + startCoordinate[1] - 1; // поменяно 21.04.2024 offset с 0
                                                                                     // на 1
        int highlightCellsLen = highlightCells.length;

        UtilByte uB = new UtilByte();
        String[] strArr = uB.getValuesOfHighlt(table, highlightCells);
        buffer[0] = strArr;

        ChangeHandler cHZero = new ChangeHandler(operationType, offt, highlightCellsLen, null);
        sH.makeHandle(cHZero);
    }
}

import java.io.IOException;

/**
 * Stores information about the current page and is responsible for the initial
 * change of page data.
 *
 * @see java.io.IOException
 * @author DMelnik
 * @version 1.0
 */

public class SheetHolder {
    private String[] data;
    private String[] reserveData;
    private String fName;
    private int rowLen;
    private int columnLen;
    private int currentRow;
    private int currentColumn;
    private int dLen;
    private int offt;
    private int[][] hCells;
    private int[][] fCells;
    private int[][] erCells;

    /**
     * Setter for rowLen attribute
     *
     * @param width new number of cells per row
     */

    public void setRowLen(int width) {
        this.rowLen = width;
    }

    /**
     * Setter for offt attribute
     *
     * @param offt new offset for page in whole data
     */

    public void setOfft(int offt) {
        this.offt = offt;
    }

    /**
     * Setter for columnLen attribute
     *
     * @param height new number of cells per column
     */

    public void setColumnLen(int height) {
        this.columnLen = height;
    }

    /**
     * Setter for data attribute
     *
     * @param data array of string interpretations of bytes
     */

    public void setData(String[] data) {
        this.data = data;
    }

    /**
     * Setter for reserveData attribute
     *
     * @param data array of string interpretations of bytes
     */

    public void setReserveData(String[] data) {
        this.reserveData = data;
    }

    /**
     * Setter for fName attribute
     *
     * @param fName new name of file with data (work file)
     */

    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * Setter for hCells attribute
     *
     * @param hCells array of highlited cells coordinates
     */

    public void setHCells(int[][] hCells) {
        this.hCells = hCells;
    }

    /**
     * Getter for hCells attribute
     *
     * @return array of highlited cells coordinates
     */

    public int[][] getHCells() {
        return this.hCells;
    }

    /**
     * Setter for data and reserveData attributes
     *
     * @param data array of string interpretation of bytes
     */

    public void setAllData(String[] data) {
        this.reserveData = data;
        this.data = data;
    }

    /**
     * Setter for currentRow attribute
     *
     * @param currentRow number of row containing the cell with current focus
     */

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    /**
     * Setter for currentColumn attribute
     *
     * @param currentColumn number of column containing the cell with current focus
     */

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    /**
     * Getter for currentRow attribute
     *
     * @return number of row containing the cell with current focus
     */

    public int getCurrentRow() {
        return this.currentRow;
    }

    /**
     * Getter for currentColumn attribute
     *
     * @return number of column containing the cell with current focus
     */

    public int getCurrentColumn() {
        return this.currentColumn;
    }

    /**
     * Getter for data attribute
     *
     * @return string interpritation of data from current page
     */

    public String[] getData() {
        return this.data;
    }

    /**
     * Getter for reserveData attribute
     *
     * @return string interpritation of cashed data from current page
     */

    public String[] getReserveData() {
        return this.reserveData;
    }

    /**
     * Getter for rowLen attribute
     *
     * @return number of cells in table row
     */

    public int getRowLen() {
        return this.rowLen;
    }

    /**
     * Getter for columnLen attribute
     *
     * @return number of cells in table column
     */

    public int getColumnLen() {
        return this.columnLen;
    }

    /**
     * Getter for columnLen attribute
     *
     * @return number of cells in table column
     */

    public int getDLen() {
        return this.dLen;
    }

    /**
     * Getter fCells attribute
     *
     * @return array of coordinates of finded cells on page
     */

    public int[][] getFCells() {
        return this.fCells;
    }

    /**
     * Getter fCells attribute
     *
     * @return array of coordinates of finded cells on page
     */

    public void setFCells(int[][] fCells) {
        this.fCells = fCells;
    }

    /**
     * Getter erCells attribute
     *
     * @return array of coordinates of error cells on page
     */

    public int[][] getErCells() {
        return this.erCells;
    }

    /**
     * Setter for erCells attribute
     *
     * @param erCells array of coordinates table cells with errors
     */

    public void setErCells(int[][] erCells) {
        this.erCells = erCells;
    }

    /**
     * Constructs SheetHolder object for current Hex Editor session
     *
     * @param fName file name for file used
     */

    public SheetHolder(String fName) {
        this.offt = 0;
        this.fName = fName;
        this.hCells = new int[0][0];
        this.fCells = new int[0][0];
        this.erCells = new int[0][0];
    }

    /**
     * Getter for fName attribute
     * 
     * @return string, name of file used
     */

    public String getfName() {
        return this.fName;
    }

    /**
     * Gives cell coordinates in table by offsets in file
     * 
     * @param offts array of offsets in file
     * @return array of coordinates in table
     */

    public int[][] getTableCellCoords(int[] offts) {
        int offtLen = offts.length;
        int[][] res = new int[offtLen][2];
        for (int i = 0; i < offtLen; i++) {
            int singleOfft = offts[i];
            res[i][0] = singleOfft / rowLen;
            res[i][1] = singleOfft % rowLen + 1;
        }
        return res;
    }

    /**
     * Setter for dLen attribute
     * 
     * @param dLen new delta length
     */

    public void setDLen(int dLen) {
        this.dLen = dLen;
    }

    /**
     * resets table sheet
     * 
     * @param mH mouse event object
     */

    public void resetSheet(MouseHig mH) {
        this.setErCells(new int[0][0]);
        this.setHCells(new int[0][0]);

        mH.setCond((byte) 0);
    }

    /**
     * Remiove stars "*" frome the table sheet
     */

    public void clearStarsOnSheet() {
        utilByte uB = new utilByte();
        String[] newData = uB.clearStars(this.data);
        this.data = newData;
    }

    /**
     * Adds stars "*" to emty cells on the table sheet
     */

    public void fillInStarsOnSheet() {
        utilByte uB = new utilByte();

        int emptyCellDataShift;
        if (this.data.length % this.rowLen != 0) {
            emptyCellDataShift = this.rowLen - this.data.length % this.rowLen;
        } else
            emptyCellDataShift = 0;

        this.data = uB.fillInStars(this.data, emptyCellDataShift);
    }

    /**
     * Сhecks for data buffer overflow
     * 
     * @param addLen number of bytes to be added to the current table sheet
     * 
     * @return boolean value indicating a buffer overflow
     */

    public boolean isEmptyVolume(int addLen) {
        boolean res = true;
        int currLen = this.data.length;
        int MAX_VOLUME = 1048577;
        res = currLen + addLen < MAX_VOLUME;
        return res;
    }

    /**
     * Makes a change to the current table page according to the type of change
     * 
     * @param chH change object
     */

    public void makeHandle(ChangeHandler chH) {
        int index = chH.getOfft() % (this.rowLen * this.columnLen);
        int len = 0;
        int currOfft = 0;
        utilByte uB = new utilByte();
        ByteIO bIO = new ByteIO(this.fName);
        switch (chH.getType()) {
            case 0:
                this.data[index] = chH.getData()[0];
                break;

            case 1:
                len = chH.getLen();
                currOfft = chH.getOfft() - this.offt;
                uB.removeFromArrZero(this.data, len, currOfft);
                break;

            case 2:

                try {
                    this.clearStarsOnSheet();
                    len = chH.getLen();
                    currOfft = chH.getOfft() - this.offt;
                    int newOfft = this.offt + this.rowLen * this.columnLen + this.dLen;

                    if (newOfft < bIO.getFileLength(this.fName)) {
                        String[] leftData = uB.removeFromArr(this.data, len, currOfft);
                        String[] rightData = bIO.getHexBytesOfft(newOfft, len);
                        this.data = uB.concatArrs(leftData, rightData);
                        this.dLen += len;
                        this.fillInStarsOnSheet();
                    }
                } catch (IOException e) {
                    System.err.println("An error occurred while trying to get the file length: " + e.getMessage());
                }

                break;

            case 3:
                this.clearStarsOnSheet();
                int offt = chH.getOfft();
                String[] newData = chH.getData();
                this.data = uB.addDataSubst(this.data, newData, offt);
                this.fillInStarsOnSheet();
                break;

            case 4:
                this.clearStarsOnSheet();
                len = chH.getLen();
                offt = chH.getOfft();
                String[] newDataShift = chH.getData();
                String[] tempDataShift = uB.addDataShift(this.data, newDataShift, offt);
                this.data = tempDataShift;
                this.fillInStarsOnSheet();
                break;

            case 7:
                this.clearStarsOnSheet();
                len = chH.getLen();
                offt = chH.getOfft();
                this.data = uB.fillInZeros(this.data, offt, len);
                this.fillInStarsOnSheet();
            default:
                break;
        }
    }
}

package main.java.hexeditor.Listeners;

/**
 * Utility class for mouse operations
 * Purpose: Automatically selects cells in a table based on mouse interactions
 *
 * @see MouseListener
 * @author DMelnik
 */
public class MouseListener {
    private byte condition;
    private int[][] coordinate;

    /**
     * Constructor for MouseHig class
     */

    public MouseListener() {
        coordinate = new int[2][2];
        condition = 0;
    }

    /**
     * Sets the condition for selecting coordinates
     * 
     * @param newCondition the new condition byte value
     */

    public void setCond(byte newCondition) {
        this.condition = newCondition;
    }

    /**
     * Adds coordinates to the selection based on the current condition
     * 
     * @param coordinate the coordinates to add
     */

    public void addCoord(int[] coordinate) {
        switch (this.condition) {
            case 0:
                this.coordinate[0] = coordinate;
                this.condition = 1;
                break;

            case 1:
                this.coordinate[1] = coordinate;
                this.condition = 2;
                break;

            case 2:
                this.coordinate = new int[2][2];
                this.condition = 0;
                break;

            default:
                break;
        }
    }

    /**
     * Retrieves the current condition
     * 
     * @return the current condition byte value
     */

    public byte getCond() {
        return this.condition;
    }

    /**
     * Retrieves the selected coordinates
     * 
     * @return the selected coordinates as a 2D array
     */

    public int[][] getCoord() {
        return this.coordinate;
    }

    /**
     * Ensures the coordinates are valid by swapping if necessary
     */

    private void validCoord() {
        if ((this.coordinate[0][0] > this.coordinate[1][0])
                || (this.coordinate[0][0] == this.coordinate[1][0] && this.coordinate[0][1] > this.coordinate[1][1])) {
            int[] dCoord = this.coordinate[0];
            this.coordinate[0] = this.coordinate[1];
            this.coordinate[1] = dCoord;
        }
    }

    /**
     * Retrieves all selected coordinates considering row lengths
     * 
     * @param len the length of each row
     * @return an array of all selected coordinates
     */

    public int[][] getFullCoords(int len) {

        validCoord();

        /*
         * Consider the top and bottom rows separately
         * Treat the rest as complete cells
         */
        int nFullRows = this.coordinate[1][0] - this.coordinate[0][0] - 1;
        int nExtraUp = len - this.coordinate[0][1] + 1;
        int nExtraBottom = this.coordinate[1][1];
        int nExtraCells = nExtraUp + nExtraBottom;

        int nAllCells = nFullRows * len + nExtraCells;

        int[][] res = new int[nAllCells][2];

        int nRes = 0;

        for (int i = this.coordinate[0][0] * (len + 1) + this.coordinate[0][1]; i <= this.coordinate[1][0] * (len + 1)
                + this.coordinate[1][1]; i++) {
            if (i % (len + 1) != 0) {
                int[] qT = new int[2];
                qT[0] = i / (len + 1);
                qT[1] = i % (len + 1);
                res[nRes] = qT;
                nRes += 1;
            }
        }
        return res;
    }
}

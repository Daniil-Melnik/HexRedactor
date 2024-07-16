/**
* Utility class for mouse operations
* Purpose: Automatically selects cells in a table based on mouse interactions
*
* @see MouseHig
* @author OpenAI
*/
public class MouseHig {
    private byte cond;
    private int [][] coord;

    /**
     * Constructor for MouseHig class
     */

    public MouseHig(){
        coord = new int [2][2];
        cond = 0;
    }

    /**
     * Sets the condition for selecting coordinates
     * 
     * @param newCond the new condition byte value
     */

    public void setCond(byte newCond){
        this.cond = newCond;
    }

    /**
     * Adds coordinates to the selection based on the current condition
     * 
     * @param coord the coordinates to add
     */

    public void addCoord(int [] coord){
        switch (this.cond) {
            case 0:
                this.coord[0] = coord;
                this.cond = 1;
                break;

            case 1:
                this.coord[1] = coord;
                this.cond = 2;
                break;
            
            case 2:
                this.coord = new int [2][2];
                this.cond = 0;
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

    public byte getCond(){
        return this.cond;
    }

    /**
     * Retrieves the selected coordinates
     * 
     * @return the selected coordinates as a 2D array
     */

    public int [][] getCoord(){
        return this.coord;
    }

    /**
     * Ensures the coordinates are valid by swapping if necessary
     */

    private void validCoord(){
        if ((this.coord[0][0] > this.coord[1][0]) || (this.coord[0][0] == this.coord[1][0] && this.coord[0][1] > this.coord[1][1])){
            int [] dCoord = this.coord[0];
            this.coord[0] = this.coord[1];
            this.coord[1] = dCoord; 
        }
    }

    /**
     * Retrieves all selected coordinates considering row lengths
     * 
     * @param len the length of each row
     * @return an array of all selected coordinates
     */

    public int [][] getFullCoords(int len){

        validCoord();

        /*
         * Consider the top and bottom rows separately
         * Treat the rest as complete cells
         */
        int nFullRows = this.coord[1][0] - this.coord[0][0] - 1;
        int nExtraUp = len - this.coord[0][1] + 1;
        int nExtraBottom = this.coord[1][1];
        int nExtraCells = nExtraUp + nExtraBottom;

        int nAllCells = nFullRows * len + nExtraCells;

        int [][] res = new int [nAllCells][2];

        int nRes = 0;

        for (int i = this.coord[0][0] * (len + 1) + this.coord[0][1]; i <= this.coord[1][0] * (len + 1) + this.coord[1][1]; i++){
            if (i % (len + 1) != 0){   
                int [] qT = new int [2];
                qT[0] = i / (len + 1);
                qT[1] = i % (len + 1);
                res[nRes] = qT;
                nRes += 1;
            }
        }
        return res;
    }
}

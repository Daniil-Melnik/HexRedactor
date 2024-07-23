package main.java.hexeditor;

/**
 * Class representing an operation on data.
 * <p>
 * This class encapsulates the details of different types of data operations
 * such as modification, deletion, and insertion.
 * </p>
 * <p>
 * Operation types:
 * <ul>
 * <li>0 - Modify a single cell</li>
 * <li>1 - Delete with zeroing</li>
 * <li>2 - Delete with shifting</li>
 * <li>3 - Insert with replacement</li>
 * <li>4 - Insert with shifting</li>
 * <li>7 - Insert zeros</li>
 * </ul>
 * </p>
 * 
 * @autor DMelnik
 * @version 1.0
 */
public class ChangeHandler {
    private String type;
    private String[] changedCells;
    private int offset;
    private int len;

    /**
     * Constructs a ChangeHandler with specified type, offset, length, and data.
     * 
     * @param type   the type of operation
     * @param offset the offset for the operation
     * @param len    the length affected by the operation
     * @param data   the data involved in the operation
     */

    public ChangeHandler(String type, int offset, int len, String[] data) {
        this.type = type;
        this.offset = offset;
        this.len = len;
        this.changedCells = data;
    }

    /**
     * Gets the offset for the operation.
     * 
     * @return the offset
     */

    public int getOffset() {
        return this.offset;
    }

    /**
     * Gets the length affected by the operation.
     * 
     * @return the length
     */

    public int getLen() {
        return this.len;
    }

    /**
     * Gets the type of operation.
     * 
     * @return the type of operation
     */

    public String getOperationType() {
        return this.type;
    }

    /**
     * Gets the data involved in the operation.
     * 
     * @return the data as a String array
     */

    public String[] getData() {
        return this.changedCells;
    }
}

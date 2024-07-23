package main.java.hexeditor.Renderers;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;

/**
 * HighLightRenderer class
 * Purpose: Custom table cell renderer to highlight cells based on different
 * criteria:
 * 1) Highlighted cells
 * 2) Error cells
 * 3) Found cells
 * 
 * <p>
 * Uses different background colors for each type of highlighting.
 * </p>
 * 
 * @see DefaultTableCellRenderer
 * @author DMelnik
 */

public class HighLightRenderer extends DefaultTableCellRenderer {
    private final int[][] highlightCells;
    private final int[][] errorCells;
    private final int[][] findedCells;

    /**
     * Constructor for HighLightRenderer class
     * 
     * @param highlightCells 2D array of coordinates for highlighted cells
     * @param errorCells     2D array of coordinates for error cells
     * @param findedCells    2D array of coordinates for found cells
     */

    public HighLightRenderer(int[][] highlightCells, int[][] errorCells, int[][] findedCells) {
        this.highlightCells = highlightCells;
        this.errorCells = errorCells;
        this.findedCells = findedCells;
    }

    /**
     * Overrides the default cell renderer to customize cell appearance based on
     * specific criteria.
     * 
     * @param table      the JTable object
     * @param value      the value to assign to the cell
     * @param isSelected whether or not the cell is selected
     * @param hasFocus   whether or not the cell has focus
     * @param row        the row index of the cell being rendered
     * @param column     the column index of the cell being rendered
     * @return the component used for rendering the cell
     */

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (checkCoordinate(errorCells, row, column)) {
            c.setBackground(Color.RED);
        } else if (checkCoordinate(highlightCells, row, column)) {
            c.setBackground(Color.GREEN);
        } else if (checkCoordinate(findedCells, row, column)) {
            c.setBackground(Color.GRAY);
        } else {
            c.setBackground(Color.WHITE); // Сбрасываем фон обратно на белый, если ячейка не выделена
        }
        return c;
    }

    private boolean checkCoordinate(int[][] coordinateArray, int row, int column) {
        return (Arrays.stream(coordinateArray).anyMatch(coord -> coord[0] == row && coord[1] == column));
    }
}

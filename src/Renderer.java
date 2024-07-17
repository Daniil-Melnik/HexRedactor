import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Renderer class that extends DefaultTableCellRenderer to customize cell
 * rendering in a JTable.
 * It uses a HighLightRenderer to handle cell highlighting based on specified
 * conditions.
 *
 * @see HighLightRenderer
 * @see DefaultTableCellRenderer
 * @see JTable
 * @see JLabel
 * @author [Your Name]
 * @version 1.0
 */

public class Renderer extends DefaultTableCellRenderer {
    private HighLightRenderer highlightRenderer;

    /**
     * Constructs a Renderer with specified cells to highlight, cells with errors,
     * and cells found by some criteria.
     *
     * @param highlightCells the cells to be highlighted
     * @param errorCells     the cells that contain errors
     * @param findedCells    the cells that match some find criteria
     */

    public Renderer(int[][] highlightCells, int[][] errorCells, int[][] findedCells) {

        this.highlightRenderer = new HighLightRenderer(highlightCells, errorCells, findedCells);
    }

    /**
     * Returns the component used for drawing the cell. This method is used to
     * configure the renderer appropriately
     * before drawing.
     *
     * @param table      the JTable that uses this renderer
     * @param value      the value to assign to the cell at [row, column]
     * @param isSelected true if the cell is selected
     * @param hasFocus   true if the cell has focus
     * @param row        the row of the cell to render
     * @param column     the column of the cell to render
     * @return the component used for drawing the cell
     */

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (column == 0 && value instanceof JLabel) {
            return (JLabel) value;
        } else {
            return highlightRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}

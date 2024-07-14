import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Renderer extends DefaultTableCellRenderer {
    private HighLightRenderer highlightRenderer;

    public Renderer(int[][] highlightCells, int[][] errorCells, int[][] findedCells) {
        
        this.highlightRenderer = new HighLightRenderer(highlightCells, errorCells, findedCells);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (column == 0 && value instanceof JLabel) {
            return (JLabel) value;
        } else {
            return highlightRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}

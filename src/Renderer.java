import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Renderer extends DefaultTableCellRenderer {
    private HighLightRenderer highlightRenderer;

    public Renderer(int[][] highlightCells, int[][] errorCells) {
        
        this.highlightRenderer = new HighLightRenderer(highlightCells, errorCells);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Если столбец - 0, и значение - JLabel, возвращаем его без изменений
        if (column == 0 && value instanceof JLabel) {
            return (JLabel) value;
        } else {
            // Иначе, используем HighlightRenderer для выделения ячеек
            return highlightRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}

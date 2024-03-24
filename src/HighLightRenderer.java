import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;

public class HighLightRenderer extends DefaultTableCellRenderer {
    private int[][] highlightCells;

    public HighLightRenderer(int[][] highlightCells) {
        this.highlightCells = highlightCells;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Проверяем, содержится ли текущая ячейка в массиве highlightCells
        if (Arrays.stream(highlightCells).anyMatch(coord -> coord[0] == row && coord[1] == column)) {
            c.setBackground(Color.GREEN);
        } else {
            c.setBackground(Color.WHITE); // Сбрасываем фон обратно на белый, если ячейка не выделена
        }
        return c;
    }
}

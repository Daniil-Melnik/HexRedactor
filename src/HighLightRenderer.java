import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;

public class HighLightRenderer extends DefaultTableCellRenderer {
    private final int[][] highlightCells;
    private final int[][] errorCells;

    public HighLightRenderer(int[][] highlightCells, int[][] errorCells) {
        this.highlightCells = highlightCells;
        this.errorCells = errorCells;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (Arrays.stream(errorCells).anyMatch(coord -> coord[0] == row && coord[1] == column)) {
            c.setBackground(Color.RED);
            System.out.println("ПРОБЛЕМНАЯ: ошибка");
        }
        else if (Arrays.stream(highlightCells).anyMatch(coord -> coord[0] == row && coord[1] == column)){
            c.setBackground(Color.GREEN);
            System.out.println("ПРОБЛЕМНАЯ: выделение");
        }
        else{
            c.setBackground(Color.WHITE); // Сбрасываем фон обратно на белый, если ячейка не выделена
        }
        return c;
    }
}

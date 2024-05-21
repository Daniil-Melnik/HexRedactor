import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;

/*
* Класс-рендерер таблицы
* Назначение: подсветка ячеек таблицы при
* 1) Выделении
* 2) Поиске
* 3) Ошибочных значениях
* */

public class HighLightRenderer extends DefaultTableCellRenderer {
    private final int[][] highlightCells;
    private final int[][] errorCells;
    private final int[][] findedCells;

    public HighLightRenderer(int[][] highlightCells, int[][] errorCells, int[][] findedCells) {
        this.highlightCells = highlightCells;
        this.errorCells = errorCells;
        this.findedCells = findedCells;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (Arrays.stream(errorCells).anyMatch(coord -> coord[0] == row && coord[1] == column)) {
            c.setBackground(Color.RED);
        }
        else if (Arrays.stream(highlightCells).anyMatch(coord -> coord[0] == row && coord[1] == column)){
            c.setBackground(Color.GREEN);
        }
        else if (Arrays.stream(findedCells).anyMatch(coord -> coord[0] == row && coord[1] == column)){
            c.setBackground(Color.GRAY);
        }
        else{
            c.setBackground(Color.WHITE); // Сбрасываем фон обратно на белый, если ячейка не выделена
        }
        return c;
    }
}

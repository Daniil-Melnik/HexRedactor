import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.Arrays;

public class HighlightTableCells {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Создаем JFrame
            JFrame frame = new JFrame("Highlight Cells Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Создаем данные для таблицы
            Object[][] data = {
                    {"1", "One", "I"},
                    {"2", "Two", "II"},
                    {"3", "Three", "III"},
                    {"4", "Four", "IV"}
            };
            // Создаем заголовки столбцов
            String[] columnNames = {"ID", "Name", "Roman Numeral"};

            // Создаем JTable с данными
            JTable table = new JTable(data, columnNames);

            // Устанавливаем высоту строки
            table.setRowHeight(30);

            // Получаем модель таблицы
            //DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Массив координат ячеек для выделения
            int[][] highlightCells = {{0, 0}, {1, 1}, {2, 2}, {3, 0}};

            // Создаем ячейковый рендерер с желтым фоном
            DefaultTableCellRenderer highlightRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    // Проверяем, содержится ли текущая ячейка в массиве highlightCells
                    if (Arrays.stream(highlightCells).anyMatch(coord -> coord[0] == row && coord[1] == column)) {
                        c.setBackground(Color.YELLOW);
                    } else {
                        c.setBackground(Color.WHITE); // Сбрасываем фон обратно на белый, если ячейка не выделена
                    }
                    return c;
                }
            };

            // Устанавливаем рендерер для всех столбцов
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(highlightRenderer);
            }

            // Добавляем таблицу на панель содержимого
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Устанавливаем размеры окна и делаем его видимым
            frame.setSize(400, 300);
            frame.setVisible(true);
        });
    }
}

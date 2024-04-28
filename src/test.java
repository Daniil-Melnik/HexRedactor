import javax.swing.*;
import java.awt.*;

public class test {

    public static void main(String[] args) {
        // Создаем JFrame
        JFrame frame = new JFrame("JTable Focus Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Создаем массив данных для таблицы
        Object[][] data = {
            {"Alice", 30, "Engineer"},
            {"Bob", 35, "Designer"},
            {"Charlie", 25, "Developer"}
        };

        // Создаем массив заголовков столбцов
        Object[] columnNames = {"Name", "Age", "Occupation"};

        // Создаем JTable с данными и заголовками столбцов
        JTable table = new JTable(data, columnNames);

        // Создаем JScrollPane для добавления таблицы
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Устанавливаем фокус на ячейку в строке 1 и столбце 1 (вторая строка, второй столбец)
        int rowIndex = 1;
        int columnIndex = 1;
        table.changeSelection(rowIndex, columnIndex, false, false);

        // Показываем JFrame
        frame.setVisible(true);
    }
}

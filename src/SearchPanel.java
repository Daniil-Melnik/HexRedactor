import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPanel extends JPanel {
    // Компоненты панели
    JComboBox<String> byteSizeComboBox;
    private JRadioButton searchByMaskRadioButton;
    private JRadioButton searchByValueRadioButton;
    JTextField inputField;
    private JButton findButton;
    private JLabel inputLabel; // Надпись для поля ввода

    // Конструктор панели
    public SearchPanel() {
        setLayout(new GridLayout(0, 1)); // Устанавливаем компоновщик панели
        
        // Создание выпадающего меню для выбора количества байтов
        String[] byteOptions = {"1 байт", "2 байта", "4 байта", "8 байт"};
        byteSizeComboBox = new JComboBox<>(byteOptions);
        add(new JLabel("Выберите количество байтов:"));
        add(byteSizeComboBox);
        
        // Создание радио-кнопок для выбора между поиском по маске или по значению
        searchByMaskRadioButton = new JRadioButton("По маске");
        searchByValueRadioButton = new JRadioButton("По значению");
        
        // Группировка радио-кнопок
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(searchByMaskRadioButton);
        buttonGroup.add(searchByValueRadioButton);
        
        // Добавление радио-кнопок на панель
        add(new JLabel("Выберите метод поиска:"));
        add(searchByMaskRadioButton);
        add(searchByValueRadioButton);
        
        // Создание поля для ввода и надписи
        inputLabel = new JLabel("Введите значение:");
        inputField = new JTextField();
        add(inputLabel);
        add(inputField);
        
        // Создание кнопки "Найти"
        findButton = new JButton("Найти");
        add(findButton);
        
        // Добавление обработчиков событий
        searchByMaskRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Установить надпись для маски
                inputLabel.setText("Введите маску:");
            }
        });
        
        searchByValueRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Установить надпись для значения
                inputLabel.setText("Введите значение:");
            }
        });
        
        // Установите радио-кнопку по значению по умолчанию и обновите надпись
        searchByValueRadioButton.setSelected(true);
        inputLabel.setText("Введите значение:");
    }

    // Метод для добавления слушателя для кнопки "Найти"
    public void addSearchButtonListener(ActionListener listener) {
        findButton.addActionListener(listener);
    }
}

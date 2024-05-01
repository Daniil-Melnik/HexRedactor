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
    private JLabel inputLabel, sizeLabel, methodLabel, mainLabel; // Надпись для поля ввода

    // Конструктор панели
    public SearchPanel() {

        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font15 = new Font("Arial", Font.PLAIN, 15);

        // Установить компоновщик панели в null
        setLayout(null);
        setBorder(new RoundedBorder(10));

        mainLabel = new JLabel("Поиск");
        mainLabel.setBounds(170, 5, 100, 30);
        mainLabel.setFont(font20);
        add(mainLabel);
        
        // Создание выпадающего меню для выбора количества байтов
        String[] byteOptions = {"1", "2", "4", "8"};
        byteSizeComboBox = new JComboBox<>(byteOptions);
        byteSizeComboBox.setBounds(110, 40, 60, 20);

        sizeLabel = new JLabel("Кол-во байт:");
        sizeLabel.setFont(font15);
        sizeLabel.setBounds(10, 40, 100, 20);
        add(sizeLabel);
        add(byteSizeComboBox);
        
        // Создание радио-кнопок для выбора между поиском по маске или по значению
        searchByMaskRadioButton = new JRadioButton("По маске");
        searchByValueRadioButton = new JRadioButton("По значению");

        searchByMaskRadioButton.setFont(font15);
        searchByValueRadioButton.setFont(font15);

        searchByMaskRadioButton.setBounds(110, 70, 120, 20);
        searchByValueRadioButton.setBounds(230, 70, 120, 20);


        
        // Группировка радио-кнопок
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(searchByMaskRadioButton);
        buttonGroup.add(searchByValueRadioButton);
        
        // Добавление радио-кнопок на панель
        methodLabel = new JLabel("Метод:");
        methodLabel.setBounds(48, 68, 50, 20);
        methodLabel.setFont(font15);
        add(methodLabel);

        add(searchByMaskRadioButton);
        add(searchByValueRadioButton);
        
        // Создание поля для ввода и надписи
        inputLabel = new JLabel("Значение:");
        inputLabel.setFont(font15);
        inputLabel.setBounds(26, 95, 70, 20);

        inputField = new JTextField();
        inputField.setBounds(110, 95, 232, 20);

        add(inputLabel);
        add(inputField);
        
        // Создание кнопки "Найти"
        findButton = new JButton("Найти");
        findButton.setFont(font15);
        findButton.setBounds(1, 125, 398, 24);
        add(findButton);
        
        // Добавление обработчиков событий
        searchByMaskRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Установить надпись для маски
                inputLabel.setText("Маска:");
                inputLabel.setBounds(48, 95, 70, 20);

            }
        });
        
        searchByValueRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Установить надпись для значения
                inputLabel.setText("Значение:");
                inputLabel.setBounds(26, 95, 70, 20);
            }
        });
        
        // Установите радио-кнопку по значению по умолчанию и обновите надпись
        searchByValueRadioButton.setSelected(true);
        inputLabel.setText("Значение:");
    }

    // Метод для добавления слушателя для кнопки "Найти"
    public void addSearchButtonListener(ActionListener listener) {
        findButton.addActionListener(listener);
    }
}

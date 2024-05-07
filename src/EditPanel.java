import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPanel extends JPanel {
    private JComboBox<String> comboBox;
    private JPanel dynamicContent;
    private JButton executeButton;
    private JTextField valueField;
    private JRadioButton bufferRadio;
    private JRadioButton valueRadio;

    public EditPanel() {
        setLayout(new BorderLayout());

        // Создаем выпадающее меню
        comboBox = new JComboBox<>(new String[]{"Удалить", "Вырезать", "Вставить", "Вставить нули"});
        comboBox.addActionListener(new ComboBoxListener());
        add(comboBox, BorderLayout.NORTH);

        // Создаем динамическую панель для переключаемых элементов
        dynamicContent = new JPanel();
        add(dynamicContent, BorderLayout.CENTER);

        // Создаем кнопку "Выполнить"
        executeButton = new JButton("Выполнить");
        executeButton.addActionListener(new ExecuteButtonListener());
        add(executeButton, BorderLayout.SOUTH);

        // Создаем поле для ввода заранее
        valueField = new JTextField(10);

        // Инициализируем динамическую панель
        updateDynamicPanel();
    }

    private void updateDynamicPanel() {
        dynamicContent.removeAll();
        String selectedItem = (String) comboBox.getSelectedItem();

        if ("Удалить".equals(selectedItem) || "Вырезать".equals(selectedItem)) {
            JRadioButton shiftRadio = new JRadioButton("со сдвигом");
            JRadioButton zeroRadio = new JRadioButton("с обнулением");
            ButtonGroup group = new ButtonGroup();
            group.add(shiftRadio);
            group.add(zeroRadio);

            dynamicContent.add(shiftRadio);
            dynamicContent.add(zeroRadio);

        } else if ("Вставить".equals(selectedItem)) {
            JRadioButton replaceRadio = new JRadioButton("с замещением");
            JRadioButton shiftRadio = new JRadioButton("со сдвигом");
            ButtonGroup actionGroup = new ButtonGroup();
            actionGroup.add(replaceRadio);
            actionGroup.add(shiftRadio);

            dynamicContent.add(replaceRadio);
            dynamicContent.add(shiftRadio);

            bufferRadio = new JRadioButton("из буфера");
            valueRadio = new JRadioButton("задать значение");
            ButtonGroup sourceGroup = new ButtonGroup();
            sourceGroup.add(bufferRadio);
            sourceGroup.add(valueRadio);

            dynamicContent.add(bufferRadio);
            dynamicContent.add(valueRadio);

            // Добавляем действие при выборе radio-кнопок
            bufferRadio.addActionListener(new RadioActionListener());
            valueRadio.addActionListener(new RadioActionListener());

        } else if ("Вставить нули".equals(selectedItem)) {
            JTextField zeroField = new JTextField(10);
            dynamicContent.add(zeroField);
        }

        dynamicContent.revalidate();
        dynamicContent.repaint();
    }

    private class RadioActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == valueRadio && valueRadio.isSelected()) {
                dynamicContent.add(valueField);
            } else if (e.getSource() == bufferRadio && bufferRadio.isSelected()) {
                dynamicContent.remove(valueField);
            }
            dynamicContent.revalidate();
            dynamicContent.repaint();
        }
    }

    private class ComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateDynamicPanel();
        }
    }

    private class ExecuteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(EditPanel.this, "Действие выполнено!");
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Тестовое окно");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        EditPanel customPanel = new EditPanel();
        frame.add(customPanel);

        frame.setVisible(true);
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPanel extends JPanel {
    JComboBox<String> comboBox, valueBuffer;
    private JPanel dynamicContent;
    private JLabel mainLabel, opLabel, zeroLabel;
    private JButton executeButton;
    JTextField valueField, zeroField;

    public EditPanel() {
        setLayout(null);
        setBorder(new RoundedBorder(10));

        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font15 = new Font("Arial", Font.PLAIN, 15);

        mainLabel = new JLabel("Редактирование");
        mainLabel.setFont(font20);
        mainLabel.setBounds(125, 10, 200, 30);
        add(mainLabel);

        opLabel = new JLabel("Операция: ");
        opLabel.setFont(font15);
        opLabel.setBounds(10, 52, 80, 20);
        add(opLabel);

        // Создаем выпадающее меню
        comboBox = new JComboBox<>(new String[]{"Удалить", "Вырезать", "Вставить", "Вставить нули"});
        comboBox.addActionListener(new ComboBoxListener());
        comboBox.setBounds(90, 50, 300, 25);
        comboBox.setFont(font15);
        add(comboBox);

        // Создаем динамическую панель для переключаемых элементов
        dynamicContent = new JPanel();
        dynamicContent.setLayout(null);
        // dynamicContent.setBorder(new RoundedBorder(10));
        dynamicContent.setBounds(1, 65, 398, 80);
        add(dynamicContent);

        // Создаем кнопку "Выполнить"
        executeButton = new JButton("Выполнить");
        //executeButton.addActionListener(new ExecuteButtonListener());
        executeButton.setBounds(1, 149, 398,30);
        add(executeButton);

        // Создаем поле для ввода заранее
        valueField = new JTextField(10);

        // Инициализируем динамическую панель
        updateDynamicPanel();
    }

    private void updateDynamicPanel() {
        dynamicContent.removeAll();

        Font font15 = new Font("Arial", Font.PLAIN, 15);        

        String selectedItem = (String) comboBox.getSelectedItem();

        if ("Удалить".equals(selectedItem) || "Вырезать".equals(selectedItem)) {
            JRadioButton shiftRadio = new JRadioButton("со сдвигом");
            JRadioButton zeroRadio = new JRadioButton("с обнулением");
            ButtonGroup group = new ButtonGroup();
            group.add(shiftRadio);
            group.add(zeroRadio);

            shiftRadio.setBounds(55, 25, 150, 30);
            zeroRadio.setBounds(215, 25, 150, 30);

            shiftRadio.setFont(font15);
            zeroRadio.setFont(font15);

            dynamicContent.add(shiftRadio);
            dynamicContent.add(zeroRadio);

        } else if ("Вставить".equals(selectedItem)) {
            JRadioButton replaceRadio = new JRadioButton("с замещением");
            JRadioButton shiftRadio = new JRadioButton("со сдвигом");
            ButtonGroup actionGroup = new ButtonGroup();

            actionGroup.add(replaceRadio);
            actionGroup.add(shiftRadio);

            replaceRadio.setBounds(55, 13, 150, 30);
            shiftRadio.setBounds(215, 13, 150, 30);

            replaceRadio.setFont(font15);
            shiftRadio.setFont(font15);

            dynamicContent.add(replaceRadio);
            dynamicContent.add(shiftRadio);

            valueBuffer = new JComboBox<String>(new String []{"из буфера", "задать"});
            valueBuffer.setBounds(10, 49, 120, 25);
            valueBuffer.setFont(font15);
            dynamicContent.add(valueBuffer);

            valueBuffer.addActionListener(new ComboBoxVBListener());

            valueField = new JTextField();
            valueField.setBounds(135, 49, 260, 25);
            valueField.setEditable(false);
            valueField.setFont(font15);
            dynamicContent.add(valueField);

        } else if ("Вставить нули".equals(selectedItem)) {
            zeroField = new JTextField(10);
            zeroField.setFont(font15);
            zeroField.setBounds(120, 35, 150, 25);

            zeroLabel = new JLabel("Кол-во нулей:");
            zeroLabel.setFont(font15);
            zeroLabel.setBounds(10, 35, 150, 25);

            dynamicContent.add(zeroField);
            dynamicContent.add(zeroLabel);
        }

        dynamicContent.revalidate();
        dynamicContent.repaint();
    }

    private class ComboBoxVBListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = (String) valueBuffer.getSelectedItem();
            if (str.equals("из буфера")) {
                valueField.setEditable(false);
            } else if (str.equals("задать")) {
                valueField.setEditable(true);
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

    // private class ExecuteButtonListener implements ActionListener {
    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         JOptionPane.showMessageDialog(EditPanel.this, "Действие выполнено!");
    //     }
    // }

        // Метод для добавления слушателя для кнопки "Найти"
    public void addEditButtonListener(ActionListener listener) {
        executeButton.addActionListener(listener);
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
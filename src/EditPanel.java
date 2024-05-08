import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPanel extends JPanel {
    private JComboBox<String> comboBox, valueBuffer;
    private JPanel dynamicContent;
    private JLabel mainLabel, opLabel;
    private JButton executeButton;
    private JTextField valueField;
    private JRadioButton bufferRadio;
    private JRadioButton valueRadio;

    public EditPanel() {
        setLayout(null);
        setBorder(new RoundedBorder(10));

        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font15 = new Font("Arial", Font.PLAIN, 15);

        mainLabel = new JLabel("Редактирование");
        mainLabel.setFont(font20);
        mainLabel.setBounds(125, 0, 200, 30);
        add(mainLabel);

        opLabel = new JLabel("Операция: ");
        opLabel.setFont(font15);
        opLabel.setBounds(10, 38, 80, 20);
        add(opLabel);

        // Создаем выпадающее меню
        comboBox = new JComboBox<>(new String[]{"Удалить", "Вырезать", "Вставить", "Вставить нули"});
        comboBox.addActionListener(new ComboBoxListener());
        comboBox.setBounds(90, 35, 300, 25);
        comboBox.setFont(font15);
        add(comboBox);

        // Создаем динамическую панель для переключаемых элементов
        dynamicContent = new JPanel();
        dynamicContent.setLayout(null);
        dynamicContent.setBorder(new RoundedBorder(10));
        dynamicContent.setBounds(1, 60, 398, 80);
        add(dynamicContent);

        // Создаем кнопку "Выполнить"
        executeButton = new JButton("Выполнить");
        executeButton.addActionListener(new ExecuteButtonListener());
        executeButton.setBounds(1, 139, 398,30);
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

            replaceRadio.setBounds(55, 10, 150, 30);
            shiftRadio.setBounds(215, 10, 150, 30);

            replaceRadio.setFont(font15);
            shiftRadio.setFont(font15);

            dynamicContent.add(replaceRadio);
            dynamicContent.add(shiftRadio);

            bufferRadio = new JRadioButton("из буфера");
            valueRadio = new JRadioButton("задать значение");
            ButtonGroup sourceGroup = new ButtonGroup();
            sourceGroup.add(bufferRadio);
            sourceGroup.add(valueRadio);

            bufferRadio.setBounds(215, 15, 150, 30);
            valueRadio.setBounds(215, 40, 150, 30); 
            
            bufferRadio.setFont(font15);
            valueRadio.setFont(font15);

            valueBuffer = new JComboBox<String>(new String []{"из буфера", "задать"});
            valueBuffer.setBounds(30, 40, 120, 20);
            valueBuffer.setFont(font15);
            dynamicContent.add(valueBuffer);

            valueBuffer.addActionListener(new ComboBoxVBListener());

            valueField = new JTextField();
            valueField.setBounds(180, 40, 200, 25);
            valueField.setEditable(false);
            valueField.setFont(font15);
            dynamicContent.add(valueField);

            // bufferRadio.addActionListener(new RadioActionListener());
            // valueRadio.addActionListener(new RadioActionListener());

        } else if ("Вставить нули".equals(selectedItem)) {
            JTextField zeroField = new JTextField(10);
            dynamicContent.add(zeroField);
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
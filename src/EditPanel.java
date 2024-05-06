import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditPanel extends JPanel {

    private JComboBox<String> comboBox;
    private JRadioButton radioWithShift, radioWithZero, radioReplace, radioFromBuffer, radioSetValue;
    private JTextField textField;
    private JButton executeButton;

    public EditPanel() {
        setLayout(null);
        comboBox = new JComboBox<>(new String[]{"Удалить", "Вставить", "Вставить нули", "Вырезать"});
        comboBox.setBounds(0,0,100,30);

        textField = new JTextField(10);
        radioReplace = new JRadioButton("с замещением");
        radioWithShift = new JRadioButton("со сдвигом");
        radioFromBuffer = new JRadioButton("из буфера");
        radioSetValue = new JRadioButton("задать значение");

        textField.setBounds(0, 50, 100, 15);

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                remove(textField);
                if (selectedOption.equals("Удалить") || selectedOption.equals("Вырезать")) {
                    radioWithShift = new JRadioButton("со сдвигом");
                    radioWithZero = new JRadioButton("с обнулением");
                    // Добавление радиокнопок на панель
                    add(radioWithShift);
                    add(radioWithZero);
                }

                if (selectedOption.equals("Вставить")) {

                    // Добавление радиокнопок на панель

                    add(radioReplace);
                    add(radioWithShift);

                    add(radioFromBuffer);
                    add(radioSetValue);

                    add(textField);
                }

                if (selectedOption.equals("Вставить нули")) {
                    add(textField);
                }
            }
        });
        executeButton = new JButton("Выполнить");
        executeButton.setBounds(0, 100, 100, 30);

        add(executeButton);
        add(comboBox);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        EditPanel editPanel = new EditPanel();
        editPanel.setBounds(0,0,500,200);
        frame.add(editPanel);

        frame.setVisible(true);
    }
}

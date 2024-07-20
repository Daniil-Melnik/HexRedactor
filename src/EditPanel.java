import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A custom JPanel for handling various editing operations on a table.
 * Provides options for deleting, cutting, copying, pasting, and inserting
 * zeros.
 * 
 * @autor DMelnik
 * @version 1.0
 */

public class EditPanel extends JPanel {
    public JComboBox<String> comboBox;
    public JComboBox<String> valueBuffer;
    private JPanel dynamicContent;
    private JLabel zeroLabel;
    private JLabel opLabel;
    private JLabel mainLabel;
    private JButton executeButton;
    public JTextField valueField;
    public JTextField zeroField;
    private JRadioButton zeroRadio;
    private JRadioButton shiftRadio;
    private JRadioButton shiftPasteRadio;
    private JRadioButton replaceRadio;

    /**
     * Constructs an EditPanel with various editing options.
     */

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

        comboBox = new JComboBox<>(new String[] { "Удалить", "Вырезать", "Копировать", "Вставить", "Вставить нули" });
        comboBox.addActionListener(new ComboBoxListener());
        comboBox.setBounds(90, 50, 300, 25);
        comboBox.setFont(font15);
        add(comboBox);

        dynamicContent = new JPanel();
        dynamicContent.setLayout(null);
        dynamicContent.setBounds(1, 65, 398, 80);
        add(dynamicContent);

        executeButton = new JButton("Выполнить");
        executeButton.setBounds(1, 149, 398, 30);
        add(executeButton);

        valueField = new JTextField(10);

        updateDynamicPanel();
    }

    public boolean isShiftSelected() {
        return shiftRadio.isSelected();
    }

    public boolean isZeroSelected() {
        return zeroRadio.isSelected();
    }

    public boolean isReplaceSelected() {
        return replaceRadio.isSelected();
    }

    public boolean isShiftPasteSelected() {
        return shiftPasteRadio.isSelected();
    }

    /**
     * Updates the dynamic content panel based on the selected operation.
     */

    private void updateDynamicPanel() {
        dynamicContent.removeAll();

        Font font15 = new Font("Arial", Font.PLAIN, 15);

        String selectedItem = (String) comboBox.getSelectedItem();

        if ("Удалить".equals(selectedItem) || "Вырезать".equals(selectedItem)) {
            shiftRadio = new JRadioButton("со сдвигом");
            zeroRadio = new JRadioButton("с обнулением");
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
            replaceRadio = new JRadioButton("с замещением");
            shiftPasteRadio = new JRadioButton("со сдвигом");
            ButtonGroup actionGroup = new ButtonGroup();

            actionGroup.add(replaceRadio);
            actionGroup.add(shiftPasteRadio);

            replaceRadio.setBounds(55, 13, 150, 30);
            shiftPasteRadio.setBounds(215, 13, 150, 30);

            replaceRadio.setFont(font15);
            shiftPasteRadio.setFont(font15);

            dynamicContent.add(replaceRadio);
            dynamicContent.add(shiftPasteRadio);

            valueBuffer = new JComboBox<String>(new String[] { "из буфера", "задать" });
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

    /**
     * Listener for value buffer combo box.
     */

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

    /**
     * Listener for operation combo box.
     */

    private class ComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateDynamicPanel();
        }
    }

    /**
     * Adds an ActionListener to the execute button.
     *
     * @param listener the ActionListener to be added
     */

    public void addEditButtonListener(ActionListener listener) {
        executeButton.addActionListener(listener);
    }
}
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An embedded panel for search functionality.
 * This panel allows searching by mask or by value with various UI components.
 *
 * @see JPanel
 * @see JComboBox
 * @see JRadioButton
 * @see JTextField
 * @see JButton
 * @see JLabel
 * @see RoundedBorder
 * @see ActionListener
 * @see ActionEvent
 * @see ButtonGroup
 * @see Font
 * @see Component
 * @see Graphics
 * @see Insets
 * @author [Your Name]
 * @version 1.0
 */

public class SearchPanel extends JPanel {
    public JComboBox<String> byteSizeComboBox;
    private JRadioButton searchByMaskRadioButton;
    private JRadioButton searchByValueRadioButton;
    public JTextField inputField;
    private JButton findButton;
    private JLabel inputLabel; // Надпись для поля ввода
    private JLabel sizeLabel;
    private JLabel methodLabel;
    private JLabel mainLabel;

    /**
     * Constructor for the SearchPanel.
     * Initializes the panel components and layout.
     */
    public SearchPanel() {

        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font15 = new Font("Arial", Font.PLAIN, 15);

        setLayout(null);
        setBorder(new RoundedBorder(10));

        mainLabel = new JLabel("Поиск");
        mainLabel.setBounds(170, 5, 100, 30);
        mainLabel.setFont(font20);
        add(mainLabel);

        String[] byteOptions = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16" };
        byteSizeComboBox = new JComboBox<>(byteOptions);
        byteSizeComboBox.setBounds(110, 40, 60, 20);

        sizeLabel = new JLabel("Кол-во байт:");
        sizeLabel.setFont(font15);
        sizeLabel.setBounds(10, 40, 100, 20);
        add(sizeLabel);
        add(byteSizeComboBox);

        searchByMaskRadioButton = new JRadioButton("По маске");
        searchByValueRadioButton = new JRadioButton("По значению");

        searchByMaskRadioButton.setFont(font15);
        searchByValueRadioButton.setFont(font15);

        searchByMaskRadioButton.setBounds(110, 70, 120, 20);
        searchByValueRadioButton.setBounds(230, 70, 120, 20);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(searchByMaskRadioButton);
        buttonGroup.add(searchByValueRadioButton);

        methodLabel = new JLabel("Метод:");
        methodLabel.setBounds(48, 68, 50, 20);
        methodLabel.setFont(font15);
        add(methodLabel);

        add(searchByMaskRadioButton);
        add(searchByValueRadioButton);

        inputLabel = new JLabel("Значение:");
        inputLabel.setFont(font15);
        inputLabel.setBounds(26, 95, 70, 20);

        inputField = new JTextField();
        inputField.setBounds(110, 95, 232, 20);

        add(inputLabel);
        add(inputField);

        findButton = new JButton("Найти");
        findButton.setFont(font15);
        findButton.setBounds(1, 125, 398, 24);
        add(findButton);

        searchByMaskRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputLabel.setText("Маска:");
                inputLabel.setBounds(48, 95, 70, 20);

            }
        });

        searchByValueRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputLabel.setText("Значение:");
                inputLabel.setBounds(26, 95, 70, 20);
            }
        });

        searchByValueRadioButton.setSelected(true);
        inputLabel.setText("Значение:");
    }

    /**
     * Checks if the "Search by Mask" radio button is selected.
     *
     * @return true if "Search by Mask" is selected, false otherwise
     */
    public boolean isSearchByMaskSelected() {
        return searchByMaskRadioButton.isSelected();
    }

    /**
     * Checks if the "Search by Value" radio button is selected.
     *
     * @return true if "Search by Value" is selected, false otherwise
     */
    public boolean isSearchByValueSelected() {
        return searchByValueRadioButton.isSelected();
    }

    /**
     * Adds an ActionListener to the "Find" button.
     *
     * @param listener the ActionListener to be added
     */
    public void addSearchButtonListener(ActionListener listener) {
        findButton.addActionListener(listener);
    }
}

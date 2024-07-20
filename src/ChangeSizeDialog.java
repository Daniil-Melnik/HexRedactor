import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridLayout;

/**
 * Class representing a panel for the dialog window used to change table
 * dimensions.
 * <p>
 * This panel provides input fields for the user to specify the new height and
 * width
 * of the table.
 * </p>
 * 
 * @autor DMelnik
 * @version 1.0
 */

public class ChangeSizeDialog extends JPanel {
    private JTextField textField1;
    private JTextField textField2;

    /**
     * Constructs a ChangeSizeDialog with input fields for height and width.
     */

    public ChangeSizeDialog() {
        int rows = 2;
        int cols = 2;

        int textFieldLen = 10;

        setLayout(new GridLayout(rows, cols));

        JLabel label1 = new JLabel("Высота :");
        textField1 = new JTextField(textFieldLen);

        JLabel label2 = new JLabel("Ширина :");
        textField2 = new JTextField(textFieldLen);

        add(label1);
        add(textField1);
        add(label2);
        add(textField2);
    }

    /**
     * Gets the height value from the input field.
     * 
     * @return the height value as a String
     */

    public String getHeightValue() {
        return textField1.getText();
    }

    /**
     * Gets the width value from the input field.
     * 
     * @return the width value as a String
     */

    public String getWidthValue() {
        return textField2.getText();
    }
}

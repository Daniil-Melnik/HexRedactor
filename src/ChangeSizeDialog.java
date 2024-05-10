// DualInputDialogExample.java
import javax.swing.*;
import java.awt.*;

public class ChangeSizeDialog extends JPanel {
    private JTextField textField1;
    private JTextField textField2;

    public ChangeSizeDialog() {
        setLayout(new GridLayout(2, 2));

        JLabel label1 = new JLabel("Первое значение:");
        textField1 = new JTextField(10);

        JLabel label2 = new JLabel("Второе значение:");
        textField2 = new JTextField(10);

        add(label1);
        add(textField1);
        add(label2);
        add(textField2);
    }

    public String getFirstValue() {
        return textField1.getText();
    }

    public String getSecondValue() {
        return textField2.getText();
    }
}

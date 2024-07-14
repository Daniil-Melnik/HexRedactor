// DualInputDialogExample.java
import javax.swing.*;
import java.awt.*;

/*
* Класс-панель
* Служит для описания диалогового окна
* при изменении размеров таблицы
* */

public class ChangeSizeDialog extends JPanel {
    private JTextField textField1;
    private JTextField textField2;

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

    public String getHeightValue() {
        return textField1.getText();
    }

    public String getWidthValue() {
        return textField2.getText();
    }
}

import javax.swing.*;
import java.awt.*;

public class ChangeSizeDialog {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dual Input Dialog Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        frame.setVisible(true);

        // Создаем JPanel для размещения полей ввода
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2)); // Два ряда, два столбца

        JLabel label1 = new JLabel("Первое значение:");
        JTextField textField1 = new JTextField(10);

        JLabel label2 = new JLabel("Второе значение:");
        JTextField textField2 = new JTextField(10);

        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);

        // Отображаем JOptionPane с панелью
        int result = JOptionPane.showConfirmDialog(frame, panel, "Введите значения", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String value1 = textField1.getText();
            String value2 = textField2.getText();
            System.out.println("Первое значение: " + value1);
            System.out.println("Второе значение: " + value2);
        } else {
            System.out.println("Отмена или закрытие");
        }
    }
}

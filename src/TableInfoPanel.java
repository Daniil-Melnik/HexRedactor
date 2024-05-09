import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableInfoPanel extends JPanel {
    private JLabel sizeLabel, lenLabel;
    private JLabel focusLabel;
    private JLabel selectionStartLabel, mainLabel;
    private JButton changeButton;

    public TableInfoPanel(int sizeX, int sizeY, int focus, int selectionStart) {
        setLayout(null);

        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font15 = new Font("Arial", Font.PLAIN, 15);

        setBorder(new RoundedBorder(10));

        mainLabel = new JLabel(" Фокус  -  выделение");
        mainLabel.setFont(font20);
        mainLabel.setBounds(40, 10, 200, 20);
        add(mainLabel);

        sizeLabel = new JLabel("Размер: " + sizeX + " x " + sizeY + " яч.");
        sizeLabel.setFont(font15);
        sizeLabel.setBounds(10, 50, 180, 20);
        add(sizeLabel);

        changeButton = new JButton("Изменить");
        changeButton.setFont(font15);
        changeButton.setBounds(165, 50, 105, 20);
        add(changeButton);

        // Текущий фокус
        focusLabel = new JLabel("Полож. курсора:      " + focus);
        focusLabel.setFont(font15);
        focusLabel.setBounds(11, 80, 200, 20);
        add(focusLabel);

        // Начало выделения
        selectionStartLabel = new JLabel("Начало выделения: " + selectionStart);
        selectionStartLabel.setFont(font15);
        selectionStartLabel.setBounds(10, 110, 200, 20);
        add(selectionStartLabel);

        lenLabel = new JLabel("Длина выделения:   " + selectionStart);
        lenLabel.setFont(font15);
        lenLabel.setBounds(10, 140, 200, 20);
        add(lenLabel);

        // Добавление обработчика событий для кнопки "Изменить"
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Пример реакции на нажатие кнопки
                JOptionPane.showMessageDialog(TableInfoPanel.this, "Нажата кнопка 'Изменить'");
            }
        });
    }

    public void updateInfo(int sizeX, int sizeY, int focus, int selectionStart) {
        // Обновление значений в JLabel
        sizeLabel.setText("Размер: " + sizeX + " x " + sizeY + " ячеек");
        focusLabel.setText("Текущий фокус: " + focus);
        selectionStartLabel.setText("Начало выделения: " + selectionStart);

        // Обязательно обновить панель, чтобы изменения были видны
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Пример использования CustomInfoPanel
        JFrame frame = new JFrame("Пример панели информации");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        // Создаем панель с начальными значениями
        TableInfoPanel infoPanel = new TableInfoPanel(10, 10, 5, 3);

        frame.add(infoPanel);
        frame.setVisible(true);

        // Обновление значений в панели после задержки
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoPanel.updateInfo(15, 20, 7, 1); // Новые значения
            }
        });
        timer.setRepeats(false); // Однократное выполнение таймера
        timer.start();
    }
}
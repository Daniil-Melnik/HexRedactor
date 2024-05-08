import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableInfoPanel extends JPanel {
    private JLabel sizeLabel;
    private JLabel focusLabel;
    private JLabel selectionStartLabel;
    private JButton changeButton;

    public TableInfoPanel(int sizeX, int sizeY, int focus, int selectionStart) {
        setLayout(new GridLayout(3, 2)); // 3 строки, 2 столбца

        // Размеры
        sizeLabel = new JLabel("Размер: " + sizeX + " x " + sizeY + " ячеек");
        add(sizeLabel);

        changeButton = new JButton("Изменить");
        add(changeButton);

        // Текущий фокус
        focusLabel = new JLabel("Текущий фокус: " + focus);
        add(focusLabel);

        // Начало выделения
        selectionStartLabel = new JLabel("Начало выделения: " + selectionStart);
        add(selectionStartLabel);

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
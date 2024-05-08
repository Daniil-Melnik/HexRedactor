import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileManagerPanel extends JPanel {
    private JLabel currentFileLabel;
    private JButton openFileButton;
    private JButton saveAsButton;

    public FileManagerPanel(String initialFileName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Вертикальная компоновка

        // Label для отображения текущего файла
        currentFileLabel = new JLabel("Текущий файл: " + initialFileName);
        add(currentFileLabel);

        // Кнопка "Открыть файл"
        openFileButton = new JButton("Открыть файл");
        add(openFileButton);

        // Кнопка "Сохранить как"
        saveAsButton = new JButton("Сохранить как");
        add(saveAsButton);
    }

    public void setCurrentFile(String newFileName) {
        // Метод для обновления названия файла
        currentFileLabel.setText("Текущий файл: " + newFileName);
        revalidate();
        repaint();
    }

    public JButton getOpenFileButton() {
        // Возвращает кнопку "Открыть файл" для установки обработчика в главном приложении
        return openFileButton;
    }

    public JButton getSaveAsButton() {
        // Возвращает кнопку "Сохранить как" для установки обработчика в главном приложении
        return saveAsButton;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Пример панели файла");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        // Создаем панель с начальными значениями
        FileManagerPanel filePanel = new FileManagerPanel("example.txt");

        // Добавляем обработчики для кнопок
        filePanel.getOpenFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Открыть файл");
            }
        });

        filePanel.getSaveAsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Сохранить как");
            }
        });

        frame.add(filePanel);
        frame.setVisible(true);

        // Пример изменения названия файла
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filePanel.setCurrentFile("new_example.txt");
            }
        });
        timer.setRepeats(false); // Однократное выполнение таймера
        timer.start();
    }
}
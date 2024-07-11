import javax.swing.*;

import java.awt.Font;
// import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* Класс-панель
* Назначение: интерфейс открытия-сохранения файлов
* */

public class FileManagerPanel extends JPanel {
    private JLabel currentFileLabel, mainLabel;
    private JButton openFileButton;
    private JButton saveAsButton;

    public FileManagerPanel(String initialFileName) {
        setLayout(null); // Вертикальная компоновка

        setBorder(new RoundedBorder(10));

        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font15 = new Font("Arial", Font.PLAIN, 15);

        // Label для отображения текущего файла
        currentFileLabel = new JLabel("Текущий файл: " + initialFileName);
        currentFileLabel.setBounds(40, 40, 200, 20);
        currentFileLabel.setFont(font15);
        add(currentFileLabel);

        mainLabel = new JLabel("Файловый менеджер");
        mainLabel.setFont(font20);
        mainLabel.setBounds(40, 10, 200, 20);
        add(mainLabel);

        // Кнопка "Открыть файл"
        openFileButton = new JButton("Открыть новый");
        openFileButton.setBounds(40, 70, 200, 25);
        openFileButton.setFont(font15);
        add(openFileButton);

        // Кнопка "Сохранить как"
        saveAsButton = new JButton("Сохранить как");
        saveAsButton.setBounds(40, 110, 200, 25);
        saveAsButton.setFont(font15);
        add(saveAsButton);
    }

    public void setCurrentFile(String newFileName) {
        // Метод для обновления названия файла
        currentFileLabel.setText("Текущий файл: " + newFileName);
        revalidate();
        repaint();
    }

    public JButton getOpenFileButton() {
        // Возвращает кнопку "Открыть файл" для установки обработчика в главном
        // приложении
        return openFileButton;
    }

    public JButton getSaveAsButton() {
        // Возвращает кнопку "Сохранить как" для установки обработчика в главном
        // приложении
        return saveAsButton;
    }

    ////////////////////////////////////////////////////////////////
    ////////////////////// Заготовки под main //////////////////////
    ////////////////////////////////////////////////////////////////
    public void addOpenFileButtonListener(ActionListener listener) {
        openFileButton.addActionListener(listener);
    }

    public void addSaveAsButtonListener(ActionListener listener) {
        saveAsButton.addActionListener(listener);
    }

}
import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionListener;

/**
 * FileManagerPanel class
 * Purpose: Interface for opening and saving files
 * 
 * @see RoundedBorder
 * @author DMelnik
 */

public class FileManagerPanel extends JPanel {
    private JLabel currentFileLabel;
    private JLabel mainLabel;
    private JButton openFileButton;
    private JButton saveAsButton;

    /**
     * Constructor to create a file manager panel
     * 
     * @param initialFileName the name of the initial file
     */

    public FileManagerPanel(String initialFileName) {
        setLayout(null); // Вертикальная компоновка

        setBorder(new RoundedBorder(10));

        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font15 = new Font("Arial", Font.PLAIN, 15);

        currentFileLabel = new JLabel("Текущий файл: " + initialFileName);
        currentFileLabel.setBounds(40, 40, 200, 20);
        currentFileLabel.setFont(font15);
        add(currentFileLabel);

        mainLabel = new JLabel("Файловый менеджер");
        mainLabel.setFont(font20);
        mainLabel.setBounds(40, 10, 200, 20);
        add(mainLabel);

        openFileButton = new JButton("Открыть новый");
        openFileButton.setBounds(40, 70, 200, 25);
        openFileButton.setFont(font15);
        add(openFileButton);

        saveAsButton = new JButton("Сохранить как");
        saveAsButton.setBounds(40, 110, 200, 25);
        saveAsButton.setFont(font15);
        add(saveAsButton);
    }

    /**
     * Sets the current file name
     * 
     * @param newFileName the new file name to display
     */

    public void setCurrentFile(String newFileName) {
        currentFileLabel.setText("Текущий файл: " + newFileName);
        revalidate();
        repaint();
    }

    /**
     * Returns the open file button
     * 
     * @return the open file button
     */

    public JButton getOpenFileButton() {
        return openFileButton;
    }

    /**
     * Returns the save as button
     * 
     * @return the save as button
     */

    public JButton getSaveAsButton() {
        return saveAsButton;
    }

    /**
     * Adds an ActionListener to the open file button
     * 
     * @param listener the ActionListener to be added
     */
    public void addOpenFileButtonListener(ActionListener listener) {
        openFileButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the save as button
     * 
     * @param listener the ActionListener to be added
     */

    public void addSaveAsButtonListener(ActionListener listener) {
        saveAsButton.addActionListener(listener);
    }

}
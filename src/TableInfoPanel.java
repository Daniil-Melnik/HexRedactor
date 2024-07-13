import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* Класс-панель
* Назначение: описиние панели Фокус-выделение
* */

public class TableInfoPanel extends JPanel {
    private JLabel sizeLabel;
    private JLabel lenLabel;
    private JLabel focusLabel;
    private JLabel selectionStartLabel;
    private JLabel mainLabel;
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
    }

    public void addChangeSizeButtonListener(ActionListener listener) {
        changeButton.addActionListener(listener);
    }

    public void updateInfo(int sizeX, int sizeY, int focus, int selectionStart, int selectionLen) {
        // Обновление значений в JLabel
        sizeLabel.setText("Размер: " + sizeX + " x " + sizeY + " ячеек");
        focusLabel.setText("Текущий фокус: " + focus);
        selectionStartLabel.setText("Начало выделения: " + selectionStart);
        lenLabel.setText("Длина выделения:   " + selectionLen);

        // Обязательно обновить панель, чтобы изменения были видны
        revalidate();
        repaint();
    }

    public void updTableInfo(SheetHolder sH, int [][] highlightCells, int [] offset){
        int currentCol = sH.getCurrentColumn();
        int currentRow = sH.getCurrentRow();

        int currOfft = offset[1] + currentRow * sH.getRowLen() + currentCol - 1;
        int highOfft, highLen;
        if (highlightCells.length != 0){
            int [] startCoord = highlightCells[0];
            highOfft = startCoord[0] * sH.getRowLen() + startCoord[1] + offset[1] - 1;
            highLen = highlightCells[0].length;
        } else {
            highOfft = -0;
            highLen = -0;
        }
                    
        this.updateInfo(sH.getRowLen() , sH.getColumnLen(), currOfft, highOfft, highLen);
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
                infoPanel.updateInfo(15, 20, 7, 1, 1); // Новые значения
            }
        });
        timer.setRepeats(false); // Однократное выполнение таймера
        timer.start();
    }
}
package hexeditor.Panels;

import org.apache.logging.log4j.core.config.builder.api.Component;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import hexeditor.SheetHolder;
import hexeditor.Renderers.RoundedBorder;
// import javafx.geometry.Insets;  

import java.awt.Font;
import java.awt.event.ActionListener;

/**
 * A panel class for displaying and updating focus and selection information.
 *
 * @see JPanel
 * @see JLabel
 * @see JButton
 * @see RoundedBorder
 * @see ActionListener
 * @see Font
 * @see Component
 * @see Graphics
 * @see Insets
 * @author DMelnik
 * @version 1.0
 */

public class TableInfoPanel extends JPanel {
    private JLabel sizeLabel;
    private JLabel lenLabel;
    private JLabel focusLabel;
    private JLabel selectionStartLabel;
    private JLabel mainLabel;
    private JButton changeButton;

    /**
     * Constructs a TableInfoPanel with the specified initial values.
     *
     * @param sizeX          the width of the table
     * @param sizeY          the height of the table
     * @param focus          the current focus position
     * @param selectionStart the start position of the selection
     */

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
        changeButton.setEnabled(((sizeX != 0) && (sizeY != 0)));
        changeButton.setBounds(165, 50, 105, 20);
        add(changeButton);

        // Текущий фокус
        focusLabel = new JLabel("Полож. курсора:      " + focus);
        focusLabel.setFont(font15);
        focusLabel.setBounds(11, 80, 200, 20);
        add(focusLabel);

        selectionStartLabel = new JLabel("Начало выделения: " + selectionStart);
        selectionStartLabel.setFont(font15);
        selectionStartLabel.setBounds(10, 110, 200, 20);
        add(selectionStartLabel);

        lenLabel = new JLabel("Длина выделения:   " + selectionStart);
        lenLabel.setFont(font15);
        lenLabel.setBounds(10, 140, 200, 20);
        add(lenLabel);
    }

    /**
     * Adds an ActionListener to the "Change" button.
     *
     * @param listener the ActionListener to be added
     */

    public void addChangeSizeButtonListener(ActionListener listener) {
        changeButton.addActionListener(listener);
    }

    /**
     * Updates the displayed information on the panel.
     *
     * @param sizeX          the width of the table
     * @param sizeY          the height of the table
     * @param focus          the current focus position
     * @param selectionStart the start position of the selection
     * @param selectionLen   the length of the selection
     */

    public void updateInfo(int sizeX, int sizeY, int focus, int selectionStart, int selectionLen) {
        sizeLabel.setText("Размер: " + sizeX + " x " + sizeY + " ячеек");
        focusLabel.setText("Текущий фокус: " + focus);
        selectionStartLabel.setText("Начало выделения: " + selectionStart);
        lenLabel.setText("Длина выделения:   " + selectionLen);
        changeButton.setEnabled((sizeX != 0) && (sizeY != 0));

        revalidate();
        repaint();
    }

    /**
     * Updates the table information using the provided SheetHolder and highlighted
     * cells.
     *
     * @param sH             the SheetHolder containing table data
     * @param highlightCells the highlighted cells
     * @param offset         the offset array
     */

    public void updTableInfo(SheetHolder sH, int[][] highlightCells, int[] offset) {
        int currentCol = sH.getCurrentColumn();
        int currentRow = sH.getCurrentRow();

        int currOfft = offset[1] + currentRow * sH.getRowLen() + currentCol - 1;
        int highOfft, highLen;
        if (highlightCells.length != 0) {
            int[] startCoord = highlightCells[0];
            highOfft = startCoord[0] * sH.getRowLen() + startCoord[1] + offset[1] - 1;
            highLen = highlightCells[0].length;
        } else {
            highOfft = -0;
            highLen = -0;
        }

        this.updateInfo(sH.getRowLen(), sH.getColumnLen(), currOfft, highOfft, highLen);
    }

}
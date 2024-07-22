import java.awt.event.KeyEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class KeyListeners {
    public void keyListener(KeyEvent e, JTable table, SheetHolder[] sH, JScrollPane scrollPane, int[] offset,
            MouseHig mh) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            keyCodeD(table, sH, scrollPane, offset, mh);
        }
    }

    private void keyCodeD(JTable table, SheetHolder[] sH, JScrollPane scrollPane, int[] offset, MouseHig mh) {
        int row = table.getSelectionModel().getLeadSelectionIndex();
        int column = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
        int[] coord = new int[2];
        coord[0] = row;
        coord[1] = column;

        int rowLen = sH[0].getRowLen();

        mh.addCoord(coord);
        if (mh.getCond() == 2) {
            int[][] highlightCells = mh.getFullCoords(rowLen);
            sH[0].setHCells(highlightCells);
        } else {
            sH[0].setHCells(new int[0][0]);
        }
    }
}

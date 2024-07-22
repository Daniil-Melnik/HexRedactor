import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileManagerListeners {
    public boolean openNewListener(ByteFormatIO[] bIO, JFrame frame, String[] fileName, SheetHolder[] sH,
            String[][] dat, FileManagerPanel fileManagerPanel, int[] offset) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(frame);
        boolean conditionToNewFile = result == JFileChooser.APPROVE_OPTION;

        if (conditionToNewFile) {
            fileName[0] = fileChooser.getSelectedFile().getAbsolutePath();
            bIO[0] = new ByteFormatIO(fileName[0]);

            sH[0] = new SheetHolder(fileName[0]);

            sH[0].setColumnLen(4); // что-то не то, где то задана константа
            sH[0].setRowLen(4);
            sH[0].setDLen(0);
            dat[0] = bIO[0].getHexBytesByOffset(offset[0], 4 * 4);
            sH[0].setAllData(dat[0]);
            String[] smallFName = fileName[0].split("\\\\");
            fileManagerPanel.setCurrentFile(smallFName[smallFName.length - 1]);
        }
        return conditionToNewFile;
    }
}

package main.java.hexeditor.Listeners;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import main.java.hexeditor.SheetHolder;
import main.java.hexeditor.Panels.FileManagerPanel;
import main.java.hexeditor.Utils.ByteFormatIO;

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

    public void saveAsListener(JFrame frame, SheetHolder[] sH, int[] offset, String[][] dat, ByteFormatIO[] bIO) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fileChooser.setSelectedFile(new File("default_filename.txt"));

        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }
            int rowLen = sH[0].getRowLen();
            int columnLen = sH[0].getColumnLen();

            int cellOfft = offset[1] - rowLen * columnLen;
            dat[0] = sH[0].getData();
            int tmpDLen = sH[0].getDLen();
            bIO[0].printData(cellOfft, dat[0], tmpDLen, filePath);
        }
    }
}

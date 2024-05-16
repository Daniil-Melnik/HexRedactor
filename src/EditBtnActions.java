import javax.swing.JTable;
import javax.swing.JTextField;

public class EditBtnActions {

    public void btnFillInZero(SheetHolder sH, JTextField lenField, int [][] highlightCells){

        int [] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int len = Integer.parseInt(lenField.getText());
        int currOfft = startCoord[0] * rowLen + startCoord[1] - 1; // сдвиг в таблице по координате
        ChangeHandler chH = new ChangeHandler(7, currOfft, len, null);
        sH.makeHandle(chH);

    }

    public void btnRemoveZero(SheetHolder sH, int [] offset, int[][] highlightCells) {
        int [] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int offt = offset[1] + startCoord[0] * rowLen + startCoord[1] - 1; // поменяно 21.04.2024 offset с 0 на 1
        int highlightLen = highlightCells.length;
        ChangeHandler cHZero = new ChangeHandler(1, offt, highlightLen, null);
        sH.makeHandle(cHZero);
    }

    public void btnRemoveShift(SheetHolder sH, int [] offset, int [][] highlightCells){

        int [] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

// удаление звёзд начало
        utilByte uB = new utilByte();
        String [] oldData = sH.getData();
        sH.setAllData(uB.clearStars(oldData));
// удаление звёзд конец

        int offt = offset[1] + startCoord[0] * rowLen + startCoord[1] - 1; // поменяно 21.04.2024 offset с 0 на 1
        int highlightLen = highlightCells.length;
        offset[0] = offset[0] + highlightLen;
        ChangeHandler cHShift = new ChangeHandler(2, offt, highlightLen, null);
        sH.makeHandle(cHShift);
    }

    public void btnPasteShift(SheetHolder sH, String [] currData, int [][] highlightCells){
        int [] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int currOfft = startCoord[0] * rowLen + startCoord[1] - 1;
        int len = currData.length;
        ChangeHandler chH = new ChangeHandler(4, currOfft, len, currData);
        sH.makeHandle(chH);
    }

    public void btnPasteSubst(SheetHolder sH, String [] currData, int [][] highlightCells){
        int [] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int currOfft = startCoord[0] * rowLen + startCoord[1] - 1; // сдвиг в таблице по координате

        int len = currData.length;
        ChangeHandler chH = new ChangeHandler(3, currOfft, len, currData);
        sH.makeHandle(chH);
    }

    public void btnCutShift(JTable table, SheetHolder sH, int [] offset, String [][] buffer, int [][] highlightCells) {
        int [] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();
        
        int offt = offset[1] + startCoord[0] * rowLen + startCoord[1] - 1;
        int highlightLen = highlightCells[0].length;

        utilByte uB = new utilByte();
        String [] strArr = uB.getValuesOfHighlt(table, highlightCells);
        buffer[0] = strArr;

        ChangeHandler chH = new ChangeHandler(2, offt, highlightLen, null);
        sH.makeHandle(chH);
    }

    public void btnCutZero(JTable table, SheetHolder sH, int [] offset, String [][] buffer, int [][] highlightCells){
        int [] startCoord = highlightCells[0];
        int rowLen = sH.getRowLen();

        int offt = offset[1] + startCoord[0] * rowLen + startCoord[1] - 1; // поменяно 21.04.2024 offset с 0 на 1
        int highlightLen = highlightCells.length;

        utilByte uB = new utilByte();
        String [] strArr = uB.getValuesOfHighlt(table, highlightCells);
        buffer[0] = strArr;

        ChangeHandler cHZero = new ChangeHandler(1, offt, highlightLen, null);
        sH.makeHandle(cHZero);
    }
}

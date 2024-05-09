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
}

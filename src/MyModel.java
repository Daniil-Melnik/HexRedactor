import javax.swing.JLabel;

class MyModel extends javax.swing.table.DefaultTableModel{

    Object[][] row;

    Object[] col;

    public MyModel (Object [][] row, Object [] col) {
        this.row = row;
        this.col = col;
        //Adding columns
        for (Object c : this.col)
            this.addColumn(c);

        //Adding rows
        for (Object[] r : this.row)
            addRow(r);
    }
}
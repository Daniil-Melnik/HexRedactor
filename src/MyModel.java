class MyModel extends javax.swing.table.DefaultTableModel{

    public MyModel (Object [][] row, Object [] col){
        //Adding columns
        for(Object c: col)
            this.addColumn(c);

        //Adding rows
        for(Object[] r: row)
            addRow(r);

    }

    @Override

    public Class getColumnClass(int columnIndex) {
        if(columnIndex == 0)return getValueAt(0, columnIndex).getClass();

        else return super.getColumnClass(columnIndex);

    }

}
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class mainGui extends JFrame {

    public static void createGUI() throws IOException {

        JButton forward, back, info;
        MainGuiListeners mGL = new MainGuiListeners();
        ChangeFoculListeners cFL = new ChangeFoculListeners();
        FileManagerListeners fmL = new FileManagerListeners();
        KeyListeners kL = new KeyListeners();
        int[] offset = { 0, 0 };

        boolean[] changed = { false };
        final String[][] dat = { null, null };

        String[] maskValue = { "" };
        final String[] fileName = { "" };
        final String[] byteSize = { "" };

        final SheetHolder[] sH = { null };
        final ByteFormatIO[] bIO = { null };

        MouseHig mh = new MouseHig();

        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HandChng hc = new HandChng(frame);

        JTable table = new JTable();

        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        final String[][] buffer = { {} };

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 0, 0);

        int[][] highlightCells = new int[0][0];
        int[][] findedCells = new int[0][0];
        int[][] errorCells = new int[0][0];
        table.setDefaultRenderer(JLabel.class, new Renderer(highlightCells, errorCells, findedCells));

        BlockSizePanel bSP = new BlockSizePanel();
        bSP.setBounds(400, 10, 700, 175);
        frame.add(bSP);

        SearchPanel searchPanel = new SearchPanel();
        searchPanel.setBounds(400, 200, 400, 150);
        frame.add(searchPanel);

        searchPanel.addSearchButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGL.searchButtonListener(sH, searchPanel, byteSize, offset, maskValue, bIO, hc);
                setTable(table, scrollPane, offset[1], sH[0]);
            }
        });

        EditPanel editPanel = new EditPanel();
        editPanel.setBounds(400, 360, 400, 180);
        editPanel.addEditButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                EditTypes selectedAction = (EditTypes) editPanel.comboOperationType.getSelectedItem();
                int[][] highlightCells = sH[0].getHCells();
                try {
                    mGL.editButtonListener(selectedAction, editPanel, sH, offset, highlightCells, bIO, hc, buffer,
                            table);
                    sH[0].resetSheet(mh);
                    setTable(table, scrollPane, offset[1], sH[0]);
                    changed[0] = true;
                } catch (ArrayIndexOutOfBoundsException exception) {
                    hc.showOk("Ошибка", "Нельзя редактировать область **");
                }

            }
        });
        frame.add(editPanel);

        TableInfoPanel tableInfoPanel = new TableInfoPanel(0, 0, 0, 0);
        tableInfoPanel.setBounds(820, 360, 280, 180);
        frame.add(tableInfoPanel);
        tableInfoPanel.addChangeSizeButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mGL.changeSizeBtnListener(sH, frame, changed, offset, hc, bIO, fileName, dat);
                    setTable(table, scrollPane, offset[1], sH[0]);
                } catch (NumberFormatException ex) {
                    hc.showOk("Ошибка", "Размерности - целое число ячеек");
                }
            }
        });

        FileManagerPanel fileManagerPanel = new FileManagerPanel("null");
        fileManagerPanel.setBounds(820, 200, 280, 150);
        frame.add(fileManagerPanel);
        fileManagerPanel.addOpenFileButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean gettedOptionCondition = fmL.openNewListener(bIO, frame, fileName, sH, dat, fileManagerPanel,
                        offset);
                if (gettedOptionCondition) {
                    setTable(table, scrollPane, offset[1], sH[0]);
                }

            }
        });

        fileManagerPanel.addSaveAsButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fmL.saveAsListener(frame, sH, offset, dat, bIO);
            }

        });

        final int[] prevCol = { 0 };
        final int[] prevRow = { 1 };

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cFL.rowFocusListener(e, sH, table, prevCol, prevRow, offset, bIO, bSP, tableInfoPanel);
            }
        });

        table.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cFL.columnFocusListener(e, sH, table, prevCol, prevRow, offset, bIO, bSP, tableInfoPanel);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    int[] coord = new int[2];
                    coord[0] = row;
                    coord[1] = column;
                    if (!((String) table.getValueAt(row, column)).equals("**")) {
                        mh.addCoord(coord);

                        int rowLen = sH[0].getRowLen();

                        if (mh.getCond() == 2) {
                            int[][] highlightCells = mh.getFullCoords(rowLen);
                            sH[0].setHCells(highlightCells);
                            setTable(table, scrollPane, offset[1], sH[0]);
                        } else {
                            sH[0].setHCells(new int[0][0]);
                            setTable(table, scrollPane, offset[1], sH[0]);
                        }
                    }
                }
            }
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                kL.keyListener(e, table, sH, scrollPane, offset, mh);

                setTable(table, scrollPane, offset[1], sH[0]);
            }
        });

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (row != TableModelEvent.HEADER_ROW && column != TableModelEvent.ALL_COLUMNS) {
                    mGL.tableChangeListener(e, table, sH, scrollPane, hc, offset, row, column);
                    setTable(table, scrollPane, offset[1], sH[0]);
                    changed[0] = true;

                }
            }
        });

        forward = new JButton();
        forward.setIcon(new ImageIcon("src/icons/forward.png"));
        forward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {

                    long fileLen = bIO[0].getFileLength(sH[0].getfName());
                    int rowLen = sH[0].getRowLen();
                    int columnLen = sH[0].getColumnLen();
                    if ((offset[0] + (long) rowLen * columnLen) < fileLen) {
                        mGL.forvardButtonListener(bIO, sH, offset, changed, dat, fileName, hc, maskValue, byteSize,
                                rowLen, columnLen);
                        // наследование поиска конец
                        sH[0].resetSheet(mh);
                        setTable(table, scrollPane, offset[1], sH[0]);
                    }
                } catch (IOException e) {
                    System.err.println("An error occurred while trying to get the file length: " + e.getMessage());
                }
            }
        });

        back = new JButton();
        back.setIcon(new ImageIcon("src/icons/back.png"));
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int rowLen = sH[0].getRowLen();
                int columnLen = sH[0].getColumnLen();
                if ((offset[0] - (long) rowLen * columnLen) >= 0) {
                    mGL.backButtonListener(sH, offset, rowLen, columnLen, changed, bIO, dat, fileName, hc, byteSize,
                            maskValue);
                    sH[0].resetSheet(mh);
                    setTable(table, scrollPane, offset[1], sH[0]);
                }
            }
        });

        info = new JButton("инфо");
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGL.infoButtonListener();
            }
        });
        forward.setBounds(710, 550, 300, 40);
        back.setBounds(400, 550, 300, 40);
        info.setBounds(1020, 550, 80, 40);

        frame.getContentPane().add(scrollPane);
        frame.add(forward);
        frame.add(back);
        frame.add(info);

        frame.setSize(1150, 620);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void setTable(JTable table, JScrollPane scrollPane, int offt, SheetHolder sH) {
        String[] data = null;
        data = sH.getData();
        UtilByte ub = new UtilByte();
        int rowLen = sH.getRowLen();
        // int columnLen = sH.getColumnLen();

        int[][] highlightCells = sH.getHCells();
        int[][] findedCells = sH.getFCells();
        int[][] errorCells = sH.getErCells();

        int tmpColumnLen = (sH.getData().length % sH.getRowLen() == 0) ? sH.getData().length / sH.getRowLen()
                : sH.getData().length / sH.getRowLen() + 1;
        Object[][] tableData = ub.toLabeledArr(rowLen, tmpColumnLen, data, offt); // 16.05.2024

        String[] newColumnNames = new String[rowLen + 1];

        newColumnNames[0] = "off";
        for (int m = 1; m < rowLen + 1; m++) {
            newColumnNames[m] = Integer.toString(m - 1);
        }

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setColumnIdentifiers(newColumnNames);

        tableModel.getDataVector().removeAllElements();
        for (int i = tableData.length - 1; i >= 0; i--) {
            tableModel.insertRow(0, tableData[i]);
        }
        int leftColumnWidth = 40;
        int columnCount = rowLen + 1; // количество столбцов, а не строк
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(leftColumnWidth);
        ;

        // Устанавливаем ширину остальных столбцов
        int otherColumnWidth = 80;
        scrollPane.setBounds(0, 0, 350, 588);

        for (int i = 1; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(otherColumnWidth);
        }

        // Создаем экземпляр класса Renderer, передавая массив координат
        Renderer renderer = new Renderer(highlightCells, errorCells, findedCells);
        table.requestFocus();
        table.changeSelection(0, 1, false, false);
        // Устанавливаем рендерер для всех столбцов
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                try {
                    createGUI();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
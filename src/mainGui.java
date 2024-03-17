import java.io.IOException;
import java.util.Arrays;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class mainGui extends JFrame {

    public static void createGUI() throws IOException {

        JButton Hol;

        ByteIO bIO = new ByteIO("src/1.txt");
        bIO.getByteOfFile();
        utilByte ub = new utilByte(bIO.getBytes());

        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int len = 4;
        String [] columnNames = new String [len];
        Arrays.fill(columnNames, "");

        //String [][] data = ub.toArr(4);
        String [][] data = ub.toArr(len);

        JTable table = new JTable(data, columnNames);

        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 120, 800, 300);

        Hol = new JButton ("блямс!");
		Hol.setBounds(650,480,100,30);
		Hol.setFont(new Font("Arial", Font.PLAIN, 20));
		Hol.setHorizontalAlignment(SwingConstants.CENTER);
		Hol.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				frame.remove(scrollPane);
                frame.repaint(); 
			}});


        frame.getContentPane().add(scrollPane);
        frame.add(Hol);

        frame.pack();
        frame.setSize(1000,600);
		frame.setLayout(null); 
		frame.setVisible(true);
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
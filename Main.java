import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public  class Main extends JFrame{

	public static final char CORNER_CHARACTOR = '+';
	public static final char BOUNDARY_CHARACTOR_HORIZONTAL = '-';
	public static final char BOUNDARY_CHARACTOR_VERTICAL = '|';

	public  Container contentPane;

	private String[][] tableData = {
			{"a","b"},
			{"c","d"},
			{"e","f"}
	};

	private String[] ColumnNames = {"1","2"};

	public static void main(String[] args) {
		Main main = new Main("Text Base Table");
		main.setVisible(true);
		main.setResizable(false);
		main.myMain();
	}

	public Main(String title){
		setTitle(title);
		getContentPane().setPreferredSize(new Dimension(600,400));
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = getContentPane();

		DefaultTableModel tableModel = new DefaultTableModel(tableData,ColumnNames);

		JPanel mainPane = new JPanel();
		JTable table = new JTable(tableModel);
		JTextArea textArea = new JTextArea();
		JButton addRowsButton = new JButton("<html>add <br>rows");
		JButton addColumnsButton = new JButton("add columns");


		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JScrollPane jScrollPane = new JScrollPane(table);
		jScrollPane.setPreferredSize(new Dimension(400,100));

		mainPane.setLayout(null);
		mainPane.add(jScrollPane);
		mainPane.add(textArea);
		mainPane.add(addColumnsButton);
		mainPane.add(addRowsButton);

		jScrollPane.setBounds(0,0,550,150);
		textArea.setBounds(0,200,600,200);
		addRowsButton.setBounds(550,0,50,150);
		addColumnsButton.setBounds(0,150,550,50);

		addRowsButton.setMargin(new Insets(0,0,0,0));
		addColumnsButton.setMargin(new Insets(0,0,0,0));

		addRowsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.addColumn(tableModel.getColumnCount(),new Object[]{});

			}
		});

		addColumnsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(new Object[]{} );
			}
		});

		contentPane.add(mainPane);

		mainPane.setOpaque(false);
		mainPane.setVisible(true);

		print(getTableData(table));

		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				textArea.setText(makeTextBaseTable(table));
			}
		});
		textArea.setText(makeTextBaseTable(table));
		textArea.setFont(new Font("monospaced",Font.PLAIN,14));
	}

	private void myMain(){
	}

	private String makeTextBaseTable(JTable table){
		return makeTextBaseTable(getTableData(table));
	}

	private String makeTextBaseTable(String[][] tableData){
		StringBuilder sb= new StringBuilder();
		for (int i=0,len=tableData.length;i<len;i++){
			StringBuilder sb2 = new StringBuilder();
			int columnsWidth =0;
			for (int j=0,lenj=tableData[0].length;j<lenj;j++){
				columnsWidth +=stringWidth(tableData[i][j]);
				sb2.append(tableData[i][j]);
				sb2.append(BOUNDARY_CHARACTOR_VERTICAL);
			}
			sb.append(CORNER_CHARACTOR);
			sb.append(repeatChar(BOUNDARY_CHARACTOR_HORIZONTAL,columnsWidth*2-1)).append(CORNER_CHARACTOR).append("\n")
					.append(BOUNDARY_CHARACTOR_VERTICAL).append(sb2).append("\n");
			if(i==len-1){
				sb.append(CORNER_CHARACTOR).append(repeatChar(BOUNDARY_CHARACTOR_HORIZONTAL,columnsWidth*2-1)).append(CORNER_CHARACTOR);
			}
		}
		return sb.toString();
	}

	private String[][] getTableData(JTable table){
		int rowCount = table.getRowCount();
		int columnCount = table.getColumnCount();
		String[][] data = new String[rowCount][columnCount];
		for (int row = 0;row<rowCount;row++){
			for(int column = 0;column<columnCount;column++){
				data[row][column] = (String) table.getValueAt(row,column);
			}
		}

		return data;
	}

	private void print(Object[][] objects){
		for (int i = 0,len=objects.length,lenj=objects[0].length;i<len;i++){
			for (int j=0;j<lenj;j++){
				System.out.print(objects[i][j]);
			}
			System.out.println();
		}
	}

	private int stringWidth(String str) {
		int len = 0;
		for (int i = 0, leni = str.length(); i < leni; i++) {
			char c = str.charAt(i);
			if ((c <= '\u007e') || (c == '\u00a5') || (c == '\u203e') || (c >= '\uff61' && c <= '\uff9f')) {
				len++;
			}else{
				len+=2;
			}
		}
		return len;
	}

	private String repeatString(String str,int len){
		for (int i=0;i<len;i++){
			str+=str;
		}
		return str;
	}

	private String repeatChar(char c,int len){
	char[] cs = new char[len];
	for (int i=0;i<len;i++){
		cs[i]=c;
	}
	return String.valueOf(cs);
	}
}

package conditioner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class LogUI {
	public LogUI(Master master) {
		JFrame frame = new JFrame("查看报表");
		frame.setLayout(null);
		frame.setBounds(200, 200, 1200, 600);
		frame.setVisible(true);
		
		String[] columnNames = {"房间号","起始时间","结束时间","起始温度","目标温度","风速","费用"};
	    JTable logTable = new JTable(master.info(), columnNames);
	    
	    TableColumn second = logTable.getColumnModel().getColumn(1);
	    TableColumn third = logTable.getColumnModel().getColumn(2);
	    second.setPreferredWidth(200);
	    third.setPreferredWidth(200);
	    
	    JScrollPane p = new JScrollPane(logTable);
	    p.setBounds(100, 30, 1000, 400);
	    frame.add(p);
	}
}

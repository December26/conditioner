package conditioner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class LogUI {
	public LogUI(Master master) {
		JFrame frame = new JFrame("�鿴����");
		frame.setLayout(null);
		frame.setBounds(200, 200, 1200, 600);
		frame.setVisible(true);
		
		String[] columnNames = {"�����","��ʼʱ��","����ʱ��","��ʼ�¶�","Ŀ���¶�","����","����"};
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

package conditioner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.dom4j.DocumentException;

public class MasterUI {
	public MasterUI() throws Exception {
		Master master = new Master();
		JFrame frame = new JFrame("����յ�");
		frame.setLayout(null);
		frame.setBounds(100, 100, 1000, 800);
		frame.setVisible(true);	

		/*JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);
		panel.setVisible(true)*/;
		
		JButton setting = new JButton("����");
		JButton view = new JButton("�鿴����״̬");
		JButton log = new JButton("�鿴����");
		
		setting.setBounds(120, 120, 150, 100);
		view.setBounds(120, 320, 150, 100);
		log.setBounds(120, 520, 150, 100);
		
		frame.add(setting);
		frame.add(view);
		frame.add(log);
		
		setting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SettingUI(master);
			}
		});
		
	}
	
}

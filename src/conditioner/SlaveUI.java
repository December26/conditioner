package conditioner;

import javax.swing.JFrame;

public class SlaveUI {
	public SlaveUI(int roomId) {
		Slave slave = new Slave(roomId);
		JFrame frame = new JFrame(roomId + "����");
		frame.setLayout(null);
		frame.setBounds(100, 100, 1000, 800);
		frame.setVisible(true);
		
	}
	
}

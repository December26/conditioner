package conditioner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class viewUI {
	public viewUI(Master master) {
		JFrame frame = new JFrame("房间");
		frame.setLayout(null);
		frame.setBounds(200, 200, 800, 600);
		frame.setVisible(true);
		
		JLabel label1 = new JLabel("房间号:");
        label1.setBounds(10,70,150,25);
        frame.add(label1);
        JLabel text1 = new JLabel();
        text1.setBounds(150,70,165,25);
        frame.add(text1);
        
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(master.getRefreshRate());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					StringBuffer roomIDs = new StringBuffer(" ");
					for(int i=0; i<master.slaves.size(); i++) {
						roomIDs = roomIDs.append(String.valueOf(master.slaves.get(i).getRoomId())+"        ");
					}
					
					text1.setText(roomIDs.toString());
				}
				
			}
		}).start();
	}
}

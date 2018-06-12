package conditioner;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ViewUI {
	
	
	public ViewUI(Master master) {
		JFrame frame = new JFrame("房间");
		frame.setLayout(null);
		frame.setBounds(200, 200, 800, 600);
		frame.setVisible(true);
		
		JLabel label1 = new JLabel("房间号:");
        label1.setBounds(10,70,150,25);
        frame.add(label1);
        JLabel text1 = new JLabel();
        text1.setBounds(150,70,300,25);
        frame.add(text1);
        
        JLabel label2 = new JLabel("风速:");
        label2.setBounds(10,120,150,25);
        frame.add(label2);
        JLabel text2 = new JLabel();
        text2.setBounds(150,120,300,25);
        frame.add(text2);
        
        JLabel label3 = new JLabel("目标温度:");
        label3.setBounds(10,170,150,25);
        frame.add(label3);
        JLabel text3 = new JLabel();
        text3.setBounds(150,170,300,25);
        frame.add(text3);
        
        JLabel label4 = new JLabel("当前温度:");
        label4.setBounds(10,220,150,25);
        frame.add(label4);
        JLabel text4 = new JLabel();
        text4.setBounds(150,220,300,25);
        frame.add(text4);
        
        JLabel label5 = new JLabel("当前连接数:"+"0");
        label5.setBounds(10,300,150,25);
        frame.add(label5);
        
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(master.getRefreshRate()*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					StringBuilder roomIDs = new StringBuilder(" ");
					StringBuilder speeds = new StringBuilder(" ");
					StringBuilder targets = new StringBuilder(" ");
					StringBuilder currents = new StringBuilder(" ");
					for(int i=0; i<master.slaves.size(); i++) {
						roomIDs = roomIDs.append(String.valueOf(master.slaves.get(i).getRoomId())+"        ");
						speeds = speeds.append(String.valueOf(master.slaves.get(i).getSpeed())+"          ");
						targets = targets.append(String.valueOf(master.slaves.get(i).getTargetTemperature())+"        \t");
						currents = currents.append(String.valueOf(master.slaves.get(i).getCurrentTemperature())+"        ");
					}
					
					text1.setText(roomIDs.toString());
					text2.setText(speeds.toString());
					text3.setText(targets.toString());
					text4.setText(currents.toString());
					label5.setText("当前连接数:"+master.slaves.size());
					//System.out.println("更新");
				}
				
			}
		}).start();
	}
	
}

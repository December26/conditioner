package conditioner;

import javax.swing.*;

public class SlaveUI {
	public SlaveUI(int roomId) {
		Slave slave = new Slave(roomId);
		JFrame frame = new JFrame(roomId + "房间");
		frame.setLayout(null);
		frame.setBounds(200, 200, 800, 600);
		frame.setVisible(true);
		
		JLabel label1 = new JLabel("工作模式:");
        label1.setBounds(10,70,150,25);
        frame.add(label1);
        JTextField text1 = new JTextField(20);
        text1.setBounds(150,70,165,25);
        text1.setText(String.valueOf(slave.getMode()));
        frame.add(text1);
        
        JLabel label2 = new JLabel("缺省温度");
        label2.setBounds(10,120,150,25);
        frame.add(label2);
        JTextField text2 = new JTextField(20);
        text2.setBounds(150,120,165,25);
        if(slave.getMode()==0)
        	text2.setText("22");
        else
        	text2.setText("28");
        frame.add(text2);
		
        JLabel label3 = new JLabel("刷新频率");
        label3.setBounds(10,170,150,25);
        frame.add(label3);
        JTextField text3 = new JTextField(20);
        text3.setBounds(150,170,165,25);
        text3.setText(String.valueOf(slave.getRefreshRate()));
        frame.add(text3);
        
        JLabel label4 = new JLabel("当前温度");
        label4.setBounds(10,220,150,25);
        frame.add(label4);
        JTextField text4 = new JTextField(20);
        text4.setBounds(150,220,165,25);
        text4.setText(String.valueOf(slave.getCurrentTemperature()));
        frame.add(text4);
        
        JLabel label5 = new JLabel("目标温度");
        label5.setBounds(10,270,150,25);
        frame.add(label5);
        JTextField text5 = new JTextField(20);
        text5.setBounds(150,270,165,25);
        text5.setText(String.valueOf(slave.getTargetTemperature()));
        frame.add(text5);
        
        JLabel label6 = new JLabel("风速");
        label6.setBounds(10,320,150,25);
        frame.add(label6);
        JTextField text6 = new JTextField(20);
        text6.setBounds(150,320,165,25);
        text6.setText(String.valueOf(slave.getSpeed()));
        frame.add(text6);
        
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				slave.changeTemperature();
				try {
					Thread.sleep(slave.getRefreshRate());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				text4.setText(String.valueOf(slave.getCurrentTemperature()));
				
			}
		}).start();
	}
	
}

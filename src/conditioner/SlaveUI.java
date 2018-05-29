package conditioner;

import javax.swing.*;

public class SlaveUI {
	public SlaveUI(int roomId) {
		Slave slave = new Slave(roomId);
		JFrame frame = new JFrame(roomId + "����");
		frame.setLayout(null);
		frame.setBounds(200, 200, 800, 600);
		frame.setVisible(true);
		
		JLabel label1 = new JLabel("����ģʽ:");
        label1.setBounds(10,70,150,25);
        frame.add(label1);
        JTextField text1 = new JTextField(20);
        text1.setBounds(150,70,165,25);
        text1.setText(String.valueOf(slave.getMode()));
        frame.add(text1);
        
        /*JLabel label2 = new JLabel("ȱʡ�¶�");
        label2.setBounds(10,120,150,25);
        frame.add(label2);
        JTextField text2 = new JTextField(20);
        text2.setBounds(150,120,165,25);
        if(slave.getMode()==0)
        	text2.setText("22");
        else
        	text2.setText("28");
        frame.add(text2);*/
		
        JLabel label2 = new JLabel("ˢ��Ƶ��");
        label2.setBounds(10,120,150,25);
        frame.add(label2);
        JLabel text2 = new JLabel();
        text2.setBounds(150,120,165,25);
        text2.setText(String.valueOf(slave.getRefreshRate()));
        frame.add(text2);
        
        JLabel label3 = new JLabel("��ǰ�¶�");
        label3.setBounds(10,170,150,25);
        frame.add(label3);
        JLabel text3 = new JLabel();
        text3.setBounds(150,170,165,25);
        text3.setText(String.valueOf(slave.getCurrentTemperature()));
        frame.add(text3);
        
        JLabel label4 = new JLabel("Ŀ���¶�");
        label4.setBounds(10,220,150,25);
        frame.add(label4);
        JTextField text4 = new JTextField(20);
        text4.setBounds(150,220,165,25);
        if(slave.getMode()==0)
        	text4.setText("22");
        else
        	text4.setText("28");
        frame.add(text4);
        
        JLabel label5 = new JLabel("����");
        label5.setBounds(10,270,150,25);
        frame.add(label5);
        JTextField text5 = new JTextField(20);
        text5.setBounds(150,270,165,25);
        text5.setText(String.valueOf(slave.getSpeed()));
        frame.add(text5);
        
        JLabel label6 = new JLabel("�õ���");
        label6.setBounds(10,320,150,25);
        frame.add(label6);
        JLabel text6 = new JLabel();
        text6.setBounds(150,320,165,25);
        text6.setText(String.valueOf(slave.getUsed()));
        frame.add(text6);
        
        JLabel label7 = new JLabel("��ǰ���");
        label7.setBounds(10,370,150,25);
        frame.add(label7);
        JLabel text7 = new JLabel();
        text7.setBounds(150,370,165,25);
        text7.setText(String.valueOf(slave.getCost()));
        frame.add(text7);
        
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while(true) {
					
					try {
						Thread.sleep(slave.getRefreshRate()*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						slave.connectToMaster();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					slave.changeTemperature();
					text1.setText(String.valueOf(slave.getMode()));
					text2.setText(String.valueOf(slave.getRefreshRate()));
					text3.setText(String.valueOf(slave.getCurrentTemperature()));
					text4.setText(String.valueOf(slave.getTargetTemperature()));
					text5.setText(String.valueOf(slave.getSpeed()));
					text6.setText(String.valueOf(slave.getUsed()));
					text7.setText(String.valueOf(slave.getCost()));
				}			
				
			}
		}).start();

        JButton button = new JButton("����");
        
	
	}
	
}

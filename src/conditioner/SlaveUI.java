package conditioner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;

import javax.swing.*;

import com.sun.glass.events.WindowEvent;

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
		
        JLabel label2 = new JLabel("刷新频率");
        label2.setBounds(10,120,150,25);
        frame.add(label2);
        JLabel text2 = new JLabel();
        text2.setBounds(150,120,165,25);
        text2.setText(String.valueOf(slave.getRefreshRate()));
        frame.add(text2);
        
        JLabel label3 = new JLabel("当前温度");
        label3.setBounds(10,170,150,25);
        frame.add(label3);
        JLabel text3 = new JLabel();
        text3.setBounds(150,170,165,25);
        text3.setText(String.valueOf(slave.getCurrentTemperature()));
        frame.add(text3);
        
        JLabel label4 = new JLabel("目标温度");
        label4.setBounds(10,220,150,25);
        frame.add(label4);
        JTextField text4 = new JTextField(20);
        text4.setBounds(150,220,165,25);
        if(slave.getMode()==0)
        	text4.setText("22");
        else
        	text4.setText("28");
        frame.add(text4);
        
        JLabel label5 = new JLabel("风速");
        label5.setBounds(10,270,150,25);
        frame.add(label5);
        JTextField text5 = new JTextField(20);
        text5.setBounds(150,270,165,25);
        text5.setText(String.valueOf(slave.getSpeed()));
        frame.add(text5);
        
        JLabel label6 = new JLabel("用电量");
        label6.setBounds(10,320,150,25);
        frame.add(label6);
        JLabel text6 = new JLabel();
        text6.setBounds(150,320,165,25);
        text6.setText(String.valueOf(slave.getUsed()));
        frame.add(text6);
        
        JLabel label7 = new JLabel("当前电费");
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
					if(slave.getSpeed() == 4) {
						System.out.println("0000000000");
						break;
					}
					try {
						Thread.sleep(slave.getRefreshRate()*1000);
						if(slave.isFlag())
							slave.connectToMaster();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					slave.changeTemperature();
					if(!text1.getText().equals(String.valueOf(slave.getMode())))
					text1.setText(String.valueOf(slave.getMode()));
					text2.setText(String.valueOf(slave.getRefreshRate()));
					text3.setText(String.valueOf(slave.getCurrentTemperature()));
					if(!text4.getText().equals(String.valueOf(slave.getTargetTemperature())))
						text4.setText(String.valueOf(slave.getTargetTemperature()));
					if(!text5.getText().equals(String.valueOf(slave.getSpeed())))
						text5.setText(String.valueOf(slave.getSpeed()));
					text6.setText(String.valueOf(slave.getUsed()));
					text7.setText(String.valueOf(slave.getCost()));
				}			
				
			}
		}).start();

        JButton button = new JButton("更改");
        button.setBounds(200, 420, 100, 30);
        frame.add(button);
        
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				slave.setMode(Integer.valueOf(text1.getText()));
				slave.setCost(Integer.valueOf(text2.getText()));
				slave.setCurrentTemperature(Double.valueOf(text3.getText()));
				slave.setTargetTemperature(Double.valueOf(text4.getText()));
				slave.setSpeed(Integer.valueOf(text5.getText()));
				slave.setUsed(Double.valueOf(text6.getText()));
				slave.setCost(Double.valueOf(text7.getText()));
			}
		});
        
        frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				slave.disconnectToMaster();
        		System.out.println("断开连接");
        		//frame.dispose();
			}
			
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(java.awt.event.WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}
	
}

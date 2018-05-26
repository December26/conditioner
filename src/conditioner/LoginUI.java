package conditioner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginUI {
	public LoginUI() {
		JFrame frame = new JFrame("温度控制系统");
		frame.setLayout(null);
		frame.setBounds(600, 300, 600, 400);
		JButton button1 = new JButton("中央空调");
		JButton button2 = new JButton("从控空调");
		button1.setBounds(380, 70, 150, 30);
		button2.setBounds(380, 120, 150, 30);
		
		JLabel label1 = new JLabel("请输入管理员密码:");
        label1.setBounds(10,70,150,25);
        frame.add(label1);
        JTextField text1 = new JTextField(20);
        text1.setBounds(150,70,165,25);
        frame.add(text1);
        
        JLabel label2 = new JLabel("请输入房间号:");
        label2.setBounds(10,120,150,25);
        frame.add(label2);
        JTextField text2 = new JTextField(20);
        text2.setBounds(150,120,165,25);
        frame.add(text2);
        
        
		frame.add(button1);
		frame.add(button2);
		
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = text1.getText();
				if(password.equals("123456"))
					try {
						new MasterUI();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else 
					JOptionPane.showMessageDialog(null, "密码错误", "WRONG", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String roomId = text2.getText();
				new SlaveUI(roomId);
			}
		});
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

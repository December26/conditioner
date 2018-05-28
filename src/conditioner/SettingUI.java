package conditioner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SettingUI {
	public SettingUI(Master master) {
		JFrame frame = new JFrame("设置");
		frame.setLayout(null);
		frame.setBounds(200, 200, 800, 600);
		frame.setVisible(true);
		
		JLabel label1 = new JLabel("工作模式:");
        label1.setBounds(10,70,150,25);
        frame.add(label1);
		JComboBox<String> box = new JComboBox<String>();
		box.addItem("制冷");
		box.addItem("供暖");
		box.setBounds(150,70,165,25);
		if(master.getMode()==0)
			box.setSelectedItem("制冷");
		else
			box.setSelectedItem("供暖");
		frame.add(box);
		
		/*JLabel label1 = new JLabel("工作模式:");
        label1.setBounds(10,70,150,25);
        frame.add(label1);
        JTextField text1 = new JTextField(20);
        text1.setBounds(150,70,165,25);
        text1.setText(master.getMode());
        frame.add(text1);*/
        
        JLabel label2 = new JLabel("缺省温度");
        label2.setBounds(10,120,150,25);
        frame.add(label2);
        JTextField text2 = new JTextField(20);
        text2.setBounds(150,120,165,25);
        text2.setText(String.valueOf(master.getDefaultTemperature()));
        frame.add(text2);
        
        JLabel label3 = new JLabel("刷新频率");
        label3.setBounds(10,170,150,25);
        frame.add(label3);
        JTextField text3 = new JTextField(20);
        text3.setBounds(150,170,165,25);
        text3.setText(String.valueOf(master.getRefreshRate()));
        frame.add(text3);
        
        
        JTextField text = new JTextField(20);
        text.setBounds(10,220,165,25);
        text.setText("请输入管理员密码");
        frame.add(text);
        JButton button = new JButton("更改设置");
        button.setBounds(200, 220, 100, 30);
        frame.add(button);
        
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(text.getText().equals("123456")) {
					
					if(box.getSelectedItem().toString().equals("制冷"))
						master.setMode(0);
					else
						master.setMode(1);
					//master.setMode(Integer.valueOf(box.getSelectedItem().toString()));
					master.setDefaultTemperature(Integer.valueOf(text2.getText()));
					master.setRefreshRate(Integer.valueOf(text3.getText()));
					try {
						master.writeXML();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "设置成功", "YES", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				}
				else
			        JOptionPane.showMessageDialog(null, "密码错误", "WRONG", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
        
        
        
	}
}

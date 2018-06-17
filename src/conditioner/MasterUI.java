package conditioner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.dom4j.DocumentException;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class MasterUI {
	private ServerSocket serverSocket = new ServerSocket(9999);
	
	public MasterUI() throws Exception {
		Master master = new Master();
		master.readReport();
		JFrame frame = new JFrame("中央空调");
		frame.setLayout(null);
		frame.setBounds(100, 100, 1000, 800);
		frame.setVisible(true);	

		/*JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);
		panel.setVisible(true)*/;
		
		JButton setting = new JButton("设置");
		JButton view = new JButton("查看房间状态");
		JButton log = new JButton("查看报表");
		
		setting.setBounds(120, 120, 150, 100);
		view.setBounds(120, 320, 150, 100);
		log.setBounds(120, 520, 150, 100);
		
		frame.add(setting);
		frame.add(view);
		frame.add(log);
		
		/*JLabel label1 = new JLabel("房间号:");
        label1.setBounds(300,70,150,25);
        frame.add(label1);
        JLabel text1 = new JLabel();
        text1.setBounds(350,70,165,25);
        frame.add(text1);
        
        JLabel label2 = new JLabel("风速:");
        label2.setBounds(300,120,150,25);
        frame.add(label2);
        JLabel text2 = new JLabel();
        text2.setBounds(350,120,165,25);
        frame.add(text2);
        
        JLabel label3 = new JLabel("目标温度:");
        label3.setBounds(300,170,150,25);
        frame.add(label3);
        JLabel text3 = new JLabel();
        text3.setBounds(350,170,165,25);
        frame.add(text3);
        
        JLabel label4 = new JLabel("当前温度:");
        label4.setBounds(300,220,150,25);
        frame.add(label4);
        JLabel text4 = new JLabel();
        text4.setBounds(350,220,165,25);
        frame.add(text4);*/
		
		setting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SettingUI(master);
			}
		});
		
		view.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewUI(master);
				
			}
		});
		
		log.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					//master.readReport();
					new LogUI(master);
					master.writeReport();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						final Socket socket = serverSocket.accept();
						System.out.println("连接成功");
						
						
						
						
						InputStream inputStream = socket.getInputStream();
				        byte buffer[] = new byte[6000];
				        inputStream.read(buffer);
				        String receive = new String(buffer);
				        //System.out.println(receive);
				        
				        master.whetherWork();				        
				        master.setSlave(receive);
				        master.calculate();
				        
				        OutputStream outputStream = socket.getOutputStream();
						outputStream.write(master.SendToSlave().getBytes());
						System.out.println(master.SendToSlave());
						
						/*master.readReport();
						master.writeReport();*/
						
				        outputStream.close();
				        inputStream.close();
				        socket.close();
				        
				        
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
			}
		}).start();
	}
	
}

package conditioner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.dom4j.DocumentException;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class MasterUI {
	private ServerSocket serverSocket = new ServerSocket(9999);
	
	public MasterUI() throws Exception {
		Master master = new Master();
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
		
		setting.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SettingUI(master);
			}
		});
		
		view.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		log.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						final Socket socket = serverSocket.accept();
						System.out.println("连接成功");
						
						OutputStream outputStream = socket.getOutputStream();
						outputStream.write("1,3,1,0,0".getBytes());
						
						InputStream inputStream = socket.getInputStream();
				        byte buffer[] = new byte[6000];
				        
				        inputStream.read(buffer);
				        System.out.println(new String(buffer));
				        
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

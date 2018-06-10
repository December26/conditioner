package conditioner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Master {
	private String status;//工作状态，0为关闭，1为待机，2为工作
	private int mode;//工作模式，0为制冷，1为制热
	private int roomId;//服务对象
	private int defaultTemperature;//缺省温度
	private int refreshRate;//刷新频率
	private int whether;//是否服务
	private SAXReader saxReader = new SAXReader();
	private Document document = saxReader.read(new File("src/conditioner/config.xml"));
	private Element rootElement = document.getRootElement();
	
	private List<Slave> slaves=new ArrayList<Slave>();
	
	public Master() throws Exception {
						
		Element config = rootElement.element("master");
		
		status = "待机";
		mode = Integer.valueOf(config.element("mode").getText()).intValue();
		roomId = Integer.valueOf(config.element("roomId").getText()).intValue();
		defaultTemperature = Integer.valueOf(config.element("defaultTemperature").getText()).intValue();
		refreshRate = Integer.valueOf(config.element("refreshRate").getText()).intValue();
		whether = 1;
		writeXML();
	}
	
	public void writeXML() throws Exception {
		Element config = rootElement.element("master");
		config.element("status").setText(status);
		config.element("mode").setText(String.valueOf(mode));
		config.element("roomId").setText(String.valueOf(roomId));
		config.element("defaultTemperature").setText(String.valueOf(defaultTemperature));
		config.element("refreshRate").setText(String.valueOf(refreshRate));
		FileOutputStream output = new FileOutputStream(new File("src/conditioner/config.xml"));
		XMLWriter writer = new XMLWriter(output);
		writer.write(document);
		writer.close();
	}
	
	/*public void ConnectToSlave() throws Exception {
		ServerSocket serverSocket = new ServerSocket(9999);
		while(true) {
			Socket socket = serverSocket.accept();
			
		}
	}*/
	
	public void setSlave(String receive) {
		String[] receiveArray = receive.split(",");
		int id = Integer.valueOf(receiveArray[0]);
		boolean isExist = false;
<<<<<<< HEAD
		System.out.println(slaves.size());
		//Iterator<Slave> it = slaves.iterator();
		for(int i=0; i<slaves.size(); i++) {
			System.out.println("id="+id);
			System.out.println("roomId="+slaves.get(i).getRoomId());
			if(id == slaves.get(i).getRoomId()) {
				slaves.get(i).setSpeed(Integer.valueOf(receiveArray[1]));
				slaves.get(i).setTargetTemperature(Integer.valueOf(receiveArray[2]));
				slaves.get(i).setCurrentTemperature(Integer.valueOf(receiveArray[3]));
				if(Integer.valueOf(receiveArray[1])==4)	slaves.remove(i);
=======
		
		Iterator<Slave> it = slaves.iterator();
		while(it.hasNext()) {
			if(id == it.next().getRoomId()) {
				it.next().setSpeed(Integer.valueOf(receiveArray[1]));
				it.next().setTargetTemperature(Integer.valueOf(receiveArray[2]));
				it.next().setCurrentTemperature(Integer.valueOf(receiveArray[3]));
>>>>>>> parent of 9b236f0... viewUI
				isExist = true;
				System.out.println("22222");
			}
		}
		
		if(!isExist)	slaves.add(new Slave(id));
	}
	
	public String SendToSlave() {
		return String.valueOf(mode)+','+String.valueOf(refreshRate)+','+String.valueOf(whether)+",0,0";
	}
	
	public String getStatus() {
		return status;
	}

	public int getMode() {
		return mode;
	}

	public int getRoomId() {
		return roomId;
	}

	public int getDefaultTemperature() {
		return defaultTemperature;
	}

	public int getRefreshRate() {
		return refreshRate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public void setDefaultTemperature(int defaultTemperature) {
		this.defaultTemperature = defaultTemperature;
	}

	public void setRefreshRate(int refreshRate) {
		this.refreshRate = refreshRate;
	}

}

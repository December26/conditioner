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
	private String status;//����״̬��0Ϊ�رգ�1Ϊ������2Ϊ����
	private int mode;//����ģʽ��0Ϊ���䣬1Ϊ����
	private int roomId;//�������
	private int defaultTemperature;//ȱʡ�¶�
	private int refreshRate;//ˢ��Ƶ��
	private int whether;//�Ƿ����
	private int workingNumber;//��ǰ������
	
	private double used;
	private double cost;

	private SAXReader saxReader = new SAXReader();
	private Document document = saxReader.read(new File("src/conditioner/config.xml"));
	private Element rootElement = document.getRootElement();
	
	public List<Slave> slaves=new ArrayList<Slave>();
	
	public Master() throws Exception {
						
		Element config = rootElement.element("master");
		
		status = "����";
		mode = Integer.valueOf(config.element("mode").getText()).intValue();
		roomId = Integer.valueOf(config.element("roomId").getText()).intValue();
		defaultTemperature = Integer.valueOf(config.element("defaultTemperature").getText()).intValue();
		refreshRate = Integer.valueOf(config.element("refreshRate").getText()).intValue();
		whether = 1;
		workingNumber = 0;
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
	
	public synchronized void setSlave(String receive) {
		String[] receiveArray = receive.split(",");
		int id = Integer.valueOf(receiveArray[0]);
		System.out.println(receiveArray[3]);
		boolean isExist = false;
		System.out.println(slaves.size());
		//Iterator<Slave> it = slaves.iterator();
		for(int i=0; i<slaves.size(); i++) {
			if(id == slaves.get(i).getRoomId()) {
				System.out.println(slaves.get(i).getRoomId());
				slaves.get(i).setSpeed(Integer.valueOf(receiveArray[1]));
				slaves.get(i).setTargetTemperature(Double.valueOf(receiveArray[2]));
				slaves.get(i).setCurrentTemperature(Double.valueOf(receiveArray[3]));
				
				whether = slaves.get(i).getWhether();
				
				int speed = Integer.valueOf(receiveArray[1]);
				switch (speed) {
				case 1:
					used = slaves.get(i).getUsed()+1.3*slaves.get(i).getRefreshRate()/60;					
					break;
				case 2:
					used = slaves.get(i).getUsed()+1.0*slaves.get(i).getRefreshRate()/60;
					break;
				case 3:
					used = slaves.get(i).getUsed()+0.8*slaves.get(i).getRefreshRate()/60;
					break;

				default:
					break;
				}
				cost = used*5;
				slaves.get(i).setUsed(used);
				slaves.get(i).setCost(cost);
				if(Integer.valueOf(receiveArray[1])==4)	slaves.remove(i);
				isExist = true;
			}
		}
		
		if(!isExist)	slaves.add(new Slave(id));
	}
	
	public synchronized String SendToSlave() {
		//if(workingNumber<3) whether = 1;
		//else	whether = 0;
		System.out.println("workingNumber��"+workingNumber);
		return String.valueOf(mode)+','+String.valueOf(refreshRate)+','+String.valueOf(whether)+","+String.format("%.2f",used )+","+String.format("%.2f", cost);
	}
	
	public synchronized void whetherWork() {
		workingNumber = 0;
		for(int i=0; i<slaves.size(); i++) {
			if(slaves.get(i).getSpeed()>0&&slaves.get(i).getSpeed()<4)
				workingNumber++;
		}
		
		for(int i=0; i<slaves.size(); i++) {
			if(workingNumber < 3)	slaves.get(i).setWhether(1);
			else {
				if(slaves.get(i).getSpeed()>0&&slaves.get(i).getSpeed()<4)
					slaves.get(i).setWhether(1);
				else
					slaves.get(i).setWhether(0);
			}
		}
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
	
	public int getWhether() {
		return whether;
	}

	public void setWhether(int whether) {
		this.whether = whether;
	}

}

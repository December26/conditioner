package conditioner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
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
	private int workingNumber;//当前工作数
	
	private double used;
	private double cost;

	private SAXReader saxReader = new SAXReader();
	private Document document = saxReader.read(new File("src/conditioner/config.xml"));
	private Element rootElement = document.getRootElement();
	
	public List<Slave> slaves=new ArrayList<Slave>();
	public List<ReportForm> reportForms = new ArrayList<ReportForm>();
	
	public Master() throws Exception {
						
		Element config = rootElement.element("master");
		
		status = "待机";
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
		roomId = Integer.valueOf(receiveArray[0]);
		//System.out.println(receiveArray[3]);
		boolean isExist = false;
		//System.out.println(slaves.size());
		//Iterator<Slave> it = slaves.iterator();
		for(int i=0; i<slaves.size(); i++) {
			if(id == slaves.get(i).getRoomId()) {
				//System.out.println(slaves.get(i).getRoomId());
				slaves.get(i).setSpeed(Integer.valueOf(receiveArray[1]));
				slaves.get(i).setTargetTemperature(Double.valueOf(receiveArray[2]));
				slaves.get(i).setCurrentTemperature(Double.valueOf(receiveArray[3]));
				
				whether = slaves.get(i).getWhether();
				
				if(Integer.valueOf(receiveArray[1])==4)	slaves.remove(i);
				isExist = true;
				
				for(int j=reportForms.size()-1; j>=0; j--) {
					if(reportForms.get(j).getRoomId() == id) {
						if(slaves.get(i).getSpeed()>0&&slaves.get(i).getSpeed()<4) {
							reportForms.get(j).setSpeed(slaves.get(i).getSpeed());
							reportForms.get(j).setTargetTemperature(slaves.get(i).getTargetTemperature());
							reportForms.get(j).setCost(slaves.get(i).getCost());
							reportForms.get(j).setEndTime(new Date().toString());
							break;
						}
						else if(slaves.get(i).getSpeed()==4)
							reportForms.get(j).setEndTime(new Date().toString());
						System.out.println(reportForms.get(j).toString());
					}
				}
			}
		}
		
		if(!isExist) {	
			Slave slave = new Slave(id);	
			slaves.add(slave);
			whether = 0;
			
			ReportForm reportForm = new ReportForm();
			reportForm.setRoomId(id);
			reportForm.setCurrentTemperature(slave.getCurrentTemperature());
			reportForm.setSpeed(0);
			reportForm.setStartTime(new Date().toString());
			System.out.println(reportForm.getStartTime());
			reportForms.add(reportForm);
		}
	}
	
	public synchronized String SendToSlave() {
		//if(workingNumber<3) whether = 1;
		//else	whether = 0;
		System.out.println("workingNumber："+workingNumber);
		for(int i = 0; i<slaves.size(); i++) {
			if(roomId == slaves.get(i).getRoomId()) {
				return String.valueOf(mode)+','+String.valueOf(refreshRate)+','+String.valueOf(whether)+","+String.format("%.2f",slaves.get(i).getUsed() )+","+String.format("%.2f", slaves.get(i).getCost());
			}
		}
		return String.valueOf(mode)+','+String.valueOf(refreshRate)+','+String.valueOf(whether)+",0,0";
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
	
	public synchronized void calculate() {
		for (int i = 0; i < slaves.size(); i++) {
				switch (slaves.get(i).getSpeed()) {
				case 1:
					used = slaves.get(i).getUsed()+1.3*slaves.get(i).getRefreshRate()/60;
					//System.out.println("一档计费");
					break;
				case 2:
					used = slaves.get(i).getUsed()+1.0*slaves.get(i).getRefreshRate()/60;
					//System.out.println("二档计费");
					break;
				case 3:
					used = slaves.get(i).getUsed()+0.8*slaves.get(i).getRefreshRate()/60;
					//System.out.println("三档计费");
					break;

				default:
					used = slaves.get(i).getUsed();
					break;
				}
				cost = used*5;
				slaves.get(i).setUsed(used);
				slaves.get(i).setCost(cost);
				used = 0;
				cost = 0;
		}
	}
	
	public void writeReport() throws IOException {
		OutputStream outputStream = new FileOutputStream("src/conditioner/report.txt");
		
		for (int i = 0; i < reportForms.size(); i++) {
			outputStream.write(reportForms.get(i).toString().getBytes());
		}
		
		outputStream.close();
	}
	
	public void readReport() throws IOException {
		InputStream inputStream = new FileInputStream("src/conditioner/report.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		String str = null;
		while((str = reader.readLine()) != null) {
			String[] strs = str.split(",");
			ReportForm rf = new ReportForm();
			rf.setRoomId(Integer.valueOf(strs[0]));
			rf.setStartTime(strs[1]);
			rf.setEndTime(strs[2]);
			rf.setCurrentTemperature(Double.valueOf(strs[3]));
			rf.setTargetTemperature(Double.valueOf(strs[4]));
			rf.setSpeed(Integer.valueOf(strs[5]));
			rf.setCost(Double.valueOf(strs[6]));
			reportForms.add(rf);
		}
		
		
		reader.close();
		inputStream.close();
	}
	
	public Object[][] info(){
		Object[][] information = new Object[50][7];

		for(int i=0;i<reportForms.size();i++) {
			information[i][0] = reportForms.get(i).getRoomId();
			information[i][1] = reportForms.get(i).getStartTime();
			information[i][2] = reportForms.get(i).getEndTime();
			information[i][3] = reportForms.get(i).getCurrentTemperature();
			information[i][4] = reportForms.get(i).getTargetTemperature();
			information[i][5] = reportForms.get(i).getSpeed();
			information[i][6] = String.format("%.2f", reportForms.get(i).getCost());
		}
		return information;
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

package conditioner;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Slave {
	private int roomId;
	private int mode;
	private double currentTemperature;
	private double targetTemperature;
	private int speed;
	private int refreshRate;
	private double used;//�õ���
	private double cost;//����
	
	public Slave(int roomId) {
		this.roomId = roomId;
		currentTemperature = 25;
		refreshRate = 1000;
	}
	
	public synchronized void changeTemperature() {
		//ÿ�����¶ȱ仯 
		workTemperature();
		if(Math.abs(currentTemperature-targetTemperature) <= 0.06)//�����¶ȴﵽ��Ŀ���¶�
			speed = 0;
	}
	
	public void workTemperature() {
		//�����¶ȱ仯����
		if(mode == 0) {
			if(speed == 1) {
				//1�����¶ȱ仯����
				currentTemperature = currentTemperature - 0.08;
			}
			else if (speed == 2) {
				//2�����¶ȱ仯����
				currentTemperature = currentTemperature - 0.1;
			}
			else if (speed == 3) {
				//3�����¶ȱ仯����
				currentTemperature = currentTemperature - 0.12;
			}
			else {
				//ֹͣ����,�����Զ����±仯����
				currentTemperature = currentTemperature + 0.05;
			}
		}
		
		else {
			if(speed == 1) {
				//1�����¶ȱ仯����
				currentTemperature = currentTemperature + 0.08;
			}
			else if (speed == 2) {
				//2�����¶ȱ仯����
				currentTemperature = currentTemperature + 0.1;
			}
			else if (speed == 3) {
				//3�����¶ȱ仯����
				currentTemperature = currentTemperature + 0.12;
			}
			else {
				//ֹͣ�����������Զ����±仯����
				currentTemperature = currentTemperature - 0.05;
			}			
		}
		
	}

	public void connectToMaster() throws Exception {
		Socket socket = new Socket("10.28.224.241", 9999);
		
		InputStream inputStream = socket.getInputStream();
		DataInputStream in = new DataInputStream(inputStream);
		
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(sentToMaster().getBytes());
		
		
		outputStream.close();
		in.close();
		inputStream.close();
		socket.close();
	}
	
	public String sentToMaster() {
		return String.valueOf(roomId)+','+String.valueOf(targetTemperature)+
				','+String.valueOf(currentTemperature)+','+String.valueOf(speed);
	} 
	
	public int getRoomId() {
		return roomId;
	}

	public int getMode() {
		return mode;
	}

	public double getCurrentTemperature() {
		return currentTemperature;
	}

	public double getTargetTemperature() {
		return targetTemperature;
	}

	public int getSpeed() {
		return speed;
	}

	public int getRefreshRate() {
		return refreshRate;
	}

	public double getUsed() {
		return used;
	}

	public double getCost() {
		return cost;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setCurrentTemperature(double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public void setTargetTemperature(double targetTemperature) {
		this.targetTemperature = targetTemperature;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setRefreshRate(int refreshRate) {
		this.refreshRate = refreshRate;
	}

	public void setUsed(double used) {
		this.used = used;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void receiveFromMater(String str) {
		
	}
}

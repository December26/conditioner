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
	private int lastMode;
	private double currentTemperature;
	private double targetTemperature;
	private int speed;
	private int lastSpeed;
	private int refreshRate;
	private double used;//�õ���
	private double cost;//����
	private String receive;
	private String flag;
	
	public Slave(int roomId) {
		this.roomId = roomId;
		currentTemperature = 25;
		targetTemperature = 22;
		refreshRate = 1;
		speed = 3;
		lastSpeed = 0;
		mode = 0;
		lastMode = 0;
	}
	
	public synchronized void changeTemperature() {
		//ÿ�����¶ȱ仯 
		if(mode > lastMode)	targetTemperature = 28;
		else if(mode < lastMode) targetTemperature = 22;
		lastMode = mode;
		
		workTemperature();
		if(Math.abs(currentTemperature-targetTemperature) <= 0.06*refreshRate) {//�����¶ȴﵽ��Ŀ���¶�
			lastSpeed = speed;
			speed = 0;
		}
			
		if(Math.abs(currentTemperature-targetTemperature) >= 1)//�����¶ȴﵽ��Ŀ���¶�
			if(lastSpeed != 0)
				speed = lastSpeed;
	}
	
	public void workTemperature() {
		//�����¶ȱ仯����
		if(mode == 0) {
			if(speed == 1) {
				//1�����¶ȱ仯����
				currentTemperature = currentTemperature - 0.12*refreshRate;
			}
			else if (speed == 2) {
				//2�����¶ȱ仯����
				currentTemperature = currentTemperature - 0.1*refreshRate;
			}
			else if (speed == 3) {
				//3�����¶ȱ仯����
				currentTemperature = currentTemperature - 0.08*refreshRate;
			}
			else {
				//ֹͣ����,�����Զ����±仯����
				currentTemperature = currentTemperature + 0.05*refreshRate;
			}
		}
		
		else {
			if(speed == 1) {
				//1�����¶ȱ仯����
				currentTemperature = currentTemperature + 0.12*refreshRate;
			}
			else if (speed == 2) {
				//2�����¶ȱ仯����
				currentTemperature = currentTemperature + 0.1*refreshRate;
			}
			else if (speed == 3) {
				//3�����¶ȱ仯����
				currentTemperature = currentTemperature + 0.08*refreshRate;
			}
			else {
				//ֹͣ�����������Զ����±仯����
				currentTemperature = currentTemperature - 0.05*refreshRate;
			}			
		}
		
	}

	public void connectToMaster() throws Exception {
		
		Socket socket = new Socket("10.128.207.133", 9999);
		
		System.out.println("���ӳɹ�");
		
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(sentToMaster().getBytes());
		System.out.println(sentToMaster());
		
		/*InputStream inputStream = socket.getInputStream();
		DataInputStream in = new DataInputStream(inputStream);
		receive = in.readUTF();*/
		
		InputStream inputStream =  socket.getInputStream();
        byte buffer[] = new byte[6000];
        
        inputStream.read(buffer);
        receive = new String(buffer);
        
        receiveFromMaster();
        System.out.println("read info: "+new String(buffer));
		
		//System.out.println("123");
		
		
		
		
		outputStream.close();
		//in.close();
		inputStream.close();
		socket.close();
		//Thread.sleep(refreshRate);
		
	}
	
	public String sentToMaster() {
		return String.valueOf(roomId)+','+String.valueOf(speed)+','+String.valueOf(targetTemperature)+
				','+String.valueOf(currentTemperature);
	} 
	
	public void receiveFromMaster() {
		
		String[] receiveArray = receive.split(",");
		mode = Integer.valueOf(receiveArray[0]);
		refreshRate = Integer.valueOf(receiveArray[1]);
		flag = receiveArray[2];
		used = Double.valueOf(receiveArray[3]);
		cost = Double.valueOf(receiveArray[4]);
		System.out.println(receive);
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

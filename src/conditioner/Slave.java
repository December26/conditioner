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
	private int setSpeed;
	private int refreshRate;
	private double used;//�õ���
	private double cost;//����
	private String receive;
	private boolean flag;//�Ƿ�����
	private int whether;//�ж��Ƿ��ܹ�ͨ������
	
	public Slave(int roomId) {
		this.roomId = roomId;
		currentTemperature = 26.5;
		targetTemperature = 22;
		refreshRate = 1;
		speed = 2;
		lastSpeed = 0;
		setSpeed = 0;
		mode = 0;
		lastMode = 0;
		flag = true;
		whether = 0;
	}

	public synchronized void changeTemperature() {
		//ÿ�����¶ȱ仯 
		if(mode > lastMode)	targetTemperature = 28;
		else if(mode < lastMode) targetTemperature = 22;
		lastMode = mode;
		
		if(whether == 0) {//����յ���������
			//speed = lastSpeed;
			speed = 0;
			flag = false;
			System.out.println("����յ���������");
		}
		
		workTemperature();
		/*if((mode == 0 && (targetTemperature < currentTemperature))||(mode == 1 && (currentTemperature > targetTemperature))) {
			//�����¶ȴﵽ��Ŀ���¶�
			if(speed != 0) {//��һ�ε���Ŀ���¶ȣ���¼����ʱ�ķ���
				lastSpeed = speed;
				speed = 0;
				flag = false;
			}			
		}	
			
		if((mode == 0 && (currentTemperature - targetTemperature) >= 1)||(mode == 1 && (targetTemperature - currentTemperature) >= 1)) {
			//�����¶�δ�ﵽĿ���¶�
			if(lastSpeed != 0) {
				speed = lastSpeed;
				flag = true;
			}
		}*/
		
		if(whether == 1) {
			if((mode == 0 && (currentTemperature < targetTemperature))||(mode == 1 && (currentTemperature > targetTemperature))) {
				speed = 0;
				flag = false;
			}
			
			if((mode == 0 && (currentTemperature - targetTemperature) >= 1)||(mode == 1 && (targetTemperature - currentTemperature) >= 1)) {
				speed = setSpeed;
				flag = true;
			}
		}
						
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
			else if (speed == 0) {
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
			else if (speed == 0) {
				//ֹͣ�����������Զ����±仯����
				currentTemperature = currentTemperature - 0.05*refreshRate;
			}			
		}
		
	}

	public void connectToMaster() throws Exception {
		
		Socket socket = new Socket("10.128.206.220", 9999);
		//Socket socket = new Socket("10.28.224.241", 9999);
		//Socket socket = new Socket("10.206.40.8", 9999);
		//Socket socket = new Socket("10.8.164.10", 9999);
		
		System.out.println("���ӳɹ�");
		
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(sentToMaster().getBytes());
		//System.out.println(roomId+":   "+sentToMaster());
		
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
				','+ String.format("%.2f", currentTemperature);
	} 
	
	public void receiveFromMaster() {
		
		String[] receiveArray = receive.split(",");
		mode = Integer.valueOf(receiveArray[0]);
		refreshRate = Integer.valueOf(receiveArray[1]);
		whether = Integer.valueOf(receiveArray[2]);
		used = Double.valueOf(receiveArray[3]);
		cost = Double.valueOf(receiveArray[4]);
		//System.out.println(receive);
	}
	
	public void disconnectToMaster() {
		speed = 4;
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
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getSetSpeed() {
		return setSpeed;
	}

	public void setSetSpeed(int setSpeed) {
		this.setSpeed = setSpeed;
	}
	
	
}

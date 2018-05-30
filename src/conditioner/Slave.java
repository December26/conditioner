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
	private double used;//用电量
	private double cost;//费用
	private String receive;
	private boolean flag;
	private int whether;//判断是否能够通过请求
	
	public Slave(int roomId) {
		this.roomId = roomId;
		currentTemperature = 26.5;
		targetTemperature = 22;
		refreshRate = 1;
		speed = 1;
		lastSpeed = 0;
		mode = 0;
		lastMode = 0;
		flag = true;
		whether = 2;
	}

	public synchronized void changeTemperature() {
		//每秒钟温度变化 
		if(mode > lastMode)	targetTemperature = 28;
		else if(mode < lastMode) targetTemperature = 22;
		lastMode = mode;
		
		if(whether == 0) {//中央空调负载已满
			speed = lastSpeed;
			speed = 0;
			flag = false;
			System.out.println("中央空调负载已满");
		}
		
		workTemperature();
		/*if((mode == 0 && (targetTemperature < currentTemperature))||(mode == 1 && (currentTemperature > targetTemperature))) {
			//环境温度达到了目标温度
			if(speed != 0) {//第一次到达目标温度，记录到达时的风速
				lastSpeed = speed;
				speed = 0;
				flag = false;
			}			
		}	
			
		if((mode == 0 && (currentTemperature - targetTemperature) >= 1)||(mode == 1 && (targetTemperature - currentTemperature) >= 1)) {
			//环境温度未达到目标温度
			if(lastSpeed != 0) {
				speed = lastSpeed;
				flag = true;
			}
		}*/
		
		if((mode == 0 && (targetTemperature < currentTemperature))||(mode == 1 && (currentTemperature > targetTemperature))) {
			speed = 0;
			flag = false;
		}
		
		if((mode == 0 && (currentTemperature - targetTemperature) >= 1)||(mode == 1 && (targetTemperature - currentTemperature) >= 1)) {
			speed = 3;
			flag = true;
		}
						
	}
	
	public void workTemperature() {
		//工作温度变化函数
		if(mode == 0) {
			if(speed == 1) {
				//1档风温度变化曲线
				currentTemperature = currentTemperature - 0.12*refreshRate;
			}
			else if (speed == 2) {
				//2档风温度变化曲线
				currentTemperature = currentTemperature - 0.1*refreshRate;
			}
			else if (speed == 3) {
				//3档风温度变化曲线
				currentTemperature = currentTemperature - 0.08*refreshRate;
			}
			else if (speed == 0) {
				//停止工作,环境自动升温变化曲线
				currentTemperature = currentTemperature + 0.05*refreshRate;
			}
		}
		
		else {
			if(speed == 1) {
				//1档风温度变化曲线
				currentTemperature = currentTemperature + 0.12*refreshRate;
			}
			else if (speed == 2) {
				//2档风温度变化曲线
				currentTemperature = currentTemperature + 0.1*refreshRate;
			}
			else if (speed == 3) {
				//3档风温度变化曲线
				currentTemperature = currentTemperature + 0.08*refreshRate;
			}
			else if (speed == 0) {
				//停止工作，环境自动降温变化曲线
				currentTemperature = currentTemperature - 0.05*refreshRate;
			}			
		}
		
	}

	public void connectToMaster() throws Exception {
		
		Socket socket = new Socket("10.128.207.133", 9999);
		
		System.out.println("连接成功");
		
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(sentToMaster().getBytes());
		System.out.println(roomId+":   "+sentToMaster());
		
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
		whether = Integer.valueOf(receiveArray[2]);
		used = Double.valueOf(receiveArray[3]);
		cost = Double.valueOf(receiveArray[4]);
		//System.out.println(receive);
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
}

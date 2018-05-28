package conditioner;

public class Slave {
	private int roomId;
	private int mode;
	private float currentTemperature;
	private float targetTemperature;
	private int speed;
	private int refreshRate;
	
	public Slave(int roomId) {
		
	}
	
	public void changeTemperature() {
		autoTemperature();
		workTemperature();
	}
	
	public void autoTemperature() {
		//环境温度自动变化函数
		if(speed == 0) {
			//停止工作时环境开始自动变化
			if(mode == 0) {
				//制冷模式说明是夏天，环境自动升温变化曲线
			}
			else {
				//供暖模式说明是冬天，环境自动降温变化曲线
			}
		}
		
	}
	
	public void workTemperature() {
		//工作温度变化函数
		if(speed == 1) {
			//1档风温度变化曲线
		}
		else if (speed == 2) {
			//2档风温度变化曲线
		}
		else if (speed == 3) {
			//3档风温度变化曲线
		}
		else {
			//停止工作
		}
	}
}

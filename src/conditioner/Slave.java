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
		//�����¶��Զ��仯����
		if(speed == 0) {
			//ֹͣ����ʱ������ʼ�Զ��仯
			if(mode == 0) {
				//����ģʽ˵�������죬�����Զ����±仯����
			}
			else {
				//��ůģʽ˵���Ƕ��죬�����Զ����±仯����
			}
		}
		
	}
	
	public void workTemperature() {
		//�����¶ȱ仯����
		if(speed == 1) {
			//1�����¶ȱ仯����
		}
		else if (speed == 2) {
			//2�����¶ȱ仯����
		}
		else if (speed == 3) {
			//3�����¶ȱ仯����
		}
		else {
			//ֹͣ����
		}
	}
}

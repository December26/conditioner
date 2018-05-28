package conditioner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
	private SAXReader saxReader = new SAXReader();
	private Document document = saxReader.read(new File("src/conditioner/config.xml"));
	private Element rootElement = document.getRootElement();
	
	public Master() throws Exception {
						
		Element config = rootElement.element("master");
		
		status = "����";
		mode = Integer.valueOf(config.element("mode").getText()).intValue();
		roomId = Integer.valueOf(config.element("roomId").getText()).intValue();
		defaultTemperature = Integer.valueOf(config.element("defaultTemperature").getText()).intValue();
		refreshRate = Integer.valueOf(config.element("refreshRate").getText()).intValue();
		
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

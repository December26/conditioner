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
	private String status;//工作状态，0为关闭，1为待机，2为工作
	private String mode;//工作模式，0为制冷，1为制热
	private String roomId;//服务对象
	private int defaultTemperature;//缺省温度
	private int refreshRate;//刷新频率
	private SAXReader saxReader = new SAXReader();
	private Document document = saxReader.read(new File("src/conditioner/config.xml"));
	private Element rootElement = document.getRootElement();
	
	public Master() throws Exception {
						
		Element config = rootElement.element("master");
		
		status = "待机";
		mode = config.element("mode").getText();
		roomId = config.element("roomId").getText();
		defaultTemperature = Integer.valueOf(config.element("defaultTemperature").getText()).intValue();
		refreshRate = Integer.valueOf(config.element("refreshRate").getText()).intValue();
		
		writeXML();
	}
	
	public void writeXML() throws Exception {
		Element config = rootElement.element("master");
		config.element("status").setText(status);
		config.element("mode").setText(mode);
		config.element("roomId").setText(roomId);
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

	public String getMode() {
		return mode;
	}

	public String getRoomId() {
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

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public void setDefaultTemperature(int defaultTemperature) {
		this.defaultTemperature = defaultTemperature;
	}

	public void setRefreshRate(int refreshRate) {
		this.refreshRate = refreshRate;
	}

}

package conditioner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class ReportForm {
	private int roomId;
	private String startTime;
	private String endTime;
	private double currentTemperature;
	private double targetTemperature;
	private int speed;
	private double cost;
	
	
	
	
	@Override
	public String toString() {
		/*return "roomId=" + roomId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", currentTemperature=" + String.format("%.2f", currentTemperature) + ", targetTemperature=" + targetTemperature + ", speed="
				+ speed + ", cost=" + String.format("%.2f", cost) + "\n";*/
		return roomId + "," + startTime + "," + endTime
				+ "," + String.format("%.2f", currentTemperature) + "," + targetTemperature + ","
				+ speed + "," + String.format("%.2f", cost) + "\r\n";
	}
	public int getRoomId() {
		return roomId;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
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
	public double getCost() {
		return cost;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	
}

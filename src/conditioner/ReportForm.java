package conditioner;

import java.util.Date;

public class ReportForm {
	private int roomId;
	private Date startTime;
	private Date endTime;
	private String currentTemperature;
	private String targetTemperature;
	private String speed;
	private double cost;
	
	
	
	public int getRoomId() {
		return roomId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getCurrentTemperature() {
		return currentTemperature;
	}
	public String getTargetTemperature() {
		return targetTemperature;
	}
	public String getSpeed() {
		return speed;
	}
	public double getCost() {
		return cost;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setCurrentTemperature(String currentTemperature) {
		this.currentTemperature = currentTemperature;
	}
	public void setTargetTemperature(String targetTemperature) {
		this.targetTemperature = targetTemperature;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	
}

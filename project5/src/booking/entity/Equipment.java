package booking.entity;

public class Equipment {
	private String equipmentId;
	private String name;
	private String condition; //new/used/broken
	private String status; //available or not available
	
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Equipment(String equipmentId, String name, String condition, String status) {
		super();
		this.equipmentId = equipmentId;
		this.name = name;
		this.condition = condition;
		this.status = status;
	}
	
	@Override
	public String toString() {
		return String.format("%-13s %-21s %-13s %-15s", 
                equipmentId, name, condition, status);
	}
	
	
	
	
	
	

}

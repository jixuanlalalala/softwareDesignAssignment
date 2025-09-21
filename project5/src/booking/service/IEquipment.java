package booking.service;

import booking.entity.Equipment;

public interface IEquipment extends IFile {
	
	public void addEquipment(String name, String condition);
	
	public void updateEquipment(String id, String name, String condition, String status);
	
	public void deleteEquipment(String id);
	
	public void viewEquipment();
	
	public Equipment getEquipmentById(String equipmentId);

}

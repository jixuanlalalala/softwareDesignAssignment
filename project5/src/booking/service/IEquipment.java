package booking.service;

import booking.entity.Equipment;

public interface IEquipment extends IViewEquipment {
	
	public void addEquipment(String name, String condition);
	
	public void updateEquipment(String id, String name, String condition);
	
	public void deleteEquipment(String id);

}
